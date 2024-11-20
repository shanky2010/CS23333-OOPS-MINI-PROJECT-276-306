import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAppointmentsFrame extends JFrame {

    public ViewAppointmentsFrame() {
        setTitle("View Appointments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Scheduled Appointments", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea appointmentsArea = new JTextArea();
        appointmentsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(appointmentsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> {
            new AdminDashboardFrame();
            dispose();
        });
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);

        loadAppointments(appointmentsArea);
        setVisible(true);
    }

    private void loadAppointments(JTextArea appointmentsArea) {
        StringBuilder appointmentList = new StringBuilder();

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.date, a.time, a.status " +
                           "FROM appointments a " +
                           "JOIN patients p ON a.patient_id = p.id " +
                           "JOIN doctors d ON a.doctor_id = d.id";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                appointmentList.append("Appointment ID: ").append(resultSet.getInt("id"))
                               .append("\nPatient: ").append(resultSet.getString("patient_name"))
                               .append("\nDoctor: ").append(resultSet.getString("doctor_name"))
                               .append("\nDate: ").append(resultSet.getString("date"))
                               .append("\nTime: ").append(resultSet.getString("time"))
                               .append("\nStatus: ").append(resultSet.getString("status"))
                               .append("\n-------------------------------\n");
            }
        } catch (Exception ex) {
            appointmentList.append("Error loading appointments: ").append(ex.getMessage());
        }

        appointmentsArea.setText(appointmentList.toString());
    }
}
