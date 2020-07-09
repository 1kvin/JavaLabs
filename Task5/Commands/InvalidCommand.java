package sample.Commands;

import sample.Interfaces.ICommand;

public class InvalidCommand implements ICommand {
    public InvalidCommand() {

    }
    @Override
    public boolean isValid() {
        return false;
    }
}
