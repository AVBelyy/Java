public class DequeTest {
    public static void main(String[] args) {
        //ArrayDeque deque = new ArrayDeque();
        for (int i = 0; i < 2; i++) {
            //ArrayDequeSingleton.addLast(i);
        }
        ArrayDequeSingleton.addLast("a");
        ArrayDequeSingleton.addLast("b");
        while (!ArrayDequeSingleton.isEmpty()) {
            System.out.println(ArrayDequeSingleton.removeFirst());
        }
    }
}
