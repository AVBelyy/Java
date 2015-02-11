public class LogBinary implements Expression3 {
    final Expression3 expression;

    public LogBinary(Expression3 expr) {
        expression = expr;
    }

    public int evaluate(int x, int y, int z) throws CalculateErrorException {
        int value = expression.evaluate(x, y, z), result = 0;
        if (value == 0) {
            return Integer.MIN_VALUE;
        } else if (value < 0) {
            throw new NegativeLogException();
        }
        while (value != 1) {
            value >>= 1;
            result++;
        }
        return result;
    }
}
