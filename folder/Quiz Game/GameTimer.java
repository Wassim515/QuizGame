import java.util.Scanner;

public class GameTimer {
    public static String getTimedAnswer(Scanner scanner, int timeLimitSeconds) {
        final String[] userAnswer = {null};
        Thread inputThread = new Thread(() -> userAnswer[0] = scanner.nextLine());

        inputThread.start();
        try {
            inputThread.join(timeLimitSeconds * 1000);
        } catch (InterruptedException e) {
            System.out.println("Timer fel.");
        }

        if (inputThread.isAlive()) {
            inputThread.interrupt();
        }
        return userAnswer[0];
    }

    public static int calculatePoints(int maxPoints, long timeTaken) {
        return Math.max(0, maxPoints - (int) timeTaken);
    }
}
wwwddwd
