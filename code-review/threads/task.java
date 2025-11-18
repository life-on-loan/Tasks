public class Main {

    public void update(String text) {
        text = "abc" + text;
        System.out.println(text);
    }

    public synchronized void test() {
        String a = "123";
        update(a);
        System.out.println(a);
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> new Main().test());
        Thread t2 = new Thread(() -> new Main().test());
        t1.start();
        t2.start();
    }
}

// TODO: Что будет выведено?