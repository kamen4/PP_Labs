package com.company;

import java.util.*;

public class Student extends Man implements Comparable<Student>, Iterable<Object>, Iterator<Object> {
    private String faculty;
    private int course;
    private int group;

    public Student(String name, int age, String gender, double weight, String faculty, int course, int group) {
        super(name, age, gender, weight);
        this.faculty = faculty;
        this.course = course;
        this.group = group;
    }

    public Student(String student)
    {
        super(student);
        StringTokenizer studTok = new StringTokenizer(student, ":, ");
        studTok.nextToken();
        setName(studTok.nextToken());
        studTok.nextToken();
        setAge(Integer.parseInt(studTok.nextToken()));
        studTok.nextToken();
        setGender(studTok.nextToken());
        studTok.nextToken();
        setWeight(Double.parseDouble(studTok.nextToken()));
        studTok.nextToken();
        this.faculty = studTok.nextToken();
        studTok.nextToken();
        this.course = Integer.parseInt(studTok.nextToken());
        studTok.nextToken();
        this.group = Integer.parseInt(studTok.nextToken());
    }

    public void setAge(int age) {
        super.setAge(age);
    }

    public void setWeight(double weight) {
        super.setWeight(weight);
    }

    public void nextCourse() {
        course++;
    }

    public void changeGroup(int group) {
        this.group = group;
    }

    int compare_idx;

    public void setCompare(int idx)
    {
        compare_idx = idx;
    }

    @Override
    public int compareTo(Student student) {
        switch (compare_idx) {
            case 0:
                return CharSequence.compare(getName(), student.getName());
            case 1:
                return Integer.compare(getAge(), student.getAge());
            case 2:
                return CharSequence.compare(getGender(), student.getGender());
            case 3:
                return Double.compare(getWeight(), student.getWeight());
            case 4:
                return CharSequence.compare(faculty, student.faculty);
            case 5:
                return Integer.compare(course, student.course);
            case 6:
                return Integer.compare(group, student.group);
        }
        throw new NoSuchElementException();
    }

    @Override
    public String toString() {
        return  "Name: " + getName() +
                ", Age: " + getAge() +
                ", Gender: " + getGender() +
                ", Weight: " + getWeight() +
                ", Faculty: " + faculty +
                ", Course: " + course +
                ", Group: " + group;
    }

    int iter_idx;

    @Override
    public Iterator<Object> iterator() {
        iter_idx = -1;
        return this;
    }

    @Override
    public boolean hasNext() {
        return iter_idx < 6;
    }

    @Override
    public Object next() {
        switch (++iter_idx) {
            case 0:
                return getName();
            case 1:
                return getAge();
            case 2:
                return getGender();
            case 3:
                return getWeight();
            case 4:
                return faculty;
            case 5:
                return course;
            case 6:
                return group;
        }
        throw new NoSuchElementException();
    }
}
