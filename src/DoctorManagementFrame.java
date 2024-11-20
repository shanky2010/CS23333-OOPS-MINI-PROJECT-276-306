import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorManagementFrame extends JFrame {

    public DoctorManagementFrame() {
        setTitle("Doctor Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridLayout(4, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton addDoctorButton = new JButton("Add Doctor");
        JButton removeDoctorButton = new JButton("Remove Doctor");
        JButton viewDoctorsButton = new JButton("View All Doctors");
        JButton backButton = new JButton("Back");

        addDoctorButton.setFont(new Font("Arial", Font.BOLD, 20));
        removeDoctorButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewDoctorsButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(addDoctorButton);
        panel.add(removeDoctorButton);
        panel.add(viewDoctorsButton);
        panel.add(backButton);

        add(panel);

        addDoctorButton.addActionListener(e -> addDoctor());
        removeDoctorButton.addActionListener(e -> removeDoctor());
        viewDoctorsButton.addActionListener(e -> viewDoctors());
        backButton.addActionListener(e -> {
            new AdminDashboardFrame();
            dispose();
        });

        setVisible(true);
    }

    private void addDoctor() {
        String doctorName = JOptionPane.showInputDialog("Enter Doctor Name:");
        String specialization = JOptionPane.showInputDialog("Enter Specialization:");

        if (doctorName != null && specialization != null) {
            try (Connection connection = DatabaseConnection.connect()) {
                String query = "INSERT INTO doctors (name, specialization) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, doctorName);
                statement.setString(2, specialization);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Doctor added successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeDoctor() {
        String doctorId = JOptionPane.showInputDialog("Enter Doctor ID to Remove:");

        if (doctorId != null) {
            try (Connection connection = DatabaseConnection.connect()) {
                String query = "DELETE FROM doctors WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(doctorId));
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Doctor removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Doctor ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error removing doctor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewDoctors() {
        StringBuilder doctorList = new StringBuilder("Doctor List:\n\n");

        try (Connection connection = DatabaseConnection.connect()) {
            String query = "SELECT * FROM doctors";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                doctorList.append("ID: ").append(resultSet.getInt("id"))
                          .append(", Name: ").append(resultSet.getString("name"))
                          .append(", Specialization: ").append(resultSet.getString("specialization"))
                          .append("\n");
            }

            JOptionPane.showMessageDialog(this, doctorList.toString(), "Doctors", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching doctors: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
