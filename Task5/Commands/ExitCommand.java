package sample.Commands;

import sample.Interfaces.ICommand;

public class ExitCommand implements ICommand {
    public ExitCommand()
    {

    }

    @Override
    public void onStart() {
        outStream.println("Good By :3");
    }
}
