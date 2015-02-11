import java.util.LinkedList;

public class ExpressionParser {
    private static boolean isDelim(char c) {
        return c == ' ' || c == '\t';
    }

    private static boolean isOp(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("~");
    }

    private static boolean isUnary(String op) {
        return op.equals("^") || op.equals("~") || op.equals("|");
    }

    private static int priority(String op) {
        if (isUnary(op)) {
            return 4;
        } else if (op.equals("*") || op.equals("/")) {
            return 2;
        } else if (op.equals("+") || op.equals("-")) {
            return 1;
        } else {
            return -1;
        }
    }

    private static void processOp(LinkedList<Expression3> st, String op) {
        Expression3 r = st.removeLast();
        if (op.equals("^")) {
            st.addLast(new UnaryMinus(r));
        } else if (op.equals("~")) {
            st.addLast(new UnaryNot(r));
        } else if (op.equals("|")) {
            st.addLast(new AbsoluteValue(r));
        } else {
            Expression3 l = st.removeLast();
            if (op.equals("+")) {
                st.addLast(new Add(l, r));
            } else if (op.equals("-")) {
                st.addLast(new Subtract(l, r));
            } else if (op.equals("*")) {
                st.addLast(new Multiply(l, r));
            } else if (op.equals("/")) {
                st.addLast(new Divide(l, r));
            }
        }
    }

    public static Expression3 parse(String expr) {
        boolean mayUnaryMinus = true;
        LinkedList<Expression3> st = new LinkedList<Expression3>();
        LinkedList<String> op = new LinkedList<String>();
        for (int i = 0; i < expr.length(); i++) {
            if (!isDelim(expr.charAt(i))) {
                if (expr.charAt(i) == '(') {
                    op.addLast("(");
                    mayUnaryMinus = true;
                } else if (expr.charAt(i) == ')') {
                    while (!op.peekLast().equals("(")) {
                        processOp(st, op.removeLast());
                    }
                    op.removeLast();
                    mayUnaryMinus = false;
                } else if (isOp("" + expr.charAt(i))) {
                    String curOp = "" + expr.charAt(i);
                    if (mayUnaryMinus && curOp.equals("-")) curOp = "^";
                    while (!op.isEmpty() && (!isUnary(curOp) && priority(op.peekLast()) >= priority(curOp) || isUnary(curOp) && priority(op.peekLast()) > priority(curOp))) {
                        processOp(st, op.removeLast());
                    }
                    op.addLast(curOp);
                    mayUnaryMinus = true;
                } else {
                    String operand;
                    StringBuilder builder = new StringBuilder();
                    while (i < expr.length() && Character.isLetterOrDigit(expr.charAt(i))) {
                        builder.append(expr.charAt(i++));
                    }
                    i--;
                    Expression3 token;
                    operand = builder.toString();
                    if (operand.equals("abs")) {
                        op.addLast("|");
                    } else {
                        if (operand.equals(("" + Integer.MIN_VALUE).substring(1)) && op.peekLast().equals("^")) {
                            token = new Const(Integer.MIN_VALUE);
                        } else if (Character.isDigit(operand.charAt(0))) {
                            token = new Const(Integer.parseInt(operand));
                        } else {
                            token = new Variable(operand);
                        }
                        st.addLast(token);
                    }
                    mayUnaryMinus = false;
                }
            }
        }
        while (!op.isEmpty()) {
            processOp(st, op.removeLast());
        }
        return st.removeLast();
    }
}
