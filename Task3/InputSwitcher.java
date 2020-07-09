package com.company;

import java.io.InputStream;
import java.util.Scanner;

public class InputSwitcher implements Runnable {
    private Scanner in;
    private final static String STOP_WORD = "banan";

    public InputSwitcher(InputStream inp) {
        if (inp == null) {
            System.out.println("Switcher failed");
            System.exit(0);
        }

        in = new Scanner(inp);
    }

    @Override
    public void run() {
        while (true) {
            if (in.nextLine().equals(STOP_WORD)) {
                System.out.println("DONE");
                System.exit(0);
            }
        }
    }
}
