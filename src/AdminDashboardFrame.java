import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard");
        setLayout(new GridLayout(4, 1, 20, 20));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JButton manageDoctorsButton = new JButton("Manage Doctors");
        manageDoctorsButton.setFont(new Font("Arial", Font.BOLD, 24));
        manageDoctorsButton.addActionListener(e -> {
            new DoctorManagementFrame();
            dispose();
        });

        JButton managePatientsButton = new JButton("Manage Patients");
        managePatientsButton.setFont(new Font("Arial", Font.BOLD, 24));
        managePatientsButton.addActionListener(e -> {
            new PatientManagementFrame();
            dispose();
        });

        JButton scheduleAppointmentsButton = new JButton("Schedule Appointments");
        scheduleAppointmentsButton.setFont(new Font("Arial", Font.BOLD, 24));
        scheduleAppointmentsButton.addActionListener(e -> {
            new AppointmentSchedulingFrame();
            dispose();
        });

        JButton viewAppointmentsButton = new JButton("View Appointments");
        viewAppointmentsButton.setFont(new Font("Arial", Font.BOLD, 24));
        viewAppointmentsButton.addActionListener(e -> {
            new ViewAppointmentsFrame();
            dispose();
        });

        add(manageDoctorsButton);
        add(managePatientsButton);
        add(scheduleAppointmentsButton);
        add(viewAppointmentsButton);

        setVisible(true);
    }
}
