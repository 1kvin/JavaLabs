package com.company;

import com.company.generator.StudentGeneratorProcess;

public class Switcher implements Runnable {
    private Robot[] robots;
    private StudentGeneratorProcess generator;
    private boolean work = true;

    public Switcher(StudentGeneratorProcess generator, Robot[] robots) throws IllegalArgumentException {
        this.robots = robots;
        this.generator = generator;
    }

    @Override
    public void run() {
        while (work) {
            if (!generator.getStatus()) {
                boolean flag = false;
                for (Robot robot : robots) {
                    if (!robot.getStatus()) flag = true;
                }
                if (!flag) {
                    work = false;
                    for (Robot robot : robots) {
                        robot.off();
                    }
                    System.out.println("DONE");
                    System.exit(0);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
