package com.company;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner( System.in );
        while ( in.hasNextLine() ) {
            String s = in.nextLine();
            StringTokenizer st = new StringTokenizer(s, "\n ,.?!:-;()", true);
            while ( st.hasMoreTokens() ) {
                String next = st.nextToken();
                if (!next.isEmpty()) {
                    if (Character.isLetter(next.charAt(0))) {
                        if (Character.isUpperCase(next.charAt(0)))
                            System.out.print(Character.toLowerCase(next.charAt(0)));
                        else
                            System.out.print(Character.toUpperCase(next.charAt(0)));
                        System.out.print(next.substring(1));
                    }
                    else
                        System.out.print(next);
                }
            }
            System.out.println();
        }
        in.close();
        System.exit(0);
    }
}
