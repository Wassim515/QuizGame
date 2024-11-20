public class Question {
    private String questionText;
    private String[] options;
    private String correctAnswer;
    private int maxPoints;
    private String category;

    public Question(String questionText, String[] options, String correctAnswer, int maxPoints, String category) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.maxPoints = maxPoints;
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public String getCategory() {
        return category;
    }

    public boolean isCorrectAnswer(String userAnswer) {
        return correctAnswer.equalsIgnoreCase(userAnswer.trim());
    }
}
