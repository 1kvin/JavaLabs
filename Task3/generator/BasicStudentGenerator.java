package com.company.generator;

import com.company.Student;

public final class BasicStudentGenerator implements AutoCloseable {
    private static int labsCount;
    private static String subjectName;

    public BasicStudentGenerator(int labsCount, String subjectName) {
        BasicStudentGenerator.labsCount = labsCount;
        BasicStudentGenerator.subjectName = subjectName;
    }

    public Student generate() throws IllegalArgumentException {
        return new Student(labsCount, subjectName);
    }

    @Override
    public void close(){
    }
}
