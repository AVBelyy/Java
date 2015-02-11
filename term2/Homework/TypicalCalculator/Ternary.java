public class Ternary<T> implements Expression3<T> {
    private final Expression3<T> exprA, exprB, exprC;

    public Ternary(Expression3<T> exprA, Expression3<T> exprB, Expression3<T> exprC) {
        this.exprA = exprA;
        this.exprB = exprB;
        this.exprC = exprC;
    }

    public Numberish<T> evaluate(Numberish<T> x, Numberish<T> y, Numberish<T> z) throws CalculateErrorException {
        return exprA.evaluate(x, y, z).isZero() ? exprC.evaluate(x, y, z) : exprB.evaluate(x, y, z);
    }
}
