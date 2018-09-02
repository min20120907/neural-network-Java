/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import java.util.Arrays;

/**
 *
 * @author min20120907
 */
public class JavaApplication4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // TODO code application logic here
        float[][] INPUT = {{0, 0, 1},
        {0, 1, 1},
        {1, 0, 1},
        {1, 1, 1}};
        float[][] OUTPUT = {{0},
        {1},
        {1},
        {1}};
        float[][] syn0 = randomizeArray(3, 4);
        float[][] syn1 = randomizeArray(4, 1);

        int train_rate = 60000;

        for (int j = 0; j < train_rate; j++) {
            float[][] l0 = INPUT;
            float[][] l1 = nonlin(dot(l0, syn0), false);
            float[][] l2 = nonlin(dot(l1, syn1), false);
            float l2_error2 = 0;
            float[][] l2_error = l2;
            for (int i = 0; i < l2.length; i++) {
                for (int k = 0; k < l2[0].length; k++) {
                    l2_error[i][k] = Math.abs(OUTPUT[i][k] - l1[i][k]);
                    l2_error2 += Math.abs(OUTPUT[i][k] - l1[i][k]);
                }
            }

            if ((j % 10000) == 0) {

                System.out.println("Error: " + l2_error2 / 4);
            }
            float[][] l2_delta = nonlin(l2, true);
            for (int i = 0; i < l2.length; i++) {
                for (int k = 0; k < l2[0].length; k++) {
                    l2_delta[i][k] = l2_error[i][k] * nonlin(l2, true)[i][k];
                }
            }
            float[][] l1_error = dot(l2_delta, syn1);
            float[][] l1_delta = nonlin(l1, true);
            for (int i = 0; i < l1.length; i++) {
                for (int k = 0; k < l1[0].length; k++) {

                    l1_delta[i][0] += l1_error[i][0] * (nonlin(l1, true)[i][0]);
                }
            }

            for (int h = 0; h < syn1.length; h++) {
                for (int g = 0; g < syn1[0].length; g++) {
                    syn1[h][g] += dot(l1, l2_delta)[h][g];

                }
            }
            for (int r = 0; r < syn0.length; r++) {
                for (int s = 0; s < syn0[0].length; s++) {
                    syn0[r][s] += dot(l0, l1_delta)[r][s];

                }
            }
            if (j == train_rate - 1) {
                System.out.println("Result: ");
                for (int r = 0; r < l2.length; r++) {
                    for (int s = 0; s < l2[0].length; s++) {
                        System.out.println(l2[r][s]);

                    }
                }
            }

        }

    }

    public static float[][] randomizeArray(int a, int b) {
        float[][] m = new float[a][b];

        for (float[] r : m) {
            Arrays.fill(r, (float) (Math.random() * 10));
        }
        return m;
    }

    public static float[][] nonlin(float[][] x, boolean deriv) {
        float newArray[][] = x;
        if (deriv == true) {
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x[0].length; j++) {

                    newArray[i][j] = x[i][j] * (1 - x[i][j]);
                }
            }
        } else {
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x[0].length; j++) {

                    newArray[i][j] = (float) (1 / (1 + Math.exp(-x[i][j])));
                }
            }
        }
        return newArray;
    }

    public static float[][] dot(float[][] firstarray, float[][] secondarray) {


        /* Create another 2d array to store the result using the original arrays' lengths on row and column respectively. */
        float[][] result = new float[firstarray.length][secondarray[0].length];

        /* Loop through each and get product, then sum up and store the value */
        for (int i = 0; i < firstarray.length; i++) {
            for (int j = 0; j < secondarray[0].length; j++) {
                for (int k = 0; k < firstarray[0].length; k++) {
                    result[i][j] += firstarray[i][k] * secondarray[k][j];
                }
            }
        }
        /* Show the result */
        return result;
    }
}
