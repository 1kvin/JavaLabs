package sample;

import sample.Interfaces.DBManageable;

import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;

public class DBManager implements AutoCloseable, DBManageable {

    private Connection connection;
    private Statement statement;
    private String dataBaseURL;
    private int curProdID = 1;
    private String tableName;

    final static class Builder {
        private static ArrayList<DBManager> used = new ArrayList<>();
        private String dataBaseURL;
        private String tableName;
        private String user;
        private String password;

        public Builder setDataBaseURL(String dataBaseURL) {
            this.dataBaseURL = dataBaseURL;
            return this;
        }

        public Builder setUser(String user) {
            this.user = user;
            return this;
        }

        public Builder setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        private static boolean verifyExistence(String DATABASE_URL, String tableName) {
            for (DBManager db : used) {
                if (db.getDataBaseURL().equals(DATABASE_URL) && db.getTableName().equals(tableName)) {
                    return true;
                }
            }
            return false;
        }

        public DBManager build() throws SQLException, ClassNotFoundException, IllegalArgumentException {
            if (verifyExistence(dataBaseURL, tableName)) {
                throw new IllegalArgumentException("The table is already in use");
            }
            DBManager curDB = new DBManager(dataBaseURL, user, password, tableName);
            used.add(curDB);
            return curDB;
        }
    }

    private DBManager(String databaseUrl, String USER, String PASSWORD, String tableName) throws ClassNotFoundException, SQLException {
        //Legacy com.mysql.jdbc.Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(databaseUrl, USER, PASSWORD);
        statement = connection.createStatement();
        this.tableName = tableName;
        this.dataBaseURL = databaseUrl;
    }

    @Override
    public void createDB() throws SQLException {
        String sqlCommand = "CREATE TABLE `u0523300_java`.`" + tableName + "` (`id` INT NOT NULL AUTO_INCREMENT , `prodid` INT NOT NULL , `title` CHAR(50) NOT NULL , `cost` INT NOT NULL , PRIMARY KEY (`id`), UNIQUE(`prodid`, `title`)) ENGINE = InnoDB;";
        statement.executeUpdate(sqlCommand);
    }

    @Override
    public void clearDB() throws SQLException {
        String sqlCommand = "TRUNCATE TABLE `" + tableName + "`";
        statement.executeUpdate(sqlCommand);
    }

    @Override
    public void deleteDB() throws SQLException {
        String sqlCommand = "DROP TABLE `" + tableName + "`";
        statement.executeUpdate(sqlCommand);
    }


    @Override
    public void addItemToDB(DataDB data) throws SQLException, IllegalArgumentException {
        addItemToDB(data.title, data.cost);
    }

    public void addItemToDB(String title, int cost) throws SQLException, IllegalArgumentException {
        try {
            getItem(title);
            throw new IllegalArgumentException("Try add duplicate items");
        } catch (NullPointerException ex) {
            String sqlCommand = "INSERT INTO `u0523300_java`.`" + tableName + "` (`prodid`, `title`, `cost`) VALUES (?, ?, ?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
                preparedStatement.setInt(1, curProdID);
                preparedStatement.setString(2, title);
                preparedStatement.setInt(3, cost);
                preparedStatement.execute();
                curProdID++;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeItem(DataDB curData, DataDB newData) throws SQLException {
        changeItem(curData.title, newData.cost);
        if (curData.prodid != 0) {
            String sqlCommand = "UPDATE `u0523300_java`.`" + tableName + "`  SET `title` = '" + newData.title + "' WHERE `" + tableName + "`.`id` = " + "\"" + curData.prodid + "\"";
            statement.execute(sqlCommand);
        }
    }


    public void changeItem(String name, int price) throws SQLException {
        String sqlCommand = "UPDATE `u0523300_java`.`" + tableName + "`  SET `cost` = '" + price + "' WHERE `" + tableName + "`.`title` = " + "\"" + name + "\"";
        statement.execute(sqlCommand);
    }

    @Override
    public void deleteItem(DataDB item) throws SQLException {
        deleteItem(item.title);
    }

    public void deleteItem(String name) throws SQLException {
        String sqlCommand = "DELETE FROM `u0523300_java`.`" + tableName + "` WHERE `" + tableName + "`.`title` = " + "\"" + name + "\"";
        statement.execute(sqlCommand);
    }


    @Override
    public DataDB getItem(String name) throws SQLException, NullPointerException, IllegalArgumentException {
        String filter = "title = " + "\"" + name + "\"";
        ArrayList<DataDB> dataList = getItemsFromDB(filter);
        if (dataList.size() == 0) throw new NullPointerException("There is no such product");
        if (dataList.size() > 1) throw new IllegalArgumentException("Duplicate items");
        return dataList.get(0);
    }

    public ArrayList<DataDB> getItemsFromDB() throws SQLException {
        return getItemsFromDB("");
    }

    @Override
    public ArrayList<DataDB> getItemsFromDB(String filter) throws SQLException {
        String sqlCommand;
        if (!filter.equals("")) sqlCommand = "SELECT * FROM " + tableName + " WHERE " + filter;
        else sqlCommand = "SELECT * FROM " + tableName;
        ArrayList<DataDB> dataList = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(sqlCommand);

        while (resultSet.next()) {
            DataDB data = new DataDB();
            data.id = resultSet.getInt("id");
            data.prodid = resultSet.getInt("prodid");
            data.title = resultSet.getString("title");
            data.cost = resultSet.getInt("cost");
            dataList.add(data);
        }
        resultSet.close();
        return dataList;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getDataBaseURL() {
        return dataBaseURL;
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }
}
