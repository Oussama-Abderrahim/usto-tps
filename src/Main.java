import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Oussama on 09/06/2019.
 */
public class Main
{
    public static void main(String[] args)
    {
        int n = 3;

        Scanner sc = new Scanner(System.in);

        System.out.println("Entrer le nombre d'inconnus");
        n = sc.nextInt();

        int[][] A = new int[n][n];

        int[] B = new int[n];

        System.out.println("A : ");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
//                A[i][j] = (int) (Math.random() * 20) - 10;
                A[i][j] = sc.nextInt();
            }
        }

        printMat(A, n);

        System.out.println("Determinant de A est : " + det(A, n));

        System.out.println("B : ");
        for (int i = 0; i < n; i++) {
            B[i] = sc.nextInt();
        }

        int[] C = solve(A, B, n);

        System.out.println("Resultat : ");
        printArr(C, n);
    }

    private static void printArr(int[] A, int n)
    {
        for (int j = 0; j < n; j++) {
            System.out.println(A[j]);
        }
    }

    private static void printMat(int[][] A, int n)
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[] solve(int[][] A, int[] B, int n)
    {
        int detA = det(A, n);

        int[] X = new int[n];

        for (int i = 0; i < n; i++) {
            int detI = det(generateMatrixB(A, n, i, B), n);

            X[i] = detI / detA;
        }

        return X;
    }

    private static int det(int[][] A, int n)
    {
        if (n == 2) {
            return A[0][0] * A[1][1] - A[1][0] * A[0][1];
        }
        else {
            int determinant = 0;
            int sign = 1;
            for (int j = 0; j < n; j++) {
                determinant += sign
                        * A[0][j]
                        * det(generateSubMatrix(A, n, j), n - 1);
                sign *= -1;
            }
            return determinant;
        }
    }

    private static int[][] generateSubMatrix(int[][] A, int n, int k)
    {
        int[][] subMat = new int[n - 1][n - 1];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j < k)
                    subMat[i - 1][j] = A[i][j];
                else if (j > k)
                    subMat[i - 1][j - 1] = A[i][j];
            }
        }
        return subMat;
    }

    private static int[][] generateMatrixB(int[][] A, int n, int k, int[] B)
    {
        int[][] subMat = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == k)
                    subMat[i][j] = B[i];
                else
                    subMat[i][j] = A[i][j];
            }
        }
        return subMat;
    }
}
