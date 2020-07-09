package sample;

import java.sql.*;

public class JDBC4 {
    static final String DATABASE_URL = "jdbc:mysql://server205.hosting.reg.ru:3306/u0523300_java?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    static final String USER = "u0523300_java";
    static final String PASSWORD = "Javajavajavajava";

    static final String TABLE_NAME = "testDB";

    public static void main(String[] args) {

        try (DBManager dbManager = new DBManager.Builder()
                .setDataBaseURL(DATABASE_URL)
                .setPassword(PASSWORD)
                .setTableName(TABLE_NAME)
                .setUser(USER)
                .build()) {
            try {
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

            //Инициализируем и запускаем обработчик команд
            CommandHandler commandHandler = new CommandHandler(dbManager, System.in, System.out);
            commandHandler.run();

        } catch (SQLException e) {
            System.out.println("Connection error");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        }
    }
}