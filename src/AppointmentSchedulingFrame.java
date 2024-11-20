import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AppointmentSchedulingFrame extends JFrame {

    public AppointmentSchedulingFrame() {
        setTitle("Schedule Appointment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridLayout(6, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel patientLabel = new JLabel("Select Patient:");
        JComboBox<String> patientComboBox = new JComboBox<>();
        JLabel doctorLabel = new JLabel("Select Doctor:");
        JComboBox<String> doctorComboBox = new JComboBox<>();
        JLabel dateLabel = new JLabel("Appointment Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JLabel timeLabel = new JLabel("Appointment Time (HH:MM):");
        JTextField timeField = new JTextField();
        JButton scheduleButton = new JButton("Schedule Appointment");
        JButton backButton = new JButton("Back");

        loadPatients(patientComboBox);
        loadDoctors(doctorComboBox);

        scheduleButton.addActionListener(e -> {
            String patient = (String) patientComboBox.getSelectedItem();
            String doctor = (String) doctorComboBox.getSelectedItem();
            String date = dateField.getText();
            String time = timeField.getText();

            if (patient != null && doctor != null && !date.isEmpty() && !time.isEmpty()) {
                scheduleAppointment(patient, doctor, date, time);
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            new AdminDashboardFrame();
            dispose();
        });

        panel.add(patientLabel);
        panel.add(patientComboBox);
        panel.add(doctorLabel);
        panel.add(doctorComboBox);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(timeLabel);
        panel.add(timeField);
        panel.add(scheduleButton);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }

    private void loadPatients(JComboBox<String> patientComboBox) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT id, name FROM patients";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                patientComboBox.addItem(resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading patients: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDoctors(JComboBox<String> doctorComboBox) {
        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT id, name FROM doctors";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                doctorComboBox.addItem(resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading doctors: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void scheduleAppointment(String patient, String doctor, String date, String time) {
        int patientId = Integer.parseInt(patient.split(" - ")[0]);
        int doctorId = Integer.parseInt(doctor.split(" - ")[0]);

        try (Connection connection = DatabaseConnection.connect()) {
            String checkQuery = "SELECT * FROM appointments WHERE doctor_id = ? AND date = ? AND time = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, doctorId);
            checkStatement.setString(2, date);
            checkStatement.setString(3, time);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "The doctor already has an appointment at this time.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String insertQuery = "INSERT INTO appointments (patient_id, doctor_id, date, time, status) VALUES (?, ?, ?, ?, 'Scheduled')";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, patientId);
                insertStatement.setInt(2, doctorId);
                insertStatement.setString(3, date);
                insertStatement.setString(4, time);
                insertStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Appointment scheduled successfully.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error scheduling appointment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
