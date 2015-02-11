public class Main {
    public static void main(String[] args) {
        String expr = args[0];
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);
        try {
            Expression3 parsed = ExpressionParser.parse(expr);
            try {
                System.out.println(parsed.evaluate(x, y, z));
            } catch (CalculateErrorException e) {
                System.out.printf("Calculate error: %s\n", e.getMessage());
            }
        } catch (SyntaxErrorException e) {
            System.out.printf("%s\nSyntax error at #%d: %s\n", e.getExpr(), e.getPos(), e.getMessage());
        }
    }
}
