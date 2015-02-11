class NotImplementedException extends CalculateErrorException {
    public NotImplementedException(String op, Class cls) {
        super("'" + op + "' not implemented in " + cls.getName());
    }
}
