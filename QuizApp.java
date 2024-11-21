import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class QuizApp extends JFrame implements ActionListener {
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String userName;
    private String userId;
    private HashMap<String, User> userDatabase = new HashMap<>();
    private ArrayList<String[]> randomQuestions = new ArrayList<>();

    // 50 IT Engineering related questions
    private final String[][] questions = {
        {"What does CPU stand for?", "Central Process Unit", "Central Processing Unit", "Computer Personal Unit", "Central Processor Unit", "Central Processing Unit"},
        {"What does HTTP stand for?", "HyperText Transfer Protocol", "Hyper Transfer Text Protocol", "HyperText Transmission Protocol", "High Transfer Text Protocol", "HyperText Transfer Protocol"},
        {"Which company developed the Windows operating system?", "Apple", "Google", "Microsoft", "IBM", "Microsoft"},
        {"What does RAM stand for?", "Read Access Memory", "Random Access Memory", "Rapid Access Memory", "Reversible Access Memory", "Random Access Memory"},
        {"Which of these is an example of an operating system?", "Microsoft Word", "Linux", "Google Chrome", "Adobe Photoshop", "Linux"},
        {"What is the main function of the motherboard?", "Store data", "Process data", "Connect components", "Display output", "Connect components"},
        {"What does the acronym URL stand for?", "Universal Resource Locator", "Uniform Resource Locator", "Uniform Resource Link", "Universal Resource Link", "Uniform Resource Locator"},
        {"What is the primary function of an IP address?", "Identify a computer on the network", "Store user data", "Access the internet", "Control network security", "Identify a computer on the network"},
        {"What does VPN stand for?", "Virtual Private Network", "Virtual Public Network", "Visual Private Network", "Virtual Protected Network", "Virtual Private Network"},
        {"Which of these is a programming language?", "HTML", "CSS", "JavaScript", "Photoshop", "JavaScript"},
        {"What does SQL stand for?", "Structured Query Language", "Simple Query Language", "Standard Query Language", "Stored Query Language", "Structured Query Language"},
        // Add more questions...
    };

    // Components for Home page
    private JButton startQuizButton, checkScoreButton, exitButton;
    
    // Components for Quiz page
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextButton;
    private JProgressBar progressBar;  // Added progress bar

    public QuizApp() {
        setTitle("Quiz Application");
        setSize(500, 400);  // Increased size for better space
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Home page setup
        showHomePage();  // Show the home page when the app starts

        setVisible(true);
    }

    // Home page view with improved layout
    private void showHomePage() {
        getContentPane().removeAll();  // Remove any components from previous screens
        setLayout(new BorderLayout());

        // Title at the top
        JLabel titleLabel = new JLabel("Welcome to the Quiz App!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Bold and larger font
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.NORTH);

        // Panel to hold buttons in the center
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Buttons for the home page
        startQuizButton = new JButton("Start Quiz");
        checkScoreButton = new JButton("Check Score");
        exitButton = new JButton("Exit");

        startQuizButton.addActionListener(this);
        checkScoreButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Add some spacing between the buttons
        startQuizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkScoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the buttons to the panel
        buttonPanel.add(Box.createVerticalStrut(20)); // Top padding
        buttonPanel.add(startQuizButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Vertical spacing between buttons
        buttonPanel.add(checkScoreButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Bottom padding

        // Add the button panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);

        // Set a background color
        buttonPanel.setBackground(Color.LIGHT_GRAY);  // Light gray background for the buttons area

        revalidate();
        repaint();
    }

    // Quiz start process
    private void startQuiz() {
        // Ask for user name and generate a unique ID
        userName = JOptionPane.showInputDialog(this, "Enter your name:");
        if (userName == null || userName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!");
            return;
        }
        userId = generateUniqueId();
        JOptionPane.showMessageDialog(this, "Welcome, " + userName + "! Your ID is: " + userId);

        // Initialize for quiz
        score = 0;
        currentQuestionIndex = 0;
        randomQuestions = new ArrayList<>();
        Collections.addAll(randomQuestions, questions);
        Collections.shuffle(randomQuestions);
        randomQuestions = new ArrayList<>(randomQuestions.subList(0, 10)); // Pick 10 random questions

        // Switch to quiz page
        showQuizPage();
        loadQuestion();
    }

    private void showQuizPage() {
        // Clear the frame and add quiz components
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        // Add Progress Bar
        progressBar = new JProgressBar(0, randomQuestions.size());
        add(progressBar, BorderLayout.NORTH);

        // Add Question label and options
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Use BoxLayout for vertical layout

        questionLabel = new JLabel();
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(questionLabel);

        options = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            options[i].setAlignmentX(Component.CENTER_ALIGNMENT);  // Center-align answers
            panel.add(options[i]);
        }

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nextButton);

        add(panel, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }

    private void loadQuestion() {
        if (currentQuestionIndex < randomQuestions.size()) {
            // Clear any previous selections
            group.clearSelection();  // Clears the selection made by the user
            
            String[] currentQuestion = randomQuestions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion[0]);
            
            // Randomize answer options
            ArrayList<String> answerOptions = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                answerOptions.add(currentQuestion[i]);
            }
            Collections.shuffle(answerOptions); // Shuffle answers
    
            // Set the shuffled answers to the options
            for (int i = 0; i < 4; i++) {
                options[i].setText(answerOptions.get(i));
            }
            
            // Update progress bar value
            progressBar.setValue(currentQuestionIndex + 1);
        } else {
            showResults();  // Show results when all questions are answered
        }
    }
    
    private void resetSelections() {
        // Clear all radio button selections
        group.clearSelection();  // Clears the selection made by the user
        for (JRadioButton option : options) {
            option.setSelected(false);  // Explicitly unselect each option
        }
    }
    
    

    private void showResults() {
        JOptionPane.showMessageDialog(this, userName + ", Quiz Over! Your score: " + score + "/10");
        userDatabase.put(userId, new User(userName, score));
        resetSelections();  // Reset selections after showing results
        showHomePage();  // Show the home page again
    }
    

    // Check score
    private void checkScore() {
        String id = JOptionPane.showInputDialog(this, "Enter your ID:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty!");
            return;
        }

        User user = userDatabase.get(id);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Name: " + user.getName() + "\nScore: " + user.getScore() + "/10");
        } else {
            JOptionPane.showMessageDialog(this, "User not found!");
        }
    }

    // Exit the application
    private void exitQuiz() {
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startQuizButton) {
            startQuiz();
        } else if (e.getSource() == checkScoreButton) {
            checkScore();
        } else if (e.getSource() == exitButton) {
            exitQuiz();
        } else if (e.getSource() == nextButton) {
            // Validate answer
            String correctAnswer = randomQuestions.get(currentQuestionIndex)[5]; // Correct answer
            for (JRadioButton option : options) {
                if (option.isSelected() && option.getText().equals(correctAnswer)) {
                    score++;
                }
            }
            currentQuestionIndex++;
            loadQuestion();
        }
    }

    // Generate a unique ID for the user
    private String generateUniqueId() {
        return "" + new Random().nextInt(1000);
    }

    public static void main(String[] args) {
        new QuizApp();
    }

    // User class to hold user data
    static class User {
        private String name;
        private int score;

        public User(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}
