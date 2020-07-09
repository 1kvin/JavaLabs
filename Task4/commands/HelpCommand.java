package sample.commands;

import sample.Interfaces.ICommand;

public class HelpCommand implements ICommand {
    public HelpCommand()
    {
    }

    @Override
    public void onStart() {
        outStream.println("----------");
        outStream.println("HELP MENU");
        outStream.println("/add 'item' 'price'");
        outStream.println("/delete 'item'");
        outStream.println("/show_аll");
        outStream.println("/price 'item'");
        outStream.println("/change_price 'item' 'price'");
        outStream.println("/filter_by_price 'begin' 'end'");
        outStream.println("----------");
    }
}
