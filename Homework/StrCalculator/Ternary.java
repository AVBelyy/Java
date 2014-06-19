public class Ternary implements Expression3 {
    private final Expression3 exprA, exprB, exprC;
    private static final double E = 1e-16;

    public Ternary(Expression3 exprA, Expression3 exprB, Expression3 exprC) {
        this.exprA = exprA;
        this.exprB = exprB;
        this.exprC = exprC;
    }

    public double evaluate(double x, double y, double z) {
        return (Math.abs(exprA.evaluate(x, y, z)) > E) ? exprB.evaluate(x, y, z) : exprC.evaluate(x, y, z);
    }
}
