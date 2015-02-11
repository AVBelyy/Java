class NegativeFractionalExponentException extends CalculateErrorException {
    public NegativeFractionalExponentException() {
        super("negative number is raised to a fractional power in '^'");
    }
}
