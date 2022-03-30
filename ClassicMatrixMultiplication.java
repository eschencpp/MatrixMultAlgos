import java.util.Random;


public class ClassicMatrixMultiplication{ //Rows in matrix 1 //Columns in matrix 2 // Columns in matrix 1 and Rows in Matrix 2
    static int n = 4;
    static int matrixA[][]= new int[n][n];
    static int matrixB[][]= new int[n][n];
    //int matrixA[][]={{1,2,3},{4,5,6},{7,8,9}};
    //int matrixB[][]={{1,3,4},{2,4,3},{1,2,4}};
    static int matrixProduct[][]=new int[n][n]; //New dimensions are [rows in matrix 1][columns in matrix 2]
    
    static int matrix0[][] = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    static int matrixTestA[][] = {{1,2,3,4},{5,6,7,8},{1,2,3,4},{5,6,7,1}};
    static int matrixTestB[][] = {{5,6,7,1},{5,3,2,5},{5,6,7,8},{1,2,3,4}};
    public void checkCompatibility(){
        
    }

    public static void printMatrix(int n, int[][] matrix){
            for(int i = 0; i < n; i++){
                System.out.println();
                for(int j = 0; j < n; j++){
                    System.out.printf("%d  ", matrix[i][j]);
                }
            }
            System.out.println();
        }

    public static void fillMatrix(int n, int[][] matrix1, int[][] matrix2){
        Random rand = new Random();
        int randInt = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                randInt = 1 + rand.nextInt(10); // Random Int from 1-10
                matrix1[i][j] = randInt;
                randInt = 1 + rand.nextInt(10); // Random Int from 1-10
                matrix2[i][j] = randInt;
            }
        }
        System.out.printf("Matrix A is: ");
        printMatrix(matrix1.length, matrix1);
        System.out.println();
        System.out.printf("Matrix B is: ");
        printMatrix(matrix1.length, matrix2);
    }

    public static int[][] matrixAddition(int n,int[][] matrix1, int[][] matrix2){
        int[][] matrixSum = new int[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrixSum[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return matrixSum;
    }

    public static int[][] matrixMult(int n, int[][] matrix1, int[][] matrix2){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrixProduct[i][j] = 0;
            
                for (int k = 0; k < n; k++){
                matrixProduct[i][j] = matrixProduct[i][j] + (matrix1[i][k] * matrix2[k][j]);
                }
            }
        }
        printMatrix(4, matrixProduct);
        return matrixProduct;
    }
    

    // MatrixProduct[MatrixOne[i][j] x MatrixTwo[j][i]
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
            // Split the matrix into 4 until n = 1
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
                // System.out.printf("\n Row %d Column %d Value %d",i ,j,matrixA11[i][j] );
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
                // System.out.printf("\n Row %d Column %d Value %d",i ,j,matrixA11[i][j] );
                }
            }
        }

        return C;
    }

    public static int[][] StrassensMatrixMult(int n, int[][] matrix1, int[][] matrix2){
        int[][] C = new int[n][n];
        return C;
    }




    public static void main(String[] args){
        fillMatrix(n, matrixA, matrixB);
        //printMatrix(4, matrixAddition(4, matrixA, matrixB));
        printMatrix(4, matrixTestA);
        printMatrix(4, matrixTestB);
        //matrixMult(4, matrixTestA, matrixTestB);
        //printMatrix(4, matrixProduct);
        printMatrix(4, divAndConq(4, matrixTestA, matrixTestB));
    }
}