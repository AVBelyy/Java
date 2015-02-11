// What can be at the beginning of the file?
//
// package
// import (package.name.* | class.name | interface.name) / import static (...) (static methods | fields to be imported | *)
// [private|public|protected|...] class/interface (no static classes on top)
import static java.lang.Math.sin;

// default imports for class in a.b.c
// a.b.c + java.lang + по-умолчанию

// interface: no implementation, no variables except final and static, no static methods

// Java 8:
// 1) static methods in interface
// 2) Methods by default
import java.util.*;

/* JAVA 8
 *
 * public interface Stack {
    default public void clear() {
        while (!isEmpty()) {
            pop();
        }
    }
}*/

// String[] s = new String[10];
// Object[] o = (Object[]) s;
// o[1] = 1;
// s[0] = s[1];
//
// fails at 3rd line - original type, which is String[], is stored, here we see Integer[x.
// that's why we can't "new E[10]", because E type is overwritten

public class L09 {
    public interface Block<T, E extends Exception> {
        T execute() throws E;
    }

    public interface Steak<E> {
        E pop();
        void push(E e);
        int size();
    }

    void q() {
        Stack s  = new Stack() {
            {
                push(1);
                push(2);
                push(3);
            }
        };
    }
}
