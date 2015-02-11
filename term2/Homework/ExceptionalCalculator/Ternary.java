public class Ternary implements Expression3 {
    private final Expression3 exprA, exprB, exprC;

    public Ternary(Expression3 exprA, Expression3 exprB, Expression3 exprC) {
        this.exprA = exprA;
        this.exprB = exprB;
        this.exprC = exprC;
    }

    public int evaluate(int x, int y, int z) throws CalculateErrorException {
        return (exprA.evaluate(x, y, z) != 0) ? exprB.evaluate(x, y, z) : exprC.evaluate(x, y, z);
    }
}
