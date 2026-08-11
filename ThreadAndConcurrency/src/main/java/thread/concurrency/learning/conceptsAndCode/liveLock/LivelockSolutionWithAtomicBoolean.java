package thread.concurrency.learning.conceptsAndCode.liveLock;

import java.util.concurrent.atomic.AtomicBoolean;

public class LivelockSolutionWithAtomicBoolean {
    static class Person {
        private final String name;
        private boolean isSideStepping = true;

        Person(String name) {
            this.name = name;
        }

        public void tryToPass(Person other, AtomicBoolean firstClaimed) {
            while (isSideStepping) {
                // Try to claim the right to go first
                if (firstClaimed.compareAndSet(false, true)) {
                    // Successfully claimed → go first
                    isSideStepping = false;
                    System.out.println(name + " claimed and passed first");
                } else {
                    // Someone already claimed → wait for them
                    if (!other.isSideStepping) {
                        isSideStepping = false;
                        System.out.println(name + " passed after the other");
                    } else {
                        System.out.println(name + ": After you!");
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");
        AtomicBoolean firstClaimed = new AtomicBoolean(false);

        new Thread(() -> alice.tryToPass(bob, firstClaimed)).start();
        new Thread(() -> bob.tryToPass(alice, firstClaimed)).start();
    }
}
