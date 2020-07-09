package sample.Commands;

import sample.DataBase.DBManager;
import sample.DataBase.DataDB;
import sample.Interfaces.ICommand;

import java.sql.SQLException;
import java.util.ArrayList;

public class ShowAllCommand implements ICommand {
    public ShowAllCommand() {

    }

    @Override
    public void execute(DBManager manager) throws SQLException {
        ArrayList<DataDB> data = manager.getItemsFromDB();
        for (DataDB item : data) {
            outStream.println("================");
            outStream.println("id: " + item.id);
            outStream.println("prodid: " + item.prodid);
            outStream.println("title: " + item.title);
            outStream.println("cost: $" + item.cost);
        }
        outStream.println("done");
    }
}
