package sample.Interfaces;

import sample.DBManager;

import java.io.PrintStream;
import java.sql.SQLException;

public interface ICommand {
    PrintStream outStream = System.out;

    default boolean isValid() {
        return true;
    }

    default void onStart() {
    }

    default void execute(DBManager manager) throws SQLException {

    }
}
