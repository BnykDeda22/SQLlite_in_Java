import java.sql.*;

public class SqlInJava {

    static String[] types = new String[]{"Абиссинская кошка",
            "Австралийский мист",
            "Американская жесткошерстная",
            "Американская короткошерстная",
            "Американский бобтейл",
            "Американский кёрл",
            "Балинезийская кошка",
            "Бенгальская кошка",
            "Бирманская кошка",
            "Бомбейская кошка",
            "Бразильская короткошёрстная",
            "Британская длинношерстная",
            "Британская короткошерстная",
            "Бурманская кошка",
            "Бурмилла кошка",
            "Гавана",
            "Гималайская кошка",
            "Девон-рекс",
            "Донской сфинкс",
            "Европейская короткошерстная",
            "Египетская мау",
            "Канадский сфинкс",
            "Кимрик",
            "Корат",
            "Корниш-рекс",
            "Курильский бобтейл",
            "Лаперм",
            "Манчкин",
            "Мейн-ку́н",
            "Меконгский бобтейл",
            "Мэнкс кошка",
            "Наполеон",
            "Немецкий рекс",
            "Нибелунг",
            "Норвежская лесная кошка",
            "Ориентальная кошка",
            "Оцикет",
            "Персидская кошка",
            "Петерболд",
            "Пиксибоб",
            "Рагамаффин",
            "Русская голубая кошка",
            "Рэгдолл",
            "Саванна",
            "Селкирк-рекс",
            "Сиамская кошка",
            "Сибирская кошка",
            "Сингапурская кошка",
            "Скоттиш-фолд",
            "Сноу-шу",
            "Сомалийская кошка",
            "Тайская кошка",
            "Тойгер",
            "Тонкинская кошка",
            "Турецкая ангорская кошка",
            "Турецкий ван",
            "Украинский левкой",
            "Чаузи",
            "Шартрез",
            "Экзотическая короткошерстная",
            "Японский бобтейл"
    };

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
        //add_all_types(types);
        //delite_type(1);
        update_type(4, "Новая порода");
        con.close();
    }

    public static void add_all_types(String [] arr){
        for (String str : arr){
            insert_type(str);
        }
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

    public static void delite_type(int id){
        String query = "DELETE FROM types WHERE id= ? ;";
        try (PreparedStatement pstmt = con.prepareStatement(query)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Запись удалена");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void update_type(int id, String new_type)     {
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

}

