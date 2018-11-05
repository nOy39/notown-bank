package org.a2lpo.bank.notownbank;

public class SubClass {
    private int age;
    private String name;
    private boolean gender;

    public SubClass(int age, String name) {
        this.age = age;
        this.name = name;
        this.gender = gender(name);
    }

    boolean gender(String name) {
        int а = name.lastIndexOf("а");
        if (а>0) {
            return false;
        } else {
            return true;
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SubClass{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
