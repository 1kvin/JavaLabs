package sample.Commands;

import sample.DataBase.DBManager;
import sample.Interfaces.ICommand;

import java.sql.SQLException;

public class DeleteCommand implements ICommand {
    private String name;
    public DeleteCommand(String name)
    {
        this.name = name;
    }

    @Override
    public void execute(DBManager manager) throws SQLException {
        manager.deleteItem(name);
        outStream.println("1 item delete");
    }
}
