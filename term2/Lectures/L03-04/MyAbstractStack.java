public abstract class MyAbstractStack implements MyStack {
    protected int size;

    public Object pop() {
        assert size != 0;
        Object o = popImpl();
        size--;
        return o;
    }

    public Object peek() {
        Object value = pop();
        push(value);
        return value;
    }

    // it needs to be implemented in nesting class
    protected abstract Object popImpl();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
