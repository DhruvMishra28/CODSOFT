import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StudentGradeCalculatorGUI extends JFrame implements ActionListener {
    private JTextField[] marksFields; // Array to hold marks fields
    private JButton calculateButton, resetButton;
    private JPanel inputPanel, resultPanel;
    private JLabel totalMarksLabel, averagePercentageLabel, gradeLabel;

    private final String[] subjects = {"Maths", "Geography", "Science", "Hindi", "English"};

    public StudentGradeCalculatorGUI() {
        // Set up the JFrame
        setTitle("Student Grade Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Create a title label
        JLabel titleLabel = new JLabel("Student Grade Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.NORTH);

        // Create the input panel
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(subjects.length + 2, 2, 10, 10));
        inputPanel.setBackground(new Color(240, 240, 240));

        // Initialize marksFields array
        marksFields = new JTextField[subjects.length];

        // Add fields for subject names and marks input
        for (int i = 0; i < subjects.length; i++) {
            inputPanel.add(new JLabel("Enter marks for " + subjects[i] + " (out of 100):"));
            marksFields[i] = new JTextField(); // Initialize field for the subject
            inputPanel.add(marksFields[i]);
        }

        // Add calculate and reset buttons
        calculateButton = new JButton("Calculate");
        resetButton = new JButton("Reset");
        calculateButton.setBackground(Color.GREEN);
        resetButton.setBackground(Color.RED);
        
        // Action listeners for buttons
        calculateButton.addActionListener(this);
        resetButton.addActionListener(e -> resetFields());

        inputPanel.add(calculateButton);
        inputPanel.add(resetButton);

        add(inputPanel, BorderLayout.CENTER);

        // Create result panel
        resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(4, 1, 10, 10));
        resultPanel.setBackground(new Color(200, 255, 200));

        totalMarksLabel = new JLabel("Total Marks: ");
        averagePercentageLabel = new JLabel("Average Percentage: ");
        gradeLabel = new JLabel("Grade: ");

        resultPanel.add(totalMarksLabel);
        resultPanel.add(averagePercentageLabel);
        resultPanel.add(gradeLabel);

        // Add the result panel to the frame
        add(resultPanel, BorderLayout.SOUTH);
        resultPanel.setVisible(false); // Initially hidden

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            calculateGrades();
        }
    }

    private void calculateGrades() {
        int totalMarks = 0;
        int numSubjects = subjects.length;

        // Calculate total marks
        for (int i = 0; i < numSubjects; i++) {
            int marks;
            try {
                marks = Integer.parseInt(marksFields[i].getText());
                if (marks < 0 || marks > 100) {
                    throw new NumberFormatException("Marks must be between 0 and 100.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid marks for " + subjects[i] + " (0-100).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            totalMarks += marks;
        }

        // Calculate average percentage
        double averagePercentage = (double) totalMarks / numSubjects;

        // Determine grade
        char grade;
        if (averagePercentage >= 90) {
            grade = 'A';
        } else if (averagePercentage >= 80) {
            grade = 'B';
        } else if (averagePercentage >= 70) {
            grade = 'C';
        } else if (averagePercentage >= 60) {
            grade = 'D';
        } else {
            grade = 'F'; // Failed
        }

        // Display results
        totalMarksLabel.setText("Total Marks: " + totalMarks + " out of " + (numSubjects * 100));
        averagePercentageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        gradeLabel.setText("Grade: " + grade);

        // Show result panel
        resultPanel.setVisible(true);
    }

    private void resetFields() {
        for (JTextField marksField : marksFields) {
            marksField.setText("");
        }
        totalMarksLabel.setText("Total Marks: ");
        averagePercentageLabel.setText("Average Percentage: ");
        gradeLabel.setText("Grade: ");
        resultPanel.setVisible(false); // Hide result panel
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeCalculatorGUI());
    }
}
