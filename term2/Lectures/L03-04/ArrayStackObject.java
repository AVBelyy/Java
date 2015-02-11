// Java is an OO language
// It's all about: incapsulation, inheritance, polymorphism
// Incapsulation: distancing world-visible contract-ensured methods and properties from private implementation-connected with modifiers like private, final, etc

public class ArrayStackObject extends MyAbstractStack implements MyStack {
    // Inv: e1..es
    //      this.size() >= 0
    private  Object[] elements = new Object[10];
    private  int size = 0;

    private  void ensureCapacity(int capacity) {
        if(elements.length >= capacity) {
            // everything's fine
        } else {
            Object[] e = new Object[2 * capacity];
            for (int i = 0; i < size; i++) {
                e[i] = elements[i];
            }
            elements = e;
        }
    }

     public void push(Object o) {
        // s = s' + 1
        // Es = o
        // Ei = Ei' for all i in [1..s']
        ensureCapacity(size + 1);
        elements[size++] = o;
    }

    protected Object popImpl() {
        Object o = elements[size - 1];
        elements[size - 1] = null;
        return o;
    }

    @Override
    public int size() {
        return size;
    }
}
