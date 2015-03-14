package ru.ifmo.ctddev.belyy.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by anton on 27/02/15.
 */
public class Implementor implements Impler {
    private static String printModifiers(int modifiers) {
        List<String> modifiersStr = new LinkedList<>();

        if (Modifier.isPublic(modifiers)) {
            modifiersStr.add("public");
        } else if (Modifier.isPrivate(modifiers)) {
            modifiersStr.add("private");
        } else if (Modifier.isProtected(modifiers)) {
            modifiersStr.add("protected");
        }

        return String.join(" ", modifiersStr);
    }

    private String printTypeParams(Class[] typeParams) {
        return String.join(", ", Arrays.asList(typeParams).stream().map(Class::getCanonicalName).collect(Collectors.toList()));
    }

    private List<Method> getAllMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        Class<?> it = clazz;

        while (it != null && it != Object.class) {
            for (Method m : it.getDeclaredMethods()) {
                methods.add(m);
            }
            for (Method m : it.getMethods()) {
                methods.add(m);
            }
            it = it.getSuperclass();
        }

        return methods;
    }

    public void implement(Class<?> clazz, File root) throws ImplerException {
        if (clazz.isPrimitive()) {
            throw new ImplerException("primitive type");
        }
        if (Modifier.isFinal(clazz.getModifiers())) {
            throw new ImplerException("final class");
        }

        StringBuilder implStr = new StringBuilder();
        String className = clazz.getSimpleName() + "Impl";
        String classCanonName = clazz.getCanonicalName();

        // print package
        implStr.append("package " + clazz.getPackage().getName() + ";\n\n");

        // print class header
        implStr.append("public class " + className + " ");
        if (clazz.isInterface()) {
            implStr.append("implements");
        } else {
            implStr.append("extends");
        }
        implStr.append(" " + classCanonName + " {\n");

        // print constructors
        Constructor[] constructors = clazz.getDeclaredConstructors();
        boolean isConstructible = constructors.length == 0;
        for (Constructor c : constructors) {
            if (!Modifier.isPrivate(c.getModifiers())) {
                isConstructible = true;
            }
            implStr.append("\n\tpublic " + className + "(");
            // print constructor's params
            int varCount = 0;
            boolean firstParam = true;
            for (Class cc : c.getParameterTypes()) {
                if (!firstParam) {
                    implStr.append(", ");
                }
                implStr.append(cc.getTypeName() + " v" + (++varCount));
                firstParam = false;
            }

            implStr.append(")");

            // print constructor's exceptions
            String cExceptionTypes = printTypeParams(c.getExceptionTypes());
            if (!cExceptionTypes.isEmpty()) {
                implStr.append(" throws " + cExceptionTypes);
            }

            // print constructor's body
            implStr.append(" {\n\t\tsuper(");
            for (int i = 1; i <= varCount; i++) {
                if (i != 1) {
                    implStr.append(", ");
                }
                implStr.append("v" + i);
            }
            implStr.append(");\n\t}\n");
        }

        if (!isConstructible) {
            throw new ImplerException("no default constructor");
        }

        // print methods
        Set<UniqueMethod> methods = new HashSet<>();
        for (Method m : getAllMethods(clazz)) {
            int modifiers = m.getModifiers();
            if (Modifier.isAbstract(modifiers)) {
                methods.add(new UniqueMethod(m));
            }
        }

        for (UniqueMethod um : methods) {
            Method m = um.getMethod();
            int modifiers = m.getModifiers();
            Class retType = m.getReturnType();
            String typeName = m.getReturnType().getCanonicalName();
            implStr.append("\n\t" + printModifiers(modifiers) + " ");
            implStr.append(typeName + " " + m.getName() + "(");

            // print method's params
            int varCount = 0;
            boolean firstParam = true;
            for (Class c : m.getParameterTypes()) {
                if (!firstParam) {
                    implStr.append(", ");
                }
                implStr.append(c.getCanonicalName() + " v" + (++varCount));
                firstParam = false;
            }

            implStr.append(")");

            // print method's exceptions
            String cExceptionTypes = printTypeParams(m.getExceptionTypes());
            if (!cExceptionTypes.isEmpty()) {
                implStr.append(" throws " + cExceptionTypes);
            }

            // print method's body
            implStr.append(" {\n");
            if (!retType.equals(void.class)) {
                implStr.append("\t\treturn ");
                if (retType.isPrimitive()) {
                    if (retType.equals(boolean.class)) {
                        implStr.append("false");
                    } else {
                        implStr.append("0");
                    }
                } else {
                    implStr.append("null");
                }
                implStr.append(";\n");
            }

            implStr.append("\t}\n");
        }

        implStr.append("}\n");

        // write implStr to file
        File outputFile = new File(root, clazz.getCanonicalName().replace(".", File.separator) + "Impl.java").getAbsoluteFile();
        // Yes, I know it can fail. No, I don't know what to do in this case.
        outputFile.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf8"))) {
            writer.write(implStr.toString());
        } catch (IOException e) {
            throw new ImplerException(e);
        }
    }

    private static class UniqueMethod {
        private final Method m;

        public UniqueMethod(Method m) {
            this.m = m;
        }

        public Method getMethod() {
            return m;
        }

        public int hashCode() {
            int hash = m.getName().hashCode();
            for (Class c : m.getParameterTypes()) {
                hash ^= c.hashCode();
            }
            return hash;
        }

        public boolean equals(Object obj) {
            if (obj instanceof UniqueMethod) {
                Method other = ((UniqueMethod) obj).getMethod();
                if (!m.getName().equals(other.getName())) {
                    return false;
                }

                if (!Arrays.equals(m.getParameterTypes(), other.getParameterTypes())) {
                    return false;
                }
                return true;
            } else {
                return false;
            }
        }
    }
}
