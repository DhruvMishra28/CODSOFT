import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGame extends JFrame implements ActionListener {
    private int targetNumber;
    private int attempts;
    private int maxAttempts = 5;
    private int score;
    private JTextField guessField;
    private JLabel messageLabel, attemptsLabel, scoreLabel, titleLabel;
    private JButton submitButton, playAgainButton;
    
    public NumberGame() {
        // JFrame settings
        setTitle("Number Guessing Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize game logic
        initializeGame();

        // Title Panel
        JPanel titlePanel = new JPanel();
        titleLabel = new JLabel("Guess the Number");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Main panel to hold the game content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Instruction Label
        JLabel instructionLabel = new JLabel("Guess the number between 1 and 100:");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(instructionLabel);

        // Guess input field
        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 16));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        mainPanel.add(guessField);

        // Message Label for feedback
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        mainPanel.add(messageLabel);

        // Submit Button
        submitButton = new JButton("Submit Guess");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        submitButton.addActionListener(this);
        mainPanel.add(submitButton);

        add(mainPanel, BorderLayout.CENTER);

        // Status Panel at the bottom for attempts and score
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 1));

        attemptsLabel = new JLabel("Attempts Left: " + (maxAttempts - attempts), SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(attemptsLabel);

        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusPanel.add(scoreLabel);

        add(statusPanel, BorderLayout.SOUTH);

        // Play Again Button (Initially Disabled)
        playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Arial", Font.PLAIN, 16));
        playAgainButton.setEnabled(false);
        playAgainButton.addActionListener(e -> playAgain());
        statusPanel.add(playAgainButton);
    }

    private void initializeGame() {
        // Generate random number
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;
        attempts = 0;
        score = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userInput = guessField.getText();
        try {
            int guess = Integer.parseInt(userInput);
            if (guess < 1 || guess > 100) {
                throw new NumberFormatException();
            }

            attempts++;
            if (guess == targetNumber) {
                messageLabel.setText("Correct! You guessed the number.");
                score += 10 - attempts;
                scoreLabel.setText("Score: " + score);
                endRound(true);
            } else if (guess < targetNumber) {
                messageLabel.setText("Too low! Try again.");
            } else {
                messageLabel.setText("Too high! Try again.");
            }
            attemptsLabel.setText("Attempts Left: " + (maxAttempts - attempts));

            if (attempts >= maxAttempts) {
                messageLabel.setText("Out of attempts! The number was " + targetNumber + ".");
                endRound(false);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number between 1 and 100.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void endRound(boolean won) {
        submitButton.setEnabled(false);
        playAgainButton.setEnabled(true);
        if (won) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won the round.", "Round Over", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Better luck next time!", "Round Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void playAgain() {
        initializeGame();
        guessField.setText("");
        messageLabel.setText("");
        attemptsLabel.setText("Attempts Left: " + (maxAttempts - attempts));
        scoreLabel.setText("Score: " + score);
        submitButton.setEnabled(true);
        playAgainButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGame game = new NumberGame();
            game.setVisible(true);
        });
    }
}
