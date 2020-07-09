package com.company.generator;

import com.company.Corridor;

import java.util.Random;

public final class SerialStudentGenerator {
    private static Random randomGenerator = new Random();

    private SerialStudentGenerator() {
    }

    public static void tryPushNext(Corridor corridor, int labsCount, String subjectName) throws IllegalStateException, NullPointerException {
        if (corridor == null) {
            throw new NullPointerException("Set Corridor pls");
        }
        try(BasicStudentGenerator studentGenerator = new BasicStudentGenerator(labsCount, subjectName))
        {
            if (!corridor.add(studentGenerator.generate())) {
                throw new IllegalStateException("Corridor Full");
            }
        } catch (IllegalStateException e) {
            System.out.println("Student create error");
        }


    }

    public static void tryPushNext(Corridor corridor) throws IllegalStateException, NullPointerException {
        tryPushNext(corridor, getRandomCount(), getRandomSubject());
    }

    private static int getRandomCount() {
        switch (randomGenerator.nextInt(3)) {
            case 0:
                return 10;
            case 1:
                return 20;
            default:
                return 100;
        }
    }

    private static String getRandomSubject() {
        switch (randomGenerator.nextInt(3)) {
            case 0:
                return "Математика";
            case 1:
                return "ООП";
            default:
                return "Физика";
        }
    }
}
