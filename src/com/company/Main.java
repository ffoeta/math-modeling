package com.company;

public class Main {

    static int N = 4;
    static int L = 1;
    static double H = 1;
    static double H1 = H;
    static double HT = H;

    static double KSI1 = 1;
    static double KSI2 = -1;
    static double V1 = 1;
    static double V2 = -1;

    static double y[] = new double[N+1];
    static double alpha[] = new double[N+1];
    static double betta[] = new double[N+1];

    static double k = 1.0;
    static double q = 1.0;
    static double f = 1.0;

    static double u[] = new double[N+1];

    static double[][] A = new double[N+1][N+1];
    static double G[] = new double[N+1];
    static double X[] = new double[N+1];

    //    --------------------------
    //    --------------------------







    static double[][] testM = new double[][]{{15, 3, 0, 0}, {11, 21, 5, 0}, {0,9,19,7},{0, 0, 7, 15}};
    static double[] res = new double[]{21,68,103,81};







    //    --------------------------
    //    --------------------------

    /*
    *(-k12/h) * v1 + [(k12/h1) + X1 + H*q] * v = Hf + V1
    *
    *(-k12/h) * v-1 + [(k12/h1) + k-12/h + H*q] * v - (k12/h1) * v1 = Hf
    *
    *(-k12/h) * v-1 + [(k-12/h) + X2 + H*q] * v = Hf + V2
    */


    //(-k12/h) * v1 + [(k12/h1) + X1 + H*q] * v = Hf + V1

    public static double fb0_(double k12, double h1, double X1, double H, double q ){
        return (k12/h1) + X1 + H * q;
    }

    public static double fc0_(double k12, double h){
        return (-k12/h);
    }

    public static double ff0_(double H, double f, double V1){
        return H * f + V1;
    }

    //(-k12/h) * v-1 + [(k12/h1) + k-12/h + H*q] * v - (k12/h1) * v1 = Hf

    public static double fa(double k_12, double h){
        return (-k_12/h);
    }

    public static double fb(double k12, double h1, double k_12, double h, double H, double q ){
        return (k12/h1) + k_12/h + H * q;
    }

    public static double fc(double k12, double h1){
        return (-k12/h1);
    }

    public static double ff(double H, double f){
        return H * f;
    }

    //(-k12/h) * v-1 + [(k-12/h) + X2 + H*q] * v = Hf + V2

    public static double faN(double k_12, double h){
        return (-k_12/h);
    }

    public static double fbN(double k12, double h1, double X1, double H, double q ){
        return (k12/h1) + X1 + H * q;
    }

    public static double ffN(double H, double f, double V1){
        return H * f + V1;
    }

//    --------------------------


    public static void printArray(double[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }


    public static void printV(double[] v) {
        for (int i = 0; i < v.length; i++) {
            System.out.print(v[i]);
            System.out.print(" ");
        }
        System.out.println();
    }


    public static void fillMatrix(double[][] matrix){

        matrix[0][0] = fb0_(1,1,1,1,1);
        matrix[0][1] = fc0_(1,1);

        for (int i = 1; i < N; i++) {
            matrix[i][i-1] =    fa(1,1);
            matrix[i][i]   =    fb(1,1,1,1,1, 1);
            matrix[i][i+1] =    fc(1,1);
        }

        matrix[N][N-1] = faN(1, 1);
        matrix[N][N] = fbN(1, 1,1,1, 1);
    }

    public static void fillG(double[] g) {
        g[0] = ff0_(1, 1, 1);

        for (int i = 1; i < N+1; i++) {
            g[i] = ff(1,1);
        }

        g[N] = ffN(1,1,1);
    }

    public static void sp(double[][] arr, double[] x, double[] a, double[] b, double[] y) {

        int n = arr.length - 1;

        y[0] = arr[0][0];
        a[0] = -arr[0][1] / y[0];
        b[0] = x[0] / y[0];

        for (int i = 1; i < n; i++) {
            y[i] = arr[i][i] + arr[i][i - 1] * a[i - 1];
            a[i] = -arr[i][i + 1] / y[i];
            b[i] = (x[i] - arr[i][i - 1] * b[i - 1]) / y[i];
        }

        y[n] = arr[n][n] + arr[n][n - 1] * a[n - 1];
        b[n] = (x[n] - arr[n][n - 1] * b[n - 1]) / y[n];

    }

    public static void bp(double[] a, double[] b, double[] x){
        int n = x.length;
        for (int i = n-1; i >= 0; i--) {
            if (i == n-1) {
                x[i] = b[i];
                continue;
            };
            x[i] = a[i] * x[i+1] + b[i];
        }
    }


    public static void main(String[] args) {

        fillMatrix(A);
        fillG(G);

        sp(A,G,alpha,betta,y);
        bp(alpha, betta, X);

        printV(X);
//        sp(testM, res, alpha, betta, y);
//        bp(alpha, betta, res);
//
//        printV(res);
    }
}
