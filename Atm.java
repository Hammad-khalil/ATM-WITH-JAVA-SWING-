
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Atm extends JFrame {
    private double balance = 00.00; // Initial balance
    private String pin;
    private String customerName;
    private JLabel balanceLabel;

    public Atm() {
        setTitle("Modern ATM App Interface");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Check if PIN is set, if not, set it up
        if (getPin() == null) {
            setupAccount();
        } else {
            showLogin();
        }
    }

    private void setupAccount() {
        customerName = JOptionPane.showInputDialog(Atm.this, "Enter your name:");
        while (customerName == null || customerName.trim().isEmpty()) {
            customerName = JOptionPane.showInputDialog(Atm.this, "Invalid name. Please enter your name:");
        }

        String enteredPin;
        do {
            enteredPin = JOptionPane.showInputDialog(Atm.this, "Set up your PIN (4 digits):");
        } while (enteredPin == null || enteredPin.length() != 4 || !enteredPin.matches("\\d+"));

        setPin(enteredPin);
        JOptionPane.showMessageDialog(Atm.this, "Account set up successfully!");
        showLogin();
    }

    private void showLogin() {
        String enteredPin;
        boolean loggedIn = false;
        int attempts = 3;

        do {
            enteredPin = JOptionPane.showInputDialog(Atm.this, "Enter your ATM PIN:");
            if (enteredPin != null && enteredPin.equals(getPin())) {
                loggedIn = true;
            } else {
                attempts--;
                JOptionPane.showMessageDialog(Atm.this, "Incorrect PIN. Attempts left: " + attempts);
            }
        } while (!loggedIn && attempts > 0);

        if (loggedIn) {
            initializeUI();
            JOptionPane.showMessageDialog(Atm.this, "Login successful. Welcome, " + customerName + "!");
        } else {
            JOptionPane.showMessageDialog(Atm.this, "Too many incorrect attempts. Exiting...");
            System.exit(0);
        }
    }

    private void initializeUI() {
        // Create components with modern styling
        balanceLabel = new JLabel("Balance: $" + balance);
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        balanceLabel.setForeground(new Color(46, 204, 113));

        JButton checkBalanceButton = createStyledButton("Check Balance", new Color(52, 152, 219));
        JButton withdrawButton = createStyledButton("Withdraw", new Color(231, 76, 60));
        JButton depositButton = createStyledButton("Deposit", new Color(46, 204, 113));

        // Set layout
        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Add components to the frame
        centerPanel.add(Box.createVerticalStrut(50)); // Add some spacing
        centerPanel.add(balanceLabel);
        centerPanel.add(Box.createVerticalStrut(30)); // Add some spacing
        centerPanel.add(checkBalanceButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(withdrawButton);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(depositButton);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(text);
            }
        });

        return button;
    }

    private void handleButtonClick(String buttonType) {
        if (buttonType.equals("Check Balance")) {
            JOptionPane.showMessageDialog(Atm.this, "Your balance is: $" + balance);
        } else {
            boolean isWithdraw = buttonType.equals("Withdraw");
            performTransaction(isWithdraw);
        }
    }

    private void performTransaction(boolean isWithdraw) {
        String input = JOptionPane.showInputDialog(Atm.this, isWithdraw ? "Enter withdrawal amount:" : "Enter deposit amount:");
        try {
            double amount = Double.parseDouble(input);
            if (amount > 0) {
                if (isWithdraw && amount <= balance) {
                    balance -= amount;
                    updateBalanceLabel();
                    JOptionPane.showMessageDialog(Atm.this, "Transaction successful. New balance: $" + balance);
                } else if (!isWithdraw) {
                    balance += amount;
                    updateBalanceLabel();
                    JOptionPane.showMessageDialog(Atm.this, "Transaction successful. New balance: $" + balance);
                } else {
                    JOptionPane.showMessageDialog(Atm.this, isWithdraw ? "Insufficient funds." : "Invalid amount.");
                }
            } else {
                JOptionPane.showMessageDialog(Atm.this, "Invalid amount. Please enter a positive number.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(Atm.this, "Invalid input. Please enter a valid number.");
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: $" + balance);
    }

    private String getPin() {
        return pin;
    }

    private void setPin(String pin) {
        this.pin = pin;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Atm().setVisible(true));
    }
}