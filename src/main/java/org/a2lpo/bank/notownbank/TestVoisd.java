package org.a2lpo.bank.notownbank;

import java.util.Scanner;

public class TestVoisd {
    public static String string = "hello world";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        double sqr = sqr(a, b);
        sqr(3,2);
        sqr(10,2);
        sqr(5,2);
    }

    public static void withArgs(String someStringField) {
        System.out.println(string + " " + someStringField);
    }
    public static void withoutArgs() {
        System.out.println(string);
    }
    public static double sqr(int a, int b) {
        double pow = Math.pow(a, b);
        System.out.println(pow);
        return pow;
    }
}
