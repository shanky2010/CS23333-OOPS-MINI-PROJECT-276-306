import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatientManagementFrame extends JFrame {

    public PatientManagementFrame() {
        setTitle("Patient Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridLayout(4, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton addPatientButton = new JButton("Add Patient");
        JButton removePatientButton = new JButton("Remove Patient");
        JButton viewPatientsButton = new JButton("View All Patients");
        JButton backButton = new JButton("Back");

        addPatientButton.setFont(new Font("Arial", Font.BOLD, 20));
        removePatientButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewPatientsButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(addPatientButton);
        panel.add(removePatientButton);
        panel.add(viewPatientsButton);
        panel.add(backButton);

        add(panel);

        addPatientButton.addActionListener(e -> addPatient());
        removePatientButton.addActionListener(e -> removePatient());
        viewPatientsButton.addActionListener(e -> viewPatients());
        backButton.addActionListener(e -> {
            new AdminDashboardFrame();
            dispose();
        });

        setVisible(true);
    }

    private void addPatient() {
        String patientName = JOptionPane.showInputDialog("Enter Patient Name:");
        String contact = JOptionPane.showInputDialog("Enter Contact Number:");

        if (patientName != null && contact != null) {
            try (Connection connection = DatabaseConnection.connect()) {
                String query = "INSERT INTO patients (name, contact) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, patientName);
                statement.setString(2, contact);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Patient added successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removePatient() {
        String patientId = JOptionPane.showInputDialog("Enter Patient ID to Remove:");

        if (patientId != null) {
            try (Connection connection = DatabaseConnection.connect()) {
                String query = "DELETE FROM patients WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(patientId));
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Patient removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Patient ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error removing patient: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewPatients() {
        StringBuilder patientList = new StringBuilder("Patient List:\n\n");

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM patients";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                patientList.append("ID: ").append(resultSet.getInt("id"))
                           .append(", Name: ").append(resultSet.getString("name"))
                           .append(", Contact: ").append(resultSet.getString("contact"))
                           .append("\n");
            }

            JOptionPane.showMessageDialog(this, patientList.toString(), "Patients", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching patients: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
