package folder;
import java.util.List;


public class Question {
    private String question;
    private List<String> options;
    private int correctOption;
    private String category;
    private String difficulty;
    

    public Question(String question, List<String> options, int correctOption, String category) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public String getCategory() {
        return category;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public boolean isCorrect(int choice) {
        return choice == correctOption;
    }
}
