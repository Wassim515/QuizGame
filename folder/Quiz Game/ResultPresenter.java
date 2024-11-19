public class ResultPresenter {
    public static void displayResults(int totalPoints, int correctAnswers, long totalTime) {
        System.out.println("Spelet är slut!");
        System.out.println("Totalpoäng: " + totalPoints);
        System.out.println("Antal rätt svar: " + correctAnswers);
        System.out.println("Totaltid: " + totalTime + " sekunder.");
    }
}