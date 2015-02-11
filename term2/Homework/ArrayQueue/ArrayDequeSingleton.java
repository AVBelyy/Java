public class ArrayDequeSingleton {
    private static Object[] elements = new Object[2];
    private static int size = 0;
    private static int head = 0;
    private static int tail = 1;

    private static void ensureCapacity(int capacity) {
        if(elements.length < capacity) {
            Object[] e = new Object[2 * capacity];
            for (int i = 0; i < size; i++) {
                e[i] = elements[(i + head + 1) % elements.length];
            }
            head = e.length - 1;
            tail = elements.length;
            elements = e;
        }
    }

    static void addLast(Object o) {
        ensureCapacity(size + 1);
        elements[tail] = o;
        tail = (tail + 1) % elements.length;
        size++;
    }

    static void addFirst(Object o) {
        ensureCapacity(size + 1);
        elements[head] = o;
        head = (head - 1 + elements.length) % elements.length;
        size++;
    }

    static Object removeLast() {
        assert size > 0;
        tail = (tail - 1 + elements.length) % elements.length;
        Object o = elements[tail];
        elements[tail] = null;
        size--;
        return o;
    }

    static Object removeFirst() {
        assert size > 0;
        head = (head + 1) % elements.length;
        Object o = elements[head];
        elements[head] = null;
        size--;
        return o;
    }

    static Object peekLast() {
        assert size > 0;
        return elements[(tail - 1 + elements.length) % elements.length];
    }

    static Object peekFirst() {
        assert size > 0;
        return elements[(head + 1) % elements.length];
    }

    static int size() {
        return size;
    }

    static boolean isEmpty() {
        return size == 0;
    }
}
