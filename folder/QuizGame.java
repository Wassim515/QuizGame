package folder;
import java.util.Scanner;

public class QuizGame {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.loadQuestions(); // Ladda frågor

        System.out.println("\nVälkommen till Quiz-spelet!");
        System.out.println("Ange ditt namn:");
        String playerName = scanner.nextLine();
        System.out.println("Ange ålder:");
        int age = scanner.nextInt();
        System.out.println("Välkomen "+playerName+" Till spelet");
        
        boolean keepPlaying = true;

        while (keepPlaying) {
            System.out.println("\nVälj en kategori:");
            System.out.println("1. Alla kategorier");
            System.out.println("2. Sport");
            System.out.println("3. Vetenskap");
            System.out.println("4. Historia");
            System.out.println("5. Avsluta");
            System.out.print("Ditt val: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Hanterar newline efter int-inmatning

            switch (choice) {
                case 1:
                    quiz.startQuiz("Alla");
                    break;
                case 2:
                    quiz.startQuiz("Sport");
                    break;
                case 3:
                    quiz.startQuiz("Vetenskap");
                    break;
                case 4:
                    quiz.startQuiz("Historia");
                    break;
                case 5:
                    System.out.println("Tack för att du spelade!");
                    keepPlaying = false;
                    break;
                default:
                    System.out.println("Fel val. Försök igen!");
            }
        }
    }
}
