public class QueueTest {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        for (int i = 0; i < 15; i++) {
            queue.push(i);
        }
        while (!queue.isEmpty()) {
            System.out.println(queue.pop());
        }
    }
}
