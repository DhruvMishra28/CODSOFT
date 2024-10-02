import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

class BankAccount {
    private double balance;
    private String transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.transactionHistory = "Transaction History:\n";
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory += "Deposited: " + amount + "\n";
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory += "Withdrawn: " + amount + "\n";
            return true;
        } else {
            return false;
        }
    }

    public double checkBalance() {
        return balance;
    }

    public String getTransactionHistory() {
        return transactionHistory;
    }
}

class User {
    private String pin;
    private BankAccount account;

    public User(String pin) {
        this.pin = pin;
        this.account = new BankAccount();
    }

    public String getPin() {
        return pin;
    }

    public BankAccount getAccount() {
        return account;
    }
}

public class ATMApplication extends JFrame implements ActionListener {
    private Map<String, User> users;
    private User currentUser;
    private JTextField depositField;
    private JTextField withdrawField;
    private JTextField pinField;
    private JLabel balanceLabel;
    private JTextArea transactionHistoryArea;

    public ATMApplication() {
        users = new HashMap<>();
        users.put("user1", new User("1234")); // Example user with PIN 1234

        // Set up the frame
        setTitle("ATM Interface");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.red);
        headerPanel.add(new JLabel("Welcome to the ATM", JLabel.CENTER));
        headerPanel.setPreferredSize(new Dimension(400, 50));
        headerPanel.setForeground(Color.WHITE);
        add(headerPanel, BorderLayout.NORTH);

        // Create main panel for inputs and buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create components
        JLabel pinLabel = new JLabel("Enter PIN:");
        pinField = new JTextField();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        JLabel depositLabel = new JLabel("Deposit Amount:");
        depositField = new JTextField();
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);

        JLabel withdrawLabel = new JLabel("Withdraw Amount:");
        withdrawField = new JTextField();
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);

        JLabel checkBalanceLabel = new JLabel("Current Balance:");
        balanceLabel = new JLabel("0.00");

        // Add components to the main panel
        mainPanel.add(pinLabel);
        mainPanel.add(pinField);
        mainPanel.add(loginButton);
        mainPanel.add(depositLabel);
        mainPanel.add(depositField);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawLabel);
        mainPanel.add(withdrawField);
        mainPanel.add(withdrawButton);
        mainPanel.add(checkBalanceLabel);
        mainPanel.add(balanceLabel);

        add(mainPanel, BorderLayout.CENTER);

        // Create transaction history area
        transactionHistoryArea = new JTextArea(5, 30);
        transactionHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionHistoryArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Set button colors and styles
        styleButtons(loginButton, depositButton, withdrawButton);

        // Set the visibility
        setVisible(true);
    }

    private void styleButtons(JButton... buttons) {
        for (JButton button : buttons) {
            button.setBackground(Color.BLUE);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            button.setPreferredSize(new Dimension(100, 30)); // Set smaller button size
        }
    }

    private void resetFields() {
        depositField.setText("");
        withdrawField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("Login")) {
            String pin = pinField.getText();
            currentUser = users.get("user1"); // Hardcoded user for demo
            if (currentUser != null && currentUser.getPin().equals(pin)) {
                JOptionPane.showMessageDialog(this, "Login successful.");
                balanceLabel.setText(String.format("%.2f", currentUser.getAccount().checkBalance()));
                transactionHistoryArea.setText(currentUser.getAccount().getTransactionHistory());
                resetFields();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid PIN. Try again.");
            }
        } else if (command.equals("Deposit")) {
            try {
                double amount = Double.parseDouble(depositField.getText());
                if (amount > 0) {
                    currentUser.getAccount().deposit(amount);
                    balanceLabel.setText(String.format("%.2f", currentUser.getAccount().checkBalance()));
                    transactionHistoryArea.setText(currentUser.getAccount().getTransactionHistory());
                    resetFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        } else if (command.equals("Withdraw")) {
            try {
                double amount = Double.parseDouble(withdrawField.getText());
                if (currentUser.getAccount().withdraw(amount)) {
                    balanceLabel.setText(String.format("%.2f", currentUser.getAccount().checkBalance()));
                    transactionHistoryArea.setText(currentUser.getAccount().getTransactionHistory());
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.");
                }
                resetFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATMApplication::new);
    }
}
