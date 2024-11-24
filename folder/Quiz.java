package folder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Quiz {
    private List<Question> questions = new ArrayList<>();
    private int score = 0;
    private int correctAnswers = 0;
    private String selectedDifficulty = "Lätt " + "Medel " + "Svår";


    public void loadQuestions() {
            questions.add(new Question(
            "Vilken sport spelar Zlatan Ibrahimović?",
            Arrays.asList("Fotboll", "Basket", "Tennis", "Hockey"), 
            1,
            "Sport",
            "Lätt"
            ));
           
            questions.add(new Question("Vem vann ballandor 2024",
            Arrays.asList("Rodri", "Mppabe", "Ronaldo", "Vini"),
            1,
            "Sport",
            "Medel"
             ));
            
            questions.add(new Question( "Vilket land vann VM i fotboll 2014?",
            Arrays.asList("Brasilien", "Argentina", "Tyskland", "Frankrike"),
            3,  
            "Sport",
            "Svår"
             ));

            questions.add(new Question(
            "Vad är vattnets kemiska formel?",
            Arrays.asList("H2O", "CO2", "O2", "N2"),
            1,  
            "Vetenskap",
            "Lätt"
            ));

            questions.add(new Question(
            "Vilket är det största planet i vårt solsystem?",
            Arrays.asList("Mars", "Jupiter", "Saturnus", "Venus"),
            2,
            "Vetenskap",
            "Medel"
            ));

            questions.add(new Question(
            "Vad är den primära komponenten i jordens kärna?",
            Arrays.asList("Järn", "Koppar", "Aluminium", "Kisel"),
            1,  
            "Vetenskap",
            "Svår"
            ));

            questions.add(new Question(
            "När startade andra världskriget?",
            Arrays.asList("1914", "1939", "1945", "1923"),
            2,
            "Historia",
            "Lätt"
            ));

            questions.add(new Question(
            "Vilket land var först med att ge kvinnor rösträtt?",
            Arrays.asList("Sverige", "Nya Zeeland", "USA", "Storbritannien"),
            2,  
            "Historia",
            "Medel"
            ));

            questions.add(new Question(
            "Vad var det ursprungliga namnet på det som idag kallas Sverige innan landet enades?",
            Arrays.asList("Svealand", "Götaland", "Sverige", "Svitjod"),
            4,  
            "Historia",
            "Svår"
            ));
    }

    public void startQuiz(String category) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nVälj en svårighetsnivå:");
        System.out.println("1. Lätt");
        System.out.println("2. Medel");
        System.out.println("3. Svår");
        System.out.print("Ditt val: ");
        int difficultyChoice = scanner.nextInt();

        switch (difficultyChoice) {
            case 1 -> selectedDifficulty = "Lätt";
            case 2 -> selectedDifficulty = "Medel";
            case 3 -> selectedDifficulty = "Svår";
            default -> {
                System.out.println("Ogiltigt val. Standardnivån Lätt används.");
                selectedDifficulty = "Lätt";
            }
        }

        List<Question> selectedQuestions = getQuestionsByCategoryAndDifficulty(category, selectedDifficulty);
        if (selectedQuestions.isEmpty()) {
            System.out.println("\nInga frågor tillgängliga för kategorin " + category + " och svårighetsnivån " + selectedDifficulty);
            return;
        }
        
        score = 0;
        correctAnswers = 0;
        System.out.println("\nStartar quiz i kategorin: " + category + "(Nivå: "  + selectedDifficulty + ")");
        long startTime = System.currentTimeMillis();

        for (Question q : selectedQuestions) {
            askQuestionWithTimer(q);
        }

        long endTime = System.currentTimeMillis();
        showResults(selectedQuestions.size(), endTime - startTime);
    }

    private void askQuestionWithTimer(Question question) {
        System.out.println("\n" + question.getQuestion());
        List<String> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        
        
        int timeLimit = 10; // Sekunder
        int maxPoints = 8;
        final boolean[] answered = {false};
        final int[] userAnswer = {-1};

        Thread timerThread = new Thread(() -> {
            try {
                for (int i = timeLimit; i > 0; i--) {
                    if (answered[0]) break;
                    System.out.print("\rTid kvar: " + i + " sekunder  ");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        timerThread.start();

        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nDitt svar: ");
            userAnswer[0] = scanner.nextInt();
            answered[0] = true;
        });


        inputThread.start();

        try {
            inputThread.join(timeLimit * 1000L); // Vänta på input eller tidens slut
            if (inputThread.isAlive()) {
                inputThread.interrupt(); // Avbryt om tiden är slut
            }
            timerThread.interrupt(); // Avsluta timern
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (!answered[0]) {
            System.out.println("\nTiden är ute! Nästa fråga.");
            return; // Hoppa till nästa fråga
        }

        // Räkna ut poäng baserat på återstående tid
        int remainingTime = Math.max(0, timeLimit - (int) (timerThread.getId() % timeLimit));
        int earnedPoints = (int) ((remainingTime / (double) timeLimit) * maxPoints);

        if (question.isCorrect(userAnswer[0])) {
            System.out.println("Rätt svar!");
            score += earnedPoints;
            correctAnswers++;
        } else {
            System.out.println("Fel svar! Rätt svar var: " + options.get(question.getCorrectOption() - 1));
        }

        System.out.println("Du tjänade " + earnedPoints + " poäng för denna fråga.");
    }

    private List<Question> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
        List<Question> filtered = new ArrayList<>();
        for (Question q : questions) {
            if ((category.equalsIgnoreCase("Alla") || q.getCategory().equalsIgnoreCase(category)) &&
                q.getDifficulty().equalsIgnoreCase(difficulty)) {
                filtered.add(q);
            }
        }
        return filtered;
    }
    

    private void showResults(int totalQuestions, long timeTaken) {
        System.out.println("\n*** Resultat ***");
        System.out.println("Totalt antal frågor: " + totalQuestions);
        System.out.println("Antal rätta svar: " + correctAnswers);
        System.out.println("Totalt Poäng: " + score);
        System.out.println("Tid: " + (timeTaken / 1000) + " sekunder");
    }
}
