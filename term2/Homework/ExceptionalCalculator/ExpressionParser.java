import java.util.*;

public class ExpressionParser {
    private enum Lexem {
        OPERATOR, FUNCTION, OPERAND, LBRACKET, RBRACKET
    }

    private static boolean isDelim(char c) {
        return c == ' ' || c == '\t';
    }

    private static boolean isOp(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("~") || op.equals("^");
    }

    private static boolean isUnary(String op) {
        return op.equals("^") || op.equals("~") || op.equals("|") || op.equals("l");
    }

    private static boolean isRightAssoc(String op) {
        return op.equals("!") || isUnary(op);
    }

    private static int priority(String op) {
        if (isUnary(op)) {
            return 4;
        } else if (op.equals("!")) {
            return 3;
        } else if (op.equals("*") || op.equals("/")) {
            return 2;
        } else if (op.equals("+") || op.equals("-")) {
            return 1;
        } else {
            return -1;
        }
    }

    private static void processOp(LinkedList<Expression3> st, String op, int curPos, String expr) throws SyntaxErrorException {
        try {
            Expression3 r = st.removeLast();
            if (op.equals("^")) {
                st.addLast(new UnaryMinus(r));
            } else if (op.equals("~")) {
                st.addLast(new UnaryNot(r));
            } else if (op.equals("|")) {
                st.addLast(new AbsoluteValue(r));
            } else if (op.equals("l")) {
                st.addLast(new LogBinary(r));
            } else {
                try {
                    Expression3 l = st.removeLast();
                    if (op.equals("+")) {
                        st.addLast(new Add(l, r));
                    } else if (op.equals("-")) {
                        st.addLast(new Subtract(l, r));
                    } else if (op.equals("*")) {
                        st.addLast(new Multiply(l, r));
                    } else if (op.equals("/")) {
                        st.addLast(new Divide(l, r));
                    } else if (op.equals("!")) {
                        st.addLast(new Power(l, r));
                    }
                } catch (NoSuchElementException e) {
                    String found = curPos >= expr.length() ? "EOL" : "'" + expr.charAt(curPos) + "'";
                    throw new SyntaxErrorException(curPos, "expected operand, found " + found, expr);
                }
            }
        } catch (NoSuchElementException e) {
            String found = curPos >= expr.length() ? "EOL" : "'" + expr.charAt(curPos) + "'";
            throw new SyntaxErrorException(curPos, "expected operand, found " + found, expr);
        }
    }

    public static Expression3 parse(String expr) throws SyntaxErrorException {
        LinkedList<Expression3> st = new LinkedList<Expression3>();
        LinkedList<String> op = new LinkedList<String>();
        Lexem lastOp = null;
        for (int i = 0; i < expr.length(); i++) {
            if (!isDelim(expr.charAt(i))) {
                if (lastOp == Lexem.FUNCTION && expr.charAt(i) != '(') {
                    throw new SyntaxErrorException(i, "expected '(' after function, found '" + expr.charAt(i) + "'", expr);
                }
                if (expr.charAt(i) == '(') {
                    if (lastOp == Lexem.RBRACKET) {
                        throw new SyntaxErrorException(i, "no expression between ')' and '('", expr);
                    } else if (lastOp == Lexem.OPERAND) {
                        throw new SyntaxErrorException(i, "no operator between operand and '('", expr);
                    }
                    op.addLast("(");
                    lastOp = Lexem.LBRACKET;
                } else if (expr.charAt(i) == ')') {
                    if (lastOp == Lexem.LBRACKET) {
                        throw new SyntaxErrorException(i, "no expression in brackets", expr);
                    }
                    try {
                        while (!op.peekLast().equals("(")) {
                            processOp(st, op.removeLast(), i, expr);
                        }
                    } catch (NullPointerException e) {
                        throw new SyntaxErrorException(i, "unmatched ')'", expr);
                    }
                    op.removeLast();
                    lastOp = Lexem.RBRACKET;
                } else if (isOp("" + expr.charAt(i))) {
                    String curOp = "" + expr.charAt(i);
                    if (curOp.equals("^")) curOp = "!";
                    if ((lastOp != Lexem.RBRACKET && lastOp != Lexem.OPERAND) && curOp.equals("-")) curOp = "^";
                    if (!isUnary(curOp) && lastOp != Lexem.OPERAND && lastOp != Lexem.RBRACKET) {
                        throw new SyntaxErrorException(i, "expected operand or ')' before '" + expr.charAt(i) + "'", expr);
                    }
                    while (!op.isEmpty() && (!isRightAssoc(curOp) && priority(op.peekLast()) >= priority(curOp) || isRightAssoc(curOp) && priority(op.peekLast()) > priority(curOp))) {
                        processOp(st, op.removeLast(), i, expr);
                    }
                    op.addLast(curOp);
                    lastOp = Lexem.OPERATOR;
                } else {
                    String operand;
                    StringBuilder builder = new StringBuilder();
                    while (i < expr.length() && Character.isLetterOrDigit(expr.charAt(i))) {
                        builder.append(expr.charAt(i++));
                    }
                    i--;
                    Expression3 token = null;
                    operand = builder.toString();
                    if (operand.equals("abs")) {
                        op.addLast("|");
                        lastOp = Lexem.FUNCTION;
                    } else if (operand.equals("lb")) {
                        op.addLast("l");
                        lastOp = Lexem.FUNCTION;
                    } else {
                        try {
                            if (operand.equals(("" + Integer.MIN_VALUE).substring(1)) && op.peekLast().equals("^")) {
                                token = new Const(Integer.MIN_VALUE);
                            } else if (Character.isDigit(operand.charAt(0))) {
                                try {
                                    token = new Const(Integer.parseInt(operand));
                                } catch (NumberFormatException e) {
                                    throw new SyntaxErrorException(i, "incorrect number format: not in range [-2^31, 2^31)", expr);
                                }
                            } else {
                                token = new Variable(operand);
                            }
                        } catch (NullPointerException e) {
                            throw new SyntaxErrorException(i, "incorrect number format: not in range [-2^31, 2^31)", expr);
                        }
                        if (lastOp == Lexem.RBRACKET || lastOp == Lexem.OPERAND) {
                            throw new SyntaxErrorException(i, "expected '(' or operator between operands", expr);
                        }
                        st.addLast(token);
                        lastOp = Lexem.OPERAND;
                    }
                }
            }
        }
        while (!op.isEmpty()) {
            processOp(st, op.removeLast(), expr.length(), expr);
        }
        if (st.size() == 0) {
            throw new SyntaxErrorException(expr.length(), "numberless expression", expr);
        }
        return st.removeLast();
    }
}
