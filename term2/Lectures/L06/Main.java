import org.retloko.test.Ololo;
import static org.retloko.test.Ololo.PI;

public class Main extends Ololo {
    int x = 12;

    class A {
        int x = 10;
        void f() {
            System.out.println(x);
            System.out.println(Main.this.x);
            System.out.println(Main.super.x);
        }
    }

    static class B {
        // no 'super' here
        int x = 12;
    }

    org.retloko.test.Ololo azaza;

    interface If {
        void run();
    }

    void run() {
        System.err.println(x3);
        Main c = null;
        System.err.println(c.x3);
        System.err.println(azaza.x1);

        A a = new A();
        a.f();
        Main b = new Main(3);
        (b.new A()).f();
    }

    void f() {}

    void someVoid() {
        final int x = 0;
        new If() {
            public void run() {
            }
        }.run();
        new Thread(new Runnable() { // Thread takes interface, which
                                    // have just created as an anonym
                                    // class
            @Override
            public void run() {
            }
        });
    }

    public Main(int x) {
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    System.out.println(1);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    System.out.println(2);
                }
            }
        });
        t1.start();
        t2.start();
        System.out.println(Ololo.PI);
    }
}
