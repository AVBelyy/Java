import java.util.*;

public class ExpressionParser<T> {
    private enum Lexem {
        OPERATOR, FUNCTION, OPERAND, LBRACKET, RBRACKET
    }

    private LinkedList<Expression3<T>> st;
    private LinkedList<String> op;
    private Numberish<T> instance;
    private Map<String, Lambdish<T>> functions;
    private String expr;

    public ExpressionParser(Numberish<T> instance) {
        this.instance = instance;
        this.functions = instance.getFunctions();
    }

    private Numberish<T> fromString(String s) {
        return instance.fromString(s);
    }

    private static boolean isDelim(char c) {
        return c == ' ' || c == '\t';
    }

    private static boolean isOp(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("~") || op.equals("^");
    }

    private static boolean isUnary(String op) {
        return op.equals("^") || op.equals("~") || op.equals("|") || op.equals("l") || op.startsWith("#");
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

    private void processOp(String op, int curPos) throws SyntaxErrorException {
        try {
            Expression3<T> r = st.removeLast();
            if (op.equals("^")) {
                st.addLast(new UnaryMinus<T>(r));
            } else if (op.equals("~")) {
                st.addLast(new UnaryNot<T>(r));
            } else if (op.equals("|")) {
                st.addLast(new AbsoluteValue<T>(r));
            } else if (op.equals("l")) {
                st.addLast(new LogBinary<T>(r));
            } else if (op.charAt(0) == '#') {
                st.addLast(new ArbitraryFunction<T>(op.substring(1), instance, r));
            } else {
                try {
                    Expression3<T> l = st.removeLast();
                    if (op.equals("+")) {
                        st.addLast(new Add<T>(l, r));
                    } else if (op.equals("-")) {
                        st.addLast(new Subtract<T>(l, r));
                    } else if (op.equals("*")) {
                        st.addLast(new Multiply<T>(l, r));
                    } else if (op.equals("/")) {
                        st.addLast(new Divide<T>(l, r));
                    } else if (op.equals("!")) {
                        st.addLast(new Power<T>(l, r));
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

    public Expression3<T> parse(String expr) throws SyntaxErrorException {
        this.st = new LinkedList<Expression3<T>>();
        this.op = new LinkedList<String>();
        this.expr = expr;
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
                            processOp(op.removeLast(), i);
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
                        processOp(op.removeLast(), i);
                    }
                    op.addLast(curOp);
                    lastOp = Lexem.OPERATOR;
                } else {
                    String operand;
                    StringBuilder builder = new StringBuilder();
                    while (i < expr.length() &&
                           (Character.isLetterOrDigit(expr.charAt(i))
                            || expr.charAt(i) == '.'
                            || Character.toLowerCase(expr.charAt(i)) == 'e'
                            || (expr.charAt(i) == '-' && i > 0 && Character.toLowerCase(expr.charAt(i - 1)) == 'e')))
                    {
                        builder.append(expr.charAt(i++));
                    }
                    i--;
                    Expression3<T> token = null;
                    operand = builder.toString();
                    if (operand.isEmpty()) {
                        throw new SyntaxErrorException(i + 1, "unexpected symbol '" + expr.charAt(i + 1) + "'", expr);
                    }
                    if (operand.equals("abs")) {
                        op.addLast("|");
                        lastOp = Lexem.FUNCTION;
                    } else if (operand.equals("lb")) {
                        op.addLast("l");
                        lastOp = Lexem.FUNCTION;
                    } else if (functions.containsKey(operand)) {
                        op.addLast("#" + operand);
                        lastOp = Lexem.FUNCTION;
                    } else {
                        if (Character.isDigit(operand.charAt(0))) {
                            try {
                                if (!op.isEmpty() && op.peekLast().equals("^")) {
                                    operand = "-" + operand;
                                    op.removeLast();
                                }
                                token = new Const<T>(fromString(operand));
                            } catch (NumberFormatException e) {
                                throw new SyntaxErrorException(i, "incorrect number format", expr);
                            }
                        } else {
                            token = new Variable<T>(operand);
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
            processOp(op.removeLast(), expr.length());
        }
        if (st.size() == 0) {
            throw new SyntaxErrorException(expr.length(), "numberless expression", expr);
        }
        return st.removeLast();
    }
}
