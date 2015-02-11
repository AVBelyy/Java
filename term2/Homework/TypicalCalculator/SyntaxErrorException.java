class SyntaxErrorException extends Exception {
    private final int pos;
    private final String message, expr;

    public SyntaxErrorException(int pos, String message, String expr) {
        this.pos = pos + 1;
        this.message = message;
        this.expr = expr;
    }

    public int getPos() {
        return this.pos;
    }

    public String getMessage() {
        return this.message;
    }

    public String getExpr() {
        return this.expr;
    }
}
