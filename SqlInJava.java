import java.sql.*;

public class SqlInJava {
    public static Connection con;
    public static Statement statmt;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:My_cats.db");
        System.out.println("Есть пробитие");
        statmt = con.createStatement();
        statmt.execute("CREATE TABLE IF NOT EXISTS types(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                            "type VARCHAR(100));");
        System.out.println("Таблица создана");
        insert_type("Абиссинская кошка");
        insert_type("Австралийский мист");
        insert_type("Американская жесткошерстная");
        con.close();

    }

    public static void insert_type(String type) {
        String query = "INSERT INTO types(type) VALUES (?)";

        try (PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setString(1, type);
            pstmt.executeUpdate();
            System.out.println("Добавлено");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
