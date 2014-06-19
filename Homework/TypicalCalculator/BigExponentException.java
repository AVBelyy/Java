class BigExponentException extends CalculateErrorException {
    public BigExponentException() {
        super("non-integer exponent in '^'");
    }
}
