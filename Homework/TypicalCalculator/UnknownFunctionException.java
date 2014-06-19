class UnknownFunctionException extends CalculateErrorException {
    public UnknownFunctionException(String f) {
        super("unknown function '" + f + "'");
    }
}
