import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Admin Login");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Doctor-Patient Appointment System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(400, 50, 600, 50);
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(500, 200, 150, 30);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(650, 200, 200, 30);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(500, 250, 150, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(650, 250, 200, 30);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(550, 300, 100, 40);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("Shankar") && password.equals("Khushee")) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new AdminDashboardFrame();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(700, 300, 100, 40);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        setContentPane(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
