public class ArrayQueueADT {
    // Inv: [head..tail] = E[1]..E[size]
    //      this.size() >= 0
    private Object[] elements = new Object[1];
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if(queue.elements.length < capacity) {
            Object[] e = new Object[2 * capacity];
            for (int i = 0; i < queue.size; i++) {
                e[i] = queue.elements[(i + queue.head) % queue.elements.length];
            }
            queue.head = 0;
            queue.tail = queue.elements.length;
            queue.elements = e;
        }
    }

    static void push(ArrayQueueADT queue, Object o) {
        // s = s' + 1
        // tail = tail' + 1
        // E[tail'] = o
        // E[i] = E[i'] for all i in [head..tail')
        ensureCapacity(queue, queue.size + 1);
        queue.elements[queue.tail] = o;
        queue.tail = (queue.tail + 1) % queue.elements.length;
        queue.size++;
    }

    static Object pop(ArrayQueueADT queue) {
        // Pre: !this.isEmpty()
        // s = s' - 1
        // head = head' + 1
        // E[i] = E[i'] for all i in [head..tail]
        // r = E[head']
        assert queue.size > 0;
        Object o = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return o;
    }

    static Object peek(ArrayQueueADT queue) {
        // (const)
        // Pre: this.size() > 0
        // r = E[head]
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    static int size(ArrayQueueADT queue) {
        // (const)
        // r = size
        return queue.size;
    }

    static boolean isEmpty(ArrayQueueADT queue) {
        // (const)
        // r = size == 0
        return queue.size == 0;
    }
}
