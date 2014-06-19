public class ArrayDequeADT {
    private Object[] elements = new Object[2];
    private int size = 0;
    private int head = 0;
    private int tail = 1;

    private static void ensureCapacity(ArrayDequeADT deque, int capacity) {
        if(deque.elements.length < capacity) {
            Object[] e = new Object[2 * capacity];
            for (int i = 0; i < deque.size; i++) {
                e[i] = deque.elements[(i + deque.head + 1) % deque.elements.length];
            }
            deque.head = e.length - 1;
            deque.tail = deque.elements.length;
            deque.elements = e;
        }
    }

    static void addLast(ArrayDequeADT deque, Object o) {
        ensureCapacity(deque, deque.size + 1);
        deque.elements[deque.tail] = o;
        deque.tail = (deque.tail + 1) % deque.elements.length;
        deque.size++;
    }

    static void addFirst(ArrayDequeADT deque, Object o) {
        ensureCapacity(deque, deque.size + 1);
        deque.elements[deque.head] = o;
        deque.head = (deque.head - 1 + deque.elements.length) % deque.elements.length;
        deque.size++;
    }

    static Object removeLast(ArrayDequeADT deque) {
        assert deque.size > 0;
        deque.tail = (deque.tail - 1 + deque.elements.length) % deque.elements.length;
        Object o = deque.elements[deque.tail];
        deque.elements[deque.tail] = null;
        deque.size--;
        return o;
    }

    static Object removeFirst(ArrayDequeADT deque) {
        assert deque.size > 0;
        deque.head = (deque.head + 1) % deque.elements.length;
        Object o = deque.elements[deque.head];
        deque.elements[deque.head] = null;
        deque.size--;
        return o;
    }

    static Object peekLast(ArrayDequeADT deque) {
        assert deque.size > 0;
        return deque.elements[(deque.tail - 1 + deque.elements.length) % deque.elements.length];
    }

    static Object peekFirst(ArrayDequeADT deque) {
        assert deque.size > 0;
        return deque.elements[(deque.head + 1) % deque.elements.length];
    }

    static int size(ArrayDequeADT deque) {
        return deque.size;
    }

    static boolean isEmpty(ArrayDequeADT deque) {
        return deque.size == 0;
    }
}
