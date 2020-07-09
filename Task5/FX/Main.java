package sample.FX;

import javafx.application.Application;
import javafx.stage.Stage;

import sample.DataBase.DBManager;

import java.sql.SQLException;


public class Main extends Application {
    static final String DATABASE_URL = "jdbc:mysql://server205.hosting.reg.ru:3306/u0523300_java?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    static final String USER = "u0523300_java";
    static final String PASSWORD = "Javajavajavajava";
    static final String TABLE_NAME = "testDB";

    public static Stage stage;
    public static DBManager dbManager = null;


    @Override
    public void start(Stage tempStage) throws SQLException {
        stage = tempStage;
        GUIController.init(stage, dbManager);
    }


    public static void main(String[] args) {
        try (DBManager tempManager = new DBManager.Builder().setDataBaseURL(DATABASE_URL).setPassword(PASSWORD).setTableName(TABLE_NAME).setUser(USER).build()) {
            try {
                dbManager = tempManager;
                //Создаём БД если её нет
                dbManager.createDB();
            } catch (SQLException e) {
                //Очищаем если есть
                dbManager.clearDB();
            }

            //Добавляем элементы
            for (int i = 1; i <= 10; i++) {
                dbManager.addItemToDB("ITEM" + i, i * 10);
            }
            //Запускаем графический интерфейс
            launch(args);

        } catch (SQLException e) {
            System.out.println("Connection error");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }

    }
}
