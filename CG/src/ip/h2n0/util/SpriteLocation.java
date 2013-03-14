package ip.h2n0.util;

import java.util.Scanner;

/** This class is used to find out what corodinate you need to use<br>
 * when you use screen.render();
 * 
 * @author h2n0
 *
 */
public class SpriteLocation {

    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);

        System.out.print("Pleas enter the X co-ordinate : ");
        int cx = s.nextInt();

        System.out.print("Pleas enter the Y co-ordinate : ");
        int cy = s.nextInt();

        String cordinates = "Use : " + cx + " + " + cy + " * 32";
        System.out.println(cordinates);
    }

}
