public class Main {
    public static void main(String[] args) {
        String expr = args[0];
        int x = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        int z = Integer.parseInt(args[3]);
        Expression3 parsed = ExpressionParser.parse(expr);
        System.out.println(parsed.evaluate(x, y, z));
    }
}
