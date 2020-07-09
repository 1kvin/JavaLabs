package com.company;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class Robot implements Runnable {
    private Corridor corridor;
    private String sub;
    private CyclicBarrier cyclicBarrier;
    private int speed;
    private Student myStudent;
    private boolean work = true;

    public Robot(CyclicBarrier cyclicBarrier, Corridor corridor, String type, int speed) {
        this.corridor = corridor;
        this.sub = type;
        this.cyclicBarrier = cyclicBarrier;
        this.speed = speed;
    }

    public boolean getStatus() {
        return (myStudent == null);
    }

    public void off()
    {
        this.work = false;
    }

    @Override
    public void run() {
        while (work) {
            try {
                if (myStudent == null) {
                    myStudent = corridor.get(sub);
                    if (myStudent != null) System.out.println("Заходи и садись туда!");
                }
                if (myStudent != null) {
                    //Эмулируем умственную работу препода одной строкой
                    Thread.sleep(speed);
                    corridor.resumeWork();
                    myStudent.acceptWork(5);

                    if (myStudent.getCount() != 0)
                        System.out.println("Так посмотрим, что у тебя тут. " + myStudent.getCount() + " Лабораторных осталось");
                    else System.out.println("Ну это на " + ThreadLocalRandom.current().nextInt(3, 6));

                    if (!myStudent.countCheck()) {
                        myStudent = null;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException | InterruptedException exc) {
                System.out.println(exc);
            }
        }
    }
}
