package com.company;

import java.util.Scanner;
import java.util.Random;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.print("Enter n: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.close();
        if (n <= 1) {
            System.err.println("Invalid n value, require n > 1");
            System.exit(1);
        }
        Random rnd = new Random((new Date()).getTime());
        int [][] arr = new int [n][n];
        System.out.println("Source values: ");
        for (int i = 0; i < n; i++ ) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = rnd.nextInt() % (n + 1);
                if (arr[i][j] >= 0)
                    System.out.print(" ");
                System.out.print( arr[i][j] + "\t");
            }
            System.out.println();
        }

        int cnt = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i > 0 &&
                        (arr[i][j] >= arr[i - 1][j] ||
                                j > 0 && arr[i][j] >= arr[i - 1][j - 1] ||
                                j < n - 1 && arr[i][j] >= arr[i - 1][j + 1]))
                    continue;
                if (i < n - 1 &&
                        (arr[i][j] >= arr[i + 1][j] ||
                                j > 0 && arr[i][j] >= arr[i + 1][j - 1] ||
                                j < n - 1 && arr[i][j] >= arr[i + 1][j + 1]))
                continue;
                if (j > 0 &&
                        arr[i][j] >= arr[i][j - 1] ||
                        j < n - 1 &&
                                arr[i][j] >= arr[i][j + 1])
                    continue;
                System.out.printf("%d: (%d, %d)\n", cnt, i, j);
                cnt++;
            }
        System.out.printf("Answer: %d\n", cnt);
        System.exit(0);
    }
}
