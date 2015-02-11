class NonPositiveLogException extends CalculateErrorException {
    public NonPositiveLogException() {
        super("non-positive value in lb");
    }
}
