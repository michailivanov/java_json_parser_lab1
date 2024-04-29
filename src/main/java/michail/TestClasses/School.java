package michail.TestClasses;

import java.util.Arrays;

public class School {
    private String name;
    private Teacher[] teachers;
    private Student[] students;

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", teachers=" + Arrays.toString(teachers) +
                ", students=" + Arrays.toString(students) +
                '}';
    }
}

class Teacher {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Student {
    private String name;
    private int age;
    private String[] subjects;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", subjects=" + Arrays.toString(subjects) +
                '}';
    }
}

