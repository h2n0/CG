package ip.h2n0.util;

import java.util.Scanner;

public class ColourConverter {

    public static void main(String[] args) {
        System.out.println("Vanzeben 2D - Colour converter");
        System.out.println("Made by Elliot Lee-Cerrino");
        System.out.println("============= Warning! ==============");
        System.out.println("All colour codes are aproxamate");
        System.out.println("====================================");
        Scanner s = new Scanner(System.in);
        System.out.print("Please enter colour1 : ");
        int c1 = s.nextInt();
        while (c1 < 0 || c1 > 255) {
            System.out.println("Please enter a valid number between 0 & 255 ");
            System.out.print("Please enter colour1 : ");
            c1 = s.nextInt();
        }
        System.out.print("Please enter colour2 : ");
        int c2 = s.nextInt();
        while (c2 < 0 || c2 > 255) {
            System.out.println("Please enter a valid number between 0 & 255 ");
            System.out.print("Please enter colour2 : ");
            c2 = s.nextInt();
        }
        System.out.print("Please enter colour3 : ");
        int c3 = s.nextInt();
        while (c3 < 0 || c3 > 255) {
            System.out.println("Please enter a valid number between 0 & 255 ");
            System.out.print("Please enter colour3 : ");

            c3 = s.nextInt();
        }
        System.out.println("====== Crunching numbers! ======");
        int b1 = c1 / 45;
        int b2 = c2 / 45;
        int b3 = c3 / 45;
        System.out.println("C1 = " + b1);
        System.out.println("C2 = " + b2);
        System.out.println("C3 = " + b3);
        System.out.println("You should use :" + b1 + b2 + b3);
        System.out.println("======== End of results ========");
    }
}