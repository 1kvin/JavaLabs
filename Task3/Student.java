package com.company;

public class Student {
    private int labsCount;
    private String subjectName;

    public Student(int labsCount, String subjectName) throws IllegalArgumentException{
        if (labsCount == 10 || labsCount == 20 || labsCount == 100) {
            this.labsCount = labsCount;
        } else {
            throw new IllegalArgumentException("count can be 10 or 20 or 100");
        }
        if (!subjectName.equals("")) {
            this.subjectName = subjectName;
        } else {
            throw new IllegalArgumentException("Empty subject field");
        }

    }

    public int getCount() {
        return labsCount;
    }

    public void acceptWork(int quantity) {
        labsCount -= quantity;
        if (labsCount <= 0) {
            System.out.println("ПАЦАНЫ я сдал!!!");
        } else {
            System.out.println("Фух, осталось " + labsCount + " лаб по " + subjectName);
        }
    }

    public boolean countCheck() {
        return labsCount > 0;
    }

    public String getSubject() {
        return subjectName;
    }
}
