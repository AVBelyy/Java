public class MyLinkedStack extends MyAbstractStack implements MyStack {
    private Node head;

    public void push(Object element) {
        head = new Node(element, head);
        size++;
    }

    // doin' stupid things here
    protected Object popImpl() {
        Object o = head.value;
        head = head.next;
        return o;
    }

    // static for not importing *.this of superclasses
    // (aka LinkedStack.this)
    private static class Node {
        // more likely we won't change them after assigning
        private final Object value;
        private final Node next;

        // we defined at least one constructor - therefore, there'll
        // be no default one (e.g., no new Node())

        public Node(Object v, Node n) {
            value = v;
            next = n;
            // aka LinkedStack.this.head
            //head = null;
        }
    }

    public static void main(String[] args) {
    }
}
