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
        {"What is the full form of RAM?", "Random Access Memory", "Random Allocated Memory", "Read Access Memory", "Run Access Memory", "Random Access Memory"},
        {"What does LAN stand for?", "Local Area Network", "Large Area Network", "Long Area Network", "Low Area Network", "Local Area Network"},
        {"Which programming language is considered the 'mother' of all languages?", "C", "Java", "Python", "Assembly", "C"},
        {"What does HTML stand for?", "HyperText Markup Language", "Hyper Transfer Markup Language", "HyperText Make Language", "HyperTransfer Make Language", "HyperText Markup Language"},
        {"Which of these is an operating system?", "Python", "Linux", "MySQL", "HTML", "Linux"},
        {"What does GUI stand for?", "Graphical User Interface", "General User Interface", "Graphic User Interaction", "Graphical User Internet", "Graphical User Interface"},
        {"Which company developed the Java programming language?", "Microsoft", "Apple", "Oracle", "Sun Microsystems", "Sun Microsystems"},
        {"What does SQL stand for?", "Structured Query Language", "Simple Query Language", "Standard Query Language", "Stored Query Language", "Structured Query Language"},
        {"What is a byte in terms of data?", "8 bits", "4 bits", "16 bits", "32 bits", "8 bits"},
        {"Which programming language is used for web development?", "HTML", "JavaScript", "CSS", "All of the above", "All of the above"},
        {"What is the full form of API?", "Application Programming Interface", "Application Program Interaction", "Artificial Programming Interface", "Advanced Programming Interface", "Application Programming Interface"},
        {"What does DNS stand for?", "Domain Name System", "Domain Naming Service", "Digital Network System", "Direct Name Service", "Domain Name System"},
        {"Which language is primarily used for data science?", "Python", "C++", "Java", "Ruby", "Python"},
        {"Which of the following is a relational database?", "MongoDB", "SQL", "Redis", "Cassandra", "SQL"},
        {"Which of the following is a cloud computing platform?", "AWS", "MySQL", "Linux", "Java", "AWS"},
        {"Which protocol is used to send emails?", "SMTP", "HTTP", "FTP", "IMAP", "SMTP"},
        {"Which of the following is a NoSQL database?", "MongoDB", "SQL", "Oracle", "PostgreSQL", "MongoDB"},
        {"What does HTTP stand for?", "HyperText Transfer Protocol", "Hyper Transfer Text Protocol", "HyperText Transfer Package", "Hyper Transfer Type Protocol", "HyperText Transfer Protocol"},
        {"What does CSS stand for?", "Cascading Style Sheets", "Creative Style Sheets", "Computer Style Sheets", "Cascading Sheet Styles", "Cascading Style Sheets"},
        {"What is the full form of URL?", "Uniform Resource Locator", "Uniform Resource Link", "Universal Resource Locator", "Universal Resource Link", "Uniform Resource Locator"},
        {"Which company developed the C# language?", "Apple", "Google", "Microsoft", "IBM", "Microsoft"},
        {"What is the main function of an operating system?", "To manage software resources", "To manage hardware resources", "Both", "None", "Both"},
        {"Which of these is a version control system?", "Git", "Docker", "Kubernetes", "Jenkins", "Git"},
        {"What is the full form of IoT?", "Internet of Things", "Internal of Things", "Internet of Technology", "Internal of Technology", "Internet of Things"},
        {"What is the main purpose of a firewall?", "To monitor and control incoming and outgoing network traffic", "To enhance computer speed", "To compress data", "To create backups", "To monitor and control incoming and outgoing network traffic"},
        {"What is a compiler?", "A software that translates code into machine language", "A program that checks errors", "A tool for optimizing code", "A tool for debugging code", "A software that translates code into machine language"},
        {"What does JVM stand for?", "Java Virtual Machine", "Java Verified Machine", "Java Version Manager", "Java Visual Machine", "Java Virtual Machine"},
        {"Which of the following is a type of cyber attack?", "Phishing", "SSH", "SSL", "DNS", "Phishing"},
        {"Which of these is an open-source operating system?", "Windows", "MacOS", "Linux", "iOS", "Linux"},
        {"What does VPN stand for?", "Virtual Private Network", "Virtual Public Network", "Visible Private Network", "Virtual Private Node", "Virtual Private Network"},
        {"What is the full form of XML?", "eXtensible Markup Language", "eXchangeable Markup Language", "eXecutable Markup Language", "eXtended Markup Language", "eXtensible Markup Language"},
        {"Which algorithm is used for searching in a sorted array?", "Binary Search", "Linear Search", "Depth-First Search", "Breadth-First Search", "Binary Search"},
        {"What is an IP address?", "A unique address assigned to a device on a network", "A protocol used for secure communication", "A software tool for debugging", "A type of malware", "A unique address assigned to a device on a network"},
        {"What does TCP stand for?", "Transmission Control Protocol", "Transfer Communication Protocol", "Total Control Protocol", "Transmission Communication Protocol", "Transmission Control Protocol"},
        {"Which of these is a networking protocol?", "HTTP", "Python", "CSS", "HTML", "HTTP"},
        {"Which programming language is used for Android app development?", "Swift", "Kotlin", "Ruby", "Perl", "Kotlin"},
        {"Which data structure works in a Last In, First Out (LIFO) manner?", "Stack", "Queue", "Array", "Tree", "Stack"},
        {"Which of the following is a machine learning library?", "TensorFlow", "React", "Node.js", "JQuery", "TensorFlow"},
        {"What does ALU stand for?", "Arithmetic Logic Unit", "Application Layer Unit", "Automatic Logic Unit", "Advanced Logic Unit", "Arithmetic Logic Unit"},
        {"Which of the following is a frontend framework?", "React", "Node.js", "Django", "Flask", "React"},
        {"Which company created the Go programming language?", "Microsoft", "Apple", "Google", "Facebook", "Google"},
        {"What is a blockchain?", "A distributed ledger technology", "A centralized database", "A type of malware", "A programming language", "A distributed ledger technology"},
        {"What is the purpose of an API?", "To allow different software applications to communicate", "To make software faster", "To create visual interfaces", "To store data", "To allow different software applications to communicate"},
        {"Which of the following is a backend framework?", "Node.js", "React", "Vue.js", "Angular", "Node.js"},
        {"Which of these languages is used for artificial intelligence?", "Python", "HTML", "CSS", "SQL", "Python"},
        {"What does SSL stand for?", "Secure Sockets Layer", "Safe Security Layer", "Secure Safety Layer", "Secure Sockets Link", "Secure Sockets Layer"}
    };

    // Components for Home page
    private JButton startQuizButton, checkScoreButton, exitButton;
    
    // Components for Quiz page
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextButton;

    public QuizApp() {
        setTitle("Quiz Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        // Home page setup
        startQuizButton = new JButton("Start Quiz");
        checkScoreButton = new JButton("Check Score");
        exitButton = new JButton("Exit");

        startQuizButton.addActionListener(this);
        checkScoreButton.addActionListener(this);
        exitButton.addActionListener(this);

        add(startQuizButton);
        add(checkScoreButton);
        add(exitButton);

        setVisible(true);
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
        setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel();
        add(questionLabel);

        options = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            add(options[i]);
        }

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        add(nextButton);

        revalidate();
        repaint();
    }

    private void loadQuestion() {
        if (currentQuestionIndex < randomQuestions.size()) {
            String[] currentQuestion = randomQuestions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion[0]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(currentQuestion[i + 1]);
            }
        } else {
            showResults();
        }
    }

    private void showResults() {
        JOptionPane.showMessageDialog(this, userName + ", Quiz Over! Your score: " + score + "/10");
        userDatabase.put(userId, new User(userName, score));
        showHomePage();
    }

    // Home page view
    private void showHomePage() {
        getContentPane().removeAll();
        setLayout(new GridLayout(3, 1));

        add(startQuizButton);
        add(checkScoreButton);
        add(exitButton);

        revalidate();
        repaint();
    }

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
            if (currentQuestionIndex < randomQuestions.size()) {
                String selectedAnswer = null;
                for (JRadioButton option : options) {
                    if (option.isSelected()) {
                        selectedAnswer = option.getText();
                        break;
                    }
                }

                if (selectedAnswer != null && selectedAnswer.equals(randomQuestions.get(currentQuestionIndex)[5])) {
                    score++;
                }

                currentQuestionIndex++;
                group.clearSelection();
                loadQuestion();
            }
        }
    }

    // Generate a unique ID
    private String generateUniqueId() {
        return "U" + new Random().nextInt(10000);
    }

    // Inner class to represent a User
    private static class User {
        private final String name;
        private final int score;

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

    public static void main(String[] args) {
        new QuizApp();
    }
}

