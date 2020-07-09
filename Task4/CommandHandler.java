package sample;

import sample.Interfaces.ICommand;
import sample.commands.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Scanner;

public class CommandHandler {


    private DBManager dbManager;
    private InputStream inputStream;
    private PrintStream outputStream;

    public CommandHandler(DBManager dbManager,  InputStream inputStream, PrintStream outputStream) {
        this.dbManager = dbManager;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public ICommand stringToCommand(String str) {
        String[] A = str.split(" ");
        switch (A[0]) {
            case "/add":
                if (A.length == 3) return new AddCommand(A[1], Integer.parseInt(A[2]));
                break;
            case "/delete":
                if (A.length == 2) return new DeleteCommand(A[1]);
                break;
            case "/show_all":
                if (A.length == 1) return new ShowAllCommand();
                break;
            case "/price":
                if (A.length == 2) return new PriceCommand(A[1]);
                break;
            case "/change_price":
                if (A.length == 3) return new ChangePriceCommand(A[1], Integer.parseInt(A[2]));
                break;
            case "/filter_by_price":
                if (A.length == 3) return new FilterByPriceCommand(Integer.parseInt(A[1]), Integer.parseInt(A[2]));
                break;
            case "/help":
                if (A.length == 1) return new HelpCommand();
                break;
            case "/exit":
                if (A.length == 1) return new ExitCommand();
                break;
        }
        return new InvalidCommand();
    }

    public void run() {
        Scanner in = new Scanner(inputStream);
        outputStream.println("Database ready to work, write '/help' to get available commands");
        ICommand command = stringToCommand(in.nextLine());
        while (!(command instanceof ExitCommand)) {
            if (command.isValid()) {
                command.onStart();
                try {
                    command.execute(dbManager);
                } catch (SQLException e) {
                    e.printStackTrace();
                    outputStream.println("Error execute command");
                    return;
                }
            } else {
                outputStream.println("invalid command");
            }
            command = stringToCommand(in.nextLine());
        }
    }
}
