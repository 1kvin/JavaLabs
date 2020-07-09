package sample.Commands;

import sample.DataBase.DBManager;
import sample.Interfaces.ICommand;

import java.sql.SQLException;

public class AddCommand implements ICommand {
    private String name;
    private int price;

    public AddCommand(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean isValid() {
        return price > 0;
    }

    @Override
    public void execute(DBManager manager) throws SQLException {
        try {
            manager.addItemToDB(name, price);
            outStream.println("1 Item added");
        } catch (IllegalArgumentException e) {
            outStream.println(e.getMessage());
        }
    }

}
