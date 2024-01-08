import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Atm extends JFrame {
    private double balance = 1000; // Initial balance

    public Atm() {
        setTitle("ATM App Interface");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel balanceLabel = new JLabel("Balance: $" + balance);
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");

        // Set layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(balanceLabel);
        add(checkBalanceButton);
        add(withdrawButton);
        add(depositButton);

        // Add action listeners
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Atm.this, "Your balance is: $" + balance);
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(Atm.this, "Enter withdrawal amount:");
                try {
                    double amount = Double.parseDouble(input);
                    if (amount > 0 && amount <= balance) {
                        balance -= amount;
                        JOptionPane.showMessageDialog(Atm.this, "Withdrawal successful. New balance: $" + balance);
                    } else {
                        JOptionPane.showMessageDialog(Atm.this, "Invalid amount or insufficient funds.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Atm.this, "Invalid input. Please enter a valid number.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(Atm.this, "Enter deposit amount:");
                try {
                    double amount = Double.parseDouble(input);
                    if (amount > 0) {
                        balance += amount;
                        JOptionPane.showMessageDialog(Atm.this, "Deposit successful. New balance: $" + balance);
                    } else {
                        JOptionPane.showMessageDialog(Atm.this, "Invalid amount. Please enter a positive number.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Atm.this, "Invalid input. Please enter a valid number.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Atm().setVisible(true);
            }
        });
    }
}