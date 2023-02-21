import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

class SqlInJava {
    public static Connection con;
    public static Statement statmt;
    public static ResultSet rs;

    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:My_cats.db");
            statmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ошибка подключения");
        }
    }

    public static void create_table_types() throws SQLException {
        statmt.execute("CREATE TABLE IF NOT EXISTS types(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type VARCHAR(100));");
        System.out.println("Таблица types создана");
    }

    public static void insert_type(String type){
        String query = "INSERT INTO types(type) VALUES (?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setString(1, type);
            pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void insert_all_types(ArrayList<String> arr){
        for (String str : arr){
            insert_type(str);
        }
        System.out.println("В таблицу types добавлено " + arr.size() + " значений");
    }

    public static void delete_type(int id){
        String query = "DELETE FROM types WHERE id= ? ;";
        try (PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Запись удалена");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void update_type(int id, String new_type){
        String query = "UPDATE types " +
                "SET type = ? " +
                "WHERE id = ?;";
        try (PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setString(1, new_type);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Запись обновлена");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static String get_type(int id) {
        String query = "SELECT type FROM types WHERE id = ?";
        String result = "";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            result = rs.getString("type");
        } catch (SQLException e) {
            return "Результата нет";
        }
        return result;
    }

    public static ArrayList<String> get_type_where(String where){
        ArrayList<String> select = new ArrayList<String>();
        String query = "SELECT type FROM types WHERE " + where + ";";
        try ( Statement stmt  = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                select.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return select;
    }

    public static void get_all_types(){
        String query = "SELECT type FROM types";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)){
            System.out.println("------Все результаты------");
            while(rs.next()){
                System.out.println(rs.getString("type"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void create_table_cats() throws SQLException {
        statmt.execute("CREATE TABLE IF NOT EXISTS cats(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(20)," +
                "type_id INTEGER, " +
                "age INTEGER, " +
                "weight DOUBLE, " +
                "FOREIGN KEY(type_id) REFERENCES types(id));");
        System.out.println("Таблица cats создана");
    }

    public static int get_id(String type) {
        String query = "SELECT id FROM types WHERE type = ?";
        int id = 0;
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            id = rs.getInt("id");
        } catch (SQLException e) {
            return -1;
        }
        return id;
    }

    public static void insert_cat(String name, String type, int age, Double weight){
        if (get_id(type) == 0 || get_id(type) == 0){
            insert_type(type);
        }
        String query = "INSERT INTO cats(name, type_id, age, weight) " +
                            "VALUES (?, ?, ?, ?);";
        try (PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setString(1, name);
            pstmt.setInt(2, get_id(type));
            pstmt.setInt(3, age);
            pstmt.setDouble(4, weight);
            pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException{
        /*ArrayList<String> list = new ArrayList<String>();
        try (FileReader fr = new FileReader("data.txt")){
            Scanner sc = new Scanner(fr);
            while (sc.hasNextLine()) {
                list.add(sc.nextLine());
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }*/
        connect();
        //create_table_types();
        //insert_all_types(list);
        //delete_type(61);
        //update_type(1, "Новый тип");
        //System.out.println(get_type(2));
        //System.out.println(get_type_where("type LIKE '%а%'").toString());
        create_table_cats();
        insert_cat("Барсик", "Египетская мау", 3, 7.5);
        insert_cat("Борис", "Дворовой обыкновенный", 2, 13.0);
        insert_cat("Журавлик", "Полосатый хрен", 6, 8.3);
    }

}