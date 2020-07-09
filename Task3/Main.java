package com.company;

import com.company.generator.StudentGeneratorProcess;

import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        final int NUMBER_OF_ROBOTS = 3;
        final int NUMBER_OF_STUDENT = 10;
        final int STUDENT_GENERATOR_SPEED = 10;
        final int ROBOT_SPEED = 100;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER_OF_ROBOTS);
        Corridor corridor = new Corridor();
        Robot[] robot = new Robot[NUMBER_OF_ROBOTS];
        StudentGeneratorProcess studentGenerator = new StudentGeneratorProcess(corridor, NUMBER_OF_STUDENT, STUDENT_GENERATOR_SPEED);

        robot[0] = new Robot(cyclicBarrier, corridor, "Математика", ROBOT_SPEED);
        robot[1] = new Robot(cyclicBarrier, corridor, "ООП", ROBOT_SPEED);
        robot[2] = new Robot(cyclicBarrier, corridor, "Физика", ROBOT_SPEED);

        //  Switcher switcher = new Switcher(studentGenerator, robot);
        InputSwitcher switcher = new InputSwitcher(System.in);

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < NUMBER_OF_ROBOTS; i++) {
            service.execute(robot[i]);
        }
        service.execute(studentGenerator);
        service.execute(switcher);
        service.shutdown();
    }
}
