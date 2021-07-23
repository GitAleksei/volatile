package ru.netology.task1;

/**
 * The most useless box
 *
 */
public class App {
    private static final int TIMEOUT_USER_SWITCH = 1_000;
    private static final int TIME_TO_OF_SWITCH = 300;
    private static final int NUMBER_OF_SWITCH = 5;
    private static volatile boolean toggle;

    public static void main( String[] args ) {
        Thread userThread = new Thread(App::user, "Поток-пользователь");
        Thread gameThread = new Thread(App::game, "Поток-игра");

        userThread.start();
        gameThread.start();

        try {
            userThread.join();
        } catch (InterruptedException ignored) { }

        gameThread.interrupt();
    }

    private static void user() {
        for (int i = 0; i < NUMBER_OF_SWITCH; i++) {
            System.out.println(Thread.currentThread().getName() + " включил тумблер");
            toggle = true;
            try {
                Thread.sleep(TIMEOUT_USER_SWITCH);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static void game() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (toggle) {
                    Thread.sleep(TIME_TO_OF_SWITCH);
                    System.out.println(Thread.currentThread().getName() + " выключил тумблер");
                    toggle = false;
                }
            }
        } catch (InterruptedException ignored) {
        } finally {
            System.out.println(Thread.currentThread().getName() + " отключился");
        }
    }
}
