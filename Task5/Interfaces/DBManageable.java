package sample.Interfaces;

import sample.DataBase.DataDB;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DBManageable {
    void createDB() throws SQLException;

    void clearDB() throws SQLException;

    void deleteDB() throws SQLException;

    void addItemToDB(DataDB data) throws SQLException;

    void changeItem(DataDB curData, DataDB newData) throws SQLException;

    void deleteItem(DataDB item) throws SQLException;

    DataDB getItem(String name) throws SQLException;

    ArrayList<DataDB> getItemsFromDB(String filter) throws SQLException;

    String getTableName();

    String getDataBaseURL();
}
