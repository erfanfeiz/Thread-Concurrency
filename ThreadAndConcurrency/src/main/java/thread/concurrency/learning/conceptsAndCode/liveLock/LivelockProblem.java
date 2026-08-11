package thread.concurrency.learning.conceptsAndCode.liveLock;

public class LivelockProblem {
    static class Person {
        private String name;
        private boolean isSideStepping = true;

        Person(String name) { this.name = name; }

        public void tryToPass(Person other) {
            while (isSideStepping) {
                if (other.isSideStepping) {
                    System.out.println(name + ": After you!");
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                } else {
                    isSideStepping = false;
                    System.out.println(name + " passed");
                }
            }
        }
    }

    public static void main(String[] args) {
        Person a = new Person("Alice");
        Person b = new Person("Bob");

        new Thread(() -> a.tryToPass(b)).start();
        new Thread(() -> b.tryToPass(a)).start();
    }
}
