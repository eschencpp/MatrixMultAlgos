/*
* CS3310 Project 1: Analysis of Matrix Multiplication Algorithms
* Eric Chen
* 4/7/2022
*/

import java.util.Random;
import java.util.Scanner;

public class MatrixMult{
    
    /**
     * Constructor
     * @param n - size of the matrices being multiplied
     */
    public MatrixMult(int n){

        if(n % 2 != 0){
            System.out.println("Error inputted matrix size is not allowed. ");
            return;
        }
    }

    //Method to print matrices
    public static void printMatrix(int n, int[][] matrix){
            for(int i = 0; i < n; i++){
                System.out.println();
                for(int j = 0; j < n; j++){
                    System.out.printf("%-6d  ", matrix[i][j]);
                }
            }
            System.out.println();
        }
    
    //Method to randomly generate test matrices. Range of numbers from -100 to 100.
    public static void fillMatrix(int n, int[][] matrix1, int[][] matrix2){
        Random rand = new Random();
        int randInt = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                randInt = -100 + rand.nextInt(200); // Random Int from -100 to 100
                matrix1[i][j] = randInt;
                randInt = -100 + rand.nextInt(200); // Random Int from -100 to 100
                matrix2[i][j] = randInt;
            }
        }
    }

    //Helper method to add matrices
    public static int[][] matrixAddition(int n,int[][] matrix1, int[][] matrix2){
        int[][] matrixSum = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrixSum[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return matrixSum;
    }

    //Helper method to subtract matrices
    public static int[][] matrixSubtract(int n,int[][] matrix1, int[][] matrix2){
        int[][] matrixSum = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrixSum[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return matrixSum;
    }

    /**
    * Increment a value by delta and return the new value. 
    *
    * @param  n   size of the matrix being multiplied
    * @param  matrix1   matrix 1 to be multiplied
    * @param  matrix2   matrix 2 to be multiplied
    * @return         Matrix C of the product size nxn
    */
    public static int[][] matrixMult(int n, int[][] matrix1, int[][] matrix2){
        int[][] C = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                C[i][j] = 0;
            
                for (int k = 0; k < n; k++){
                C[i][j] = C[i][j] + (matrix1[i][k] * matrix2[k][j]);
                }
            }
        }
        return C;
    }
    
    /**
    * Increment a value by delta and return the new value. 
    *
    * @param  n   size of the matrix being multiplied
    * @param  matrix1   matrix 1 to be multiplied
    * @param  matrix2   matrix 2 to be multiplied
    * @return         Matrix C of the product size nxn
    */
    public static int[][] divAndConq(int n, int[][] matrix1, int[][] matrix2){

        int matrixA11[][]= new int[n/2][n/2];
        int matrixA12[][]= new int[n/2][n/2];
        int matrixA21[][]= new int[n/2][n/2];
        int matrixA22[][]= new int[n/2][n/2];
        int matrixB11[][]= new int[n/2][n/2];
        int matrixB12[][]= new int[n/2][n/2];
        int matrixB21[][]= new int[n/2][n/2];
        int matrixB22[][]= new int[n/2][n/2];
        int[][] C = new int[n][n];

        // Base case
        if(n == 2){
            C[0][0] = (matrix1[0][0] * matrix2[0][0]) + (matrix1[0][1] * matrix2[1][0]);
			C[0][1] = (matrix1[0][0] * matrix2[0][1]) + (matrix1[0][1] * matrix2[1][1]);
			C[1][0] = (matrix1[1][0] * matrix2[0][0]) + (matrix1[1][1] * matrix2[1][0]);
			C[1][1] = (matrix1[1][0] * matrix2[0][1]) + (matrix1[1][1] * matrix2[1][1]);
            return C;
        }else{
            // Split the matrix into 4 until n = 2
            for(int i = 0; i < n/2; i++){
                for(int j = 0; j < n/2; j++){
                    matrixA11[i][j] = matrix1[i][j];
                    matrixA12[i][j] = matrix1[i][n/2 + j];
                    matrixA21[i][j] = matrix1[n/2 + i][j];
                    matrixA22[i][j] = matrix1[n/2 + i][n/2 + j];
                    //Matrix B
                    matrixB11[i][j] = matrix2[i][j];
                    matrixB12[i][j] = matrix2[i][n/2 + j];
                    matrixB21[i][j] = matrix2[n/2 + i][j];
                    matrixB22[i][j] = matrix2[n/2 + i][n/2 + j];
                }
            }

            int[][] C11 = matrixAddition(n/2, divAndConq(n/2, matrixA11, matrixB11), divAndConq(n/2, matrixA12, matrixB21));
            int[][] C12 = matrixAddition(n/2, divAndConq(n/2, matrixA11, matrixB12), divAndConq(n/2, matrixA12, matrixB22));
            int[][] C21 = matrixAddition(n/2, divAndConq(n/2, matrixA21, matrixB11), divAndConq(n/2, matrixA22, matrixB21));
            int[][] C22 = matrixAddition(n/2, divAndConq(n/2, matrixA21, matrixB12), divAndConq(n/2, matrixA22, matrixB22));

            //Puts together the 4 quadrants (C11,C12,C21,C22) into one matrix C.
            for(int i = 0; i < n/2; i++){
                for(int j = 0; j < n/2; j++){
                    C[i][j] = C11[i][j];
                    C[i][n/2 + j] = C12[i][j];
                    C[n/2 + i][j] = C21[i][j];
                    C[n/2 + i][n/2 + j] = C22[i][j];
                }
            }
        }

        return C;
    }

    /**
    * Strassens Matrix Multiplication Algorithm. 
    *
    * @param  n   size of the matrix being multiplied
    * @param  matrix1   matrix 1 to be multiplied
    * @param  matrix2   matrix 2 to be multiplied
    * @return         Matrix C of the product size nxn
    */
    public static int[][] StrassensMatrixMult(int n, int[][] matrix1, int[][] matrix2){
        
        int matrixA11[][]= new int[n/2][n/2];
        int matrixA12[][]= new int[n/2][n/2];
        int matrixA21[][]= new int[n/2][n/2];
        int matrixA22[][]= new int[n/2][n/2];
        int matrixB11[][]= new int[n/2][n/2];
        int matrixB12[][]= new int[n/2][n/2];
        int matrixB21[][]= new int[n/2][n/2];
        int matrixB22[][]= new int[n/2][n/2];
        int[][] C = new int[n][n];

        // Base case
        if(n == 2){
            C[0][0] = (matrix1[0][0] * matrix2[0][0]) + (matrix1[0][1] * matrix2[1][0]);
			C[0][1] = (matrix1[0][0] * matrix2[0][1]) + (matrix1[0][1] * matrix2[1][1]);
			C[1][0] = (matrix1[1][0] * matrix2[0][0]) + (matrix1[1][1] * matrix2[1][0]);
			C[1][1] = (matrix1[1][0] * matrix2[0][1]) + (matrix1[1][1] * matrix2[1][1]);
            return C;
        }else{
            // Split the matrix into 4 until n = 1
            for(int i = 0; i < n/2; i++){
                for(int j = 0; j < n/2; j++){
                    matrixA11[i][j] = matrix1[i][j];
                    matrixA12[i][j] = matrix1[i][n/2 + j];
                    matrixA21[i][j] = matrix1[n/2 + i][j];
                    matrixA22[i][j] = matrix1[n/2 + i][n/2 + j];
                    //Split Matrix B
                    matrixB11[i][j] = matrix2[i][j];
                    matrixB12[i][j] = matrix2[i][n/2 + j];
                    matrixB21[i][j] = matrix2[n/2 + i][j];
                    matrixB22[i][j] = matrix2[n/2 + i][n/2 + j];
                }
            }

            int[][] P = StrassensMatrixMult(n/2, matrixAddition(n/2, matrixA11, matrixA22), matrixAddition(n/2, matrixB11, matrixB22));
            int[][] Q = StrassensMatrixMult(n/2, matrixAddition(n/2, matrixA21, matrixA22), matrixB11);
            int[][] R = StrassensMatrixMult(n/2, matrixA11, matrixSubtract(n/2, matrixB12, matrixB22));
            int[][] S = StrassensMatrixMult(n/2, matrixA22, matrixSubtract(n/2, matrixB21, matrixB11));
            int[][] T = StrassensMatrixMult(n/2, matrixAddition(n/2, matrixA11, matrixA12), matrixB22);
            int[][] U = StrassensMatrixMult(n/2, matrixSubtract(n/2, matrixA21, matrixA11), matrixAddition(n/2, matrixB11, matrixB12));
            int[][] V = StrassensMatrixMult(n/2, matrixSubtract(n/2, matrixA12, matrixA22), matrixAddition(n/2, matrixB21, matrixB22));

            int[][] C11 = matrixSubtract(n/2, matrixAddition(n/2, matrixAddition(n/2, P, S), V), T);
            int[][] C12 = matrixAddition(n/2, R, T);
            int[][] C21 = matrixAddition(n/2, Q, S);
            int[][] C22 = matrixSubtract(n/2, matrixAddition(n/2, matrixAddition(n/2, P, R), U), Q);

            //Puts together the 4 quadrants (C11,C12,C21,C22) into one matrix C.
            for(int i = 0; i < n/2; i++){
                for(int j = 0; j < n/2; j++){
                    C[i][j] = C11[i][j];
                    C[i][n/2 + j] = C12[i][j];
                    C[n/2 + i][j] = C21[i][j];
                    C[n/2 + i][n/2 + j] = C22[i][j];
                }
            }
        }

        return C;
    }




    public static void main(String[] args){

        //Take input from user to set n (matrix size)
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the matrix size you are trying to multiply. (2,4,8,16,32,64,128,256,512,1024)");
        int n = sc.nextInt();
        sc.close();
        MatrixMult mm = new MatrixMult(n);

        int matrixA[][]= new int[n][n];
        int matrixB[][]= new int[n][n];
        int numOfSets = 200; //Number of data sets tested
        int iterations = 15; //Iterations per set
        long startMM = 0; //MM start timer
        long endMM = 0; //MM end timer
        long totalTime = 0; //Total time for ClassicalMM
        long totalTimeDC = 0;//Total time for Divide and Conquer MM
        long totalTimeStrass = 0;//Total time for Strassens MM
        long totalProgramRuntime = 0;//Start timer for whole program
        long totalProgramEndtime = 0;//End time for whole program

        totalProgramRuntime =System.nanoTime();
        for(int i = 0; i < numOfSets; i++){
            System.out.println("\nData set #" + (i+1));
            fillMatrix(n, matrixA, matrixB);
            System.out.printf("Matrix A is: ");
            printMatrix(matrixA.length, matrixA);
            System.out.println();
            System.out.printf("Matrix B is: ");
            printMatrix(matrixB.length, matrixB);

            //Times start and end only while doing MM. 
            for(int j = 0; j < iterations; j++){
                //Classical
                startMM = System.nanoTime();
                mm.matrixMult(n, matrixA, matrixB);
                endMM = System.nanoTime();
                totalTime += (endMM - startMM);
                //Divide and Conquer
                startMM = System.nanoTime();
                mm.divAndConq(n, matrixA, matrixB);
                endMM = System.nanoTime();
                totalTimeDC += (endMM - startMM);
                //Strassens
                startMM = System.nanoTime();
                mm.StrassensMatrixMult(n, matrixA, matrixB);
                endMM = System.nanoTime();
                totalTimeStrass += (endMM - startMM);
            }

        System.out.printf("\nThe product of Matrix A and Matrix B is: ");
        printMatrix(n, mm.matrixMult(n, matrixA, matrixB));
        System.out.println("\nTotal time Classical: " + totalTime);
        System.out.println("Total time DivConq: " + totalTimeDC);
        System.out.println("Total time Strassens: " + totalTimeStrass);
        }
        System.out.printf("\nTo multiply matrices of size %dx%d: ",n,n);
        System.out.println("\nThe average time to execute one Classical Matrix Multiplication is: " + totalTime/(iterations*numOfSets) + " nanoseconds.");
        System.out.println("The average time to execute one Divide and Conquer Matrix Multiplication is: " + totalTimeDC/(iterations*numOfSets) + " nanoseconds.");
        System.out.println("The average time to execute one Strassens Matrix Multiplication is: " + totalTimeStrass/(iterations*numOfSets) + " nanoseconds.");
        totalProgramEndtime = System.nanoTime();
        System.out.println(((float)totalProgramEndtime - (float)totalProgramRuntime)/1000000000 + " Seconds has elapsed."); //Total program runtime including filling matrices.
    }
}