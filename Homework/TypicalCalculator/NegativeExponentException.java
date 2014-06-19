class NegativeExponentException extends CalculateErrorException {
    public NegativeExponentException() {
        super("negative exponent in '^'");
    }
}
