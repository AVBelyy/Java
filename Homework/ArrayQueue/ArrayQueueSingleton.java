public class ArrayQueueSingleton {
    // Inv: [head..tail] = E[1]..E[size]
    //      this.size() >= 0
    private static Object[] elements = new Object[1];
    private static int size = 0;
    private static int head = 0;
    private static int tail = 0;

    private static void ensureCapacity(int capacity) {
        if(elements.length < capacity) {
            Object[] e = new Object[2 * capacity];
            for (int i = 0; i < size; i++) {
                e[i] = elements[(i + head) % elements.length];
            }
            head = 0;
            tail = elements.length;
            elements = e;
        }
    }

    static void push(Object o) {
        // s = s' + 1
        // tail = tail' + 1
        // E[tail'] = o
        // E[i] = E[i'] for all i in [head..tail')
        ensureCapacity(size + 1);
        elements[tail] = o;
        tail = (tail + 1) % elements.length;
        size++;
    }

    static Object pop() {
        // Pre: !this.isEmpty()
        // s = s' - 1
        // head = head' + 1
        // E[i] = E[i'] for all i in [head..tail]
        // r = E[head']
        assert size > 0;
        Object o = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return o;
    }

    static Object peek() {
        // (const)
        // Pre: this.size() > 0
        // r = E[head]
        assert size > 0;
        return elements[head];
    }

    static int size() {
        // (const)
        // r = size
        return size;
    }

    static boolean isEmpty() {
        // (const)
        // r = size == 0
        return size == 0;
    }
}
