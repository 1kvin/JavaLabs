package com.company;

import java.util.ArrayList;
import java.util.List;

public class Corridor {
    private List<Student> store;
    private static final int maxStudentInCorridor = 10;
    private static final int minStudentInCorridor = 0;
    private int studentCounter = 0;

    public Corridor() {
        store = new ArrayList<>();
    }

    public synchronized void resumeWork() {
        notifyAll();
    }

    public synchronized boolean add(Student element) {
        try {
            if (studentCounter < maxStudentInCorridor) {
                notifyAll();
                store.add(element);
                String info = String.format("%s Студентов в корридоре. Студент пришел на экзамен с %s заданиями по %s", store.size(), element.getCount(), element.getSubject());
                System.out.println(info);
                studentCounter++;
            } else {
                System.out.println("> Мест нет. Студент пошел курить.");
                wait();
                return false;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public synchronized Student get(String sub) {
        try {
            if (studentCounter > minStudentInCorridor) {
                notifyAll();
                for (Student student : store) {
                    if (student.getSubject().equals(sub)) {
                        studentCounter--;
                        System.out.println("> Студент пошел сдавать " + sub + ", давайте помолимся за него: ");
                        store.remove(student);
                        System.out.println("> В коридоре: " + store.size() + " студентов");
                        return student;
                    }
                }
            }

            System.out.println("> Ни кто не хочет сдавать " + sub + "? Ладно я подожду.");
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
