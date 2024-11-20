import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/doctor_patient_system";
    private static final String USERNAME = "root"; 
    private static final String PASSWORD = "Shankysathu2!"; 

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}
