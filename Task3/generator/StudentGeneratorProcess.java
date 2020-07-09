package com.company.generator;

import com.company.Corridor;

public class StudentGeneratorProcess implements Runnable {

    private Corridor corridor;
    private int count;
    private int speed;
    private boolean status = true;

    public StudentGeneratorProcess(Corridor corridor, int count, int speed) {
        this.corridor = corridor;
        this.count = count;
        this.speed = speed;
    }

    public boolean getStatus()
    {
        return status;
    }

    @Override
    public void run() {
        int count = 0;
        while (count < this.count) {
            try {
                SerialStudentGenerator.tryPushNext(corridor);
                count++;
            } catch (IllegalStateException e) {
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } catch (NullPointerException e) {
                System.out.println("Null Corridor");
            }
        }
        status = false;
    }

}
