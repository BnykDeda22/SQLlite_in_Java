import java.sql.*;

public class SqlInJava {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:My_cats.db");
        System.out.println("Есть пробитие");
        Statement statmt = con.createStatement();
        statmt.execute("CREATE TABLE IF NOT EXISTS types(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                            "type VARCHAR(100))");
        System.out.println("Таблица создана");
        con.close();
    }
}
