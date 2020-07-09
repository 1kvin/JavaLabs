package sample.commands;

import sample.DBManager;
import sample.DataDB;
import sample.Interfaces.ICommand;

import java.sql.SQLException;
import java.util.ArrayList;

public class FilterByPriceCommand implements ICommand {
    private int begin;
    private int end;

    public FilterByPriceCommand(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public boolean isValid() {
        return (begin > 0 && end > 0 && end >= begin);
    }

    @Override
    public void execute(DBManager manager) throws SQLException {
        String filter = manager.getTableName() + ".cost >= " + begin + " AND " + manager.getTableName() + ".cost <= " + end;
        ArrayList<DataDB> data = manager.getItemsFromDB(filter);

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
