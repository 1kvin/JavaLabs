package sample.Commands;

import sample.DataBase.DBManager;
import sample.Interfaces.ICommand;

import java.sql.SQLException;

public class PriceCommand implements ICommand {
    private String name;

    public PriceCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute(DBManager manager) throws SQLException {
        try {
            outStream.println(manager.getItem(name).cost);
        } catch (NullPointerException | IllegalArgumentException e) {
            outStream.println(e.getMessage());
        }
    }
}
