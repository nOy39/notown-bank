package org.a2lpo.bank.notownbank;

public class Test {
    public static void main(String[] args) {
        SubClass subClass = new SubClass(23,"Андрей");
        SubClass subClass2 = new SubClass(23,"Наташа");
        System.out.println(subClass);
        System.out.println(subClass2);
    }
}
