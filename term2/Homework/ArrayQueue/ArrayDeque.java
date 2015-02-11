public class ArrayDeque {
    // Inv: this.size() >= 0
    //      deque = (E[head], E[head + 1] .. E[tail])
    //      if i <= head or i >= tail: E[i] = null
    private Object[] elements = new Object[2];
    private int size = 0;
    private int head = 0;
    private int tail = 1;

    private void ensureCapacity(int capacity) {
        if (elements.length < capacity) {
            Object[] e = new Object[2 * capacity];
            for (int i = 0; i < size; i++) {
                e[i] = elements[(i + head + 1) % elements.length];
            }
            head = e.length - 1;
            tail = elements.length;
            elements = e;
        }
    }

    void addLast(Object o) {
        // s = s' + 1
        // tail = tail' + 1
        // E[tail'] = o
        // E[i] = E[i'] for all i in (head..tail')
        ensureCapacity(size + 1);
        elements[tail] = o;
        tail = (tail + 1) % elements.length;
        size++;
    }

    void addFirst(Object o) {
        // s = s' + 1
        // head = head' - 1
        // E[head'] = o
        // E[i] = E[i'] for all i in (head'..tail)
        ensureCapacity(size + 1);
        elements[head] = o;
        head = (head - 1 + elements.length) % elements.length;
        size++;
    }

    Object removeLast() {
        // Pre: !this.isEmpty()
        // s = s' - 1
        // tail = tail' - 1
        // E[i] = E[i'] for all i in (head..tail)
        // r = E[tail]
        assert size > 0;
        tail = (tail - 1 + elements.length) % elements.length;
        Object o = elements[tail];
        elements[tail] = null;
        size--;
        return o;
    }

    Object removeFirst() {
        // Pre: !this.isEmpty()
        // s = s' - 1
        // head = head' + 1
        // E[i] = E[i'] for all i in (head..tail)
        // r = E[head]
        assert size > 0;
        head = (head + 1) % elements.length;
        Object o = elements[head];
        elements[head] = null;
        size--;
        return o;
    }

    Object peekLast() {
        // (const)
        // Pre: this.size() > 0
        // r = E[tail - 1]
        assert size > 0;
        return elements[(tail - 1 + elements.length) % elements.length];
    }

    Object peekFirst() {
        // (const)
        // Pre: this.size() > 0
        // r = E[head + 1]
        assert size > 0;
        return elements[(head + 1) % elements.length];
    }

    int size() {
        // (const)
        // r = size
        return size;
    }

    boolean isEmpty() {
        // (const)
        // r = size == 0
        return size == 0;
    }
}
