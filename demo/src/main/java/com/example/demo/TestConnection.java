import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) throws Exception {

        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/demo_spring?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
            "payrolltest",
            "simple123"
        );

        System.out.println("CONNECTED");
        conn.close();
    }
}