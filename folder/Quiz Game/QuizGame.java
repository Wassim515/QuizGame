import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class QuizGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Question> questions = new ArrayList<>();

        // Skapa frågor
        questions.add(new Question("Vad är huvudstaden i Frankrike?", 
            new String[]{"1. Paris", "2. London", "3. Berlin", "4. Madrid"}, "1", 10, "Geografi"));
        questions.add(new Question("Vilken är världens största ö?", 
            new String[]{"1. Grönland", "2. Australien", "3. Borneo", "4. Madagaskar"}, "1", 10, "Geografi"));
        questions.add(new Question("Vad är 5 + 7?", 
            new String[]{"1. 10", "2. 11", "3. 12", "4. 13"}, "3", 10, "Matematik"));
            questions.add(new Question("Vad är 5 + 7?", 
            new String[]{"1. 10", "2. 11", "3. 12", "4. 13"}, "1", 10, "Historia"));

        // Huvudmeny
        System.out.println("Välkommen till Quiz Game!");
        System.out.println("1. Starta spelet");
        System.out.println("2.Visa highscore");
        System.out.println("3. Alla kategorier\n2. Välj en specifik kategori\n3. Avsluta");
        int menuChoice = scanner.nextInt();
        scanner.nextLine(); // Konsumera newline

        List<Question> selectedQuestions = new ArrayList<>();
        if (menuChoice == 1) {
            System.out.println("Ange ditt namn:");
            String playerName = scanner.nextLine();
            System.out.print("Ange din ålder");
            selectedQuestions.addAll(questions);
        } else if (menuChoice == 2) {
            System.out.println("Välj kategori: Geografi eller Matematik");
            String chosenCategory = scanner.nextLine();
            for (Question question : questions) {
                if (question.getCategory().equalsIgnoreCase(chosenCategory)) {
                    selectedQuestions.add(question);
                }
            }
        } else {
            System.out.println("Avslutar spelet.");
            return;
        }

        // Spelstart
        int totalPoints = 0;
        int correctAnswers = 0;
        long startTime = System.currentTimeMillis();

        for (Question question : selectedQuestions) {
            System.out.println(question.getQuestionText());
            for (String option : question.getOptions()) {
                System.out.println(option);
            }

            long questionStartTime = System.currentTimeMillis();
            String userAnswer = getTimedAnswer(scanner, 10); // 10 sekunders tidsgräns

            if (userAnswer == null) {
                System.out.println("Tiden gick ut! Nästa fråga.\n");
                continue;
            }

            long timeTaken = (System.currentTimeMillis() - questionStartTime) / 1000;
            int points = Math.max(0, question.getMaxPoints() - (int) timeTaken);

            if (question.isCorrectAnswer(userAnswer)) {
                totalPoints += points;
                correctAnswers++;
                System.out.println("Rätt svar! Du fick " + points + " poäng.\n");
            } else {
                System.out.println("Fel svar. Rätt svar är: " + question.getCorrectAnswer() + "\n");
            }
        }

        // Resultatskärm
        long totalTime = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("Spelet är slut!");
        System.out.println("Totalpoäng: " + totalPoints);
        System.out.println("Antal rätt svar: " + correctAnswers);
        System.out.println("Totaltid: " + totalTime + " sekunder.");
    }

    // Metod för tidsbegränsat svar
    public static String getTimedAnswer(Scanner scanner, int timeLimitSeconds) {
        final String[] userAnswer = {null};
        Thread inputThread = new Thread(() -> userAnswer[0] = scanner.nextLine());

        inputThread.start();
        try {
            inputThread.join(timeLimitSeconds * 1000);
        } catch (InterruptedException e) {
            System.out.println("Ett fel uppstod med tidsbegränsningen.");
        }

        if (inputThread.isAlive()) {
            inputThread.interrupt();
        }

        return userAnswer[0];
    }
}
