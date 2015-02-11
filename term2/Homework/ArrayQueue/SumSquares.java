public class SumSquares {
    static Double sumSquares(ArrayDeque deque) {
        Double ans = 0.0;
        int size = deque.size();
        for (int i = 0; i < size; i++) {
            Double elem = (Double) deque.removeLast();
            ans += elem * elem;
            deque.addFirst(elem);
        }
        return ans;
    }

    public static void main(String[] args) {
        ArrayDeque deque = new ArrayDeque();
        for (int i = 0; i < args.length; i++) {
            deque.addLast(Double.parseDouble(args[i]));
        }
        System.out.println(sumSquares(deque));
    }
}
