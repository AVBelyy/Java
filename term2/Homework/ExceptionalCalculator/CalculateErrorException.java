class CalculateErrorException extends Exception {
    private final String message;

    protected CalculateErrorException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
