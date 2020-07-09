package sample.commands;

import sample.DBManager;
import sample.Interfaces.ICommand;

import java.sql.SQLException;

public class ChangePriceCommand implements ICommand {
    private String name;
    private int price;

    public ChangePriceCommand(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean isValid() {
        return price > 0;
    }

    @Override
    public void execute(DBManager manager) throws SQLException {
        manager.changeItem(name, price);
        outStream.println("Cost changed. New cost: "+manager.getItem(name).cost);
    }
}
