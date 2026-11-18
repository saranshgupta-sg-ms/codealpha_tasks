import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Map<String, String> knowledgeBase = new LinkedHashMap<String, String>();

    public static void main(String[] args) {
        loadKnowledgeBase();
        System.out.println("==================================");
        System.out.println("       CODEALPHA JAVA CHATBOT");
        System.out.println("==================================");
        System.out.println("Hello! I am a rule-based chatbot.");
        System.out.println("Ask me about Java, this internship, projects, or type 'bye' to exit.");

        while (true) {
            System.out.print("\nYou: ");
            String input = sc.nextLine().trim().toLowerCase(Locale.ROOT);
            if (input.equals("bye") || input.equals("exit") || input.equals("quit")) {
                System.out.println("Bot: Goodbye! Keep learning and coding.");
                break;
            }
            System.out.println("Bot: " + findAnswer(input));
        }
    }

    private static void loadKnowledgeBase() {
        knowledgeBase.put("hello", "Hello! How can I help you today?");
        knowledgeBase.put("hi", "Hi there! What would you like to know?");
        knowledgeBase.put("java", "Java is an object-oriented, class-based programming language used for many kinds of applications.");
        knowledgeBase.put("oops", "OOP stands for Object-Oriented Programming. Its four main ideas are encapsulation, inheritance, polymorphism and abstraction.");
        knowledgeBase.put("internship", "An internship provides practical experience. Follow your coordinator's instructions and submit your completed tasks through the official submission form.");
        knowledgeBase.put("codealpha", "CodeAlpha is the organization named in this internship task sheet. Check your official internship communication for current instructions.");
        knowledgeBase.put("project", "A good project should have a clear purpose, working features, readable code, and a README explaining how to run it.");
        knowledgeBase.put("github", "GitHub is a platform for hosting and managing code repositories. Create a repository, add your source files, commit, and push.");
        knowledgeBase.put("thank", "You're welcome! Happy coding.");
        knowledgeBase.put("help", "You can ask about Java, OOP, internship, CodeAlpha, projects, or GitHub.");
    }

    private static String findAnswer(String input) {
        if (input.isEmpty()) return "Please type a question so I can try to help.";
        String bestAnswer = null;
        int bestScore = 0;

        for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
            String keyword = entry.getKey();
            if (input.contains(keyword)) {
                int score = keyword.length();
                if (score > bestScore) {
                    bestScore = score;
                    bestAnswer = entry.getValue();
                }
            }
        }
        if (bestAnswer != null) return bestAnswer;

        if (input.contains("how are you")) return "I'm running correctly and ready to answer your questions!";
        if (input.contains("your name")) return "I am the CodeAlpha Java Chatbot demo.";
        return "Sorry, I don't know that yet. Try asking about Java, OOP, internship, projects, or GitHub.";
    }
}
