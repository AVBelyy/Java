import java.math.BigInteger;

public class GenericParser {
    private static class Tester<T> {
        private Numberish<T> instance;
        private ExpressionParser<T> parser;
        private Expression3<T> parsed;
        private String expr;

        public Tester(String expr, Numberish<T> instance) {
            this.instance = instance;
            this.expr = expr;
            this.parser = new ExpressionParser<T>(instance);
        }

        public boolean parse() {
            try {
                parsed = parser.parse(expr);
                return true;
            } catch (SyntaxErrorException e) {
                System.out.printf("%s\nSyntax error at #%d: %s\n", e.getExpr(), e.getPos(), e.getMessage());
                return false;
            }
        }

        public void test(Integer x, Integer y, Integer z) {
                try {
                    Numberish<T> nx = instance.fromInteger(x);
                    Numberish<T> ny = instance.fromInteger(y);
                    Numberish<T> nz = instance.fromInteger(z);
                    System.out.println(parsed.evaluate(nx, ny, nz).getValue());
                } catch (CalculateErrorException e) {
                    //System.out.printf("Calculate error: %s\n", e.getMessage());
                    System.out.println("error");
                }
        }
    }

    public static void main(String[] args) {
        String choice = args[0];
        String expr = args[1];
        Tester tester;
        if (choice.equals("-i")) {
            tester = new Tester<Integer>(expr, Intish.INSTANCE);
        } else if (choice.equals("-d")) {
            tester = new Tester<Double>(expr, Doublish.INSTANCE);
        } else if (choice.equals("-bi")) {
            tester = new Tester<BigInteger>(expr, BigIntish.INSTANCE);
        } else {
            System.out.printf("Unsupported number class choice: '%s'\n", choice);
            return;
        }
        if (tester.parse()) {
            for (int x = -100; x <= 100; x++) {
                for (int y = -100; y <= 100; y++) {
                    tester.test(x, y, 0);
                }
            }
        }
    }
}
