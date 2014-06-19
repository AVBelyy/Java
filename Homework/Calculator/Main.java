public class Main {
    public static void main(String[] args) {
        double xvalue = Double.parseDouble(args[0]);
        double result = new Add(
                            new Subtract(
                                new Multiply(
                                    new Variable("x"),
                                    new Variable("x")
                                ),
                                new Multiply(
                                    new Variable("x"),
                                    new Const(2)
                                )
                            ),
                            new Const(1)
                        ).evaluate(xvalue, 0, 0);
        System.out.println(result);
        System.out.println(new Ternary(new Const(0), new Const(1), new Const(2)).evaluate(0, 0, 0));
        System.out.println(new UnaryMinus(new Variable("x")).evaluate(xvalue, 0, 0));
    }
}
