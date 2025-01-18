import java.util.*;

public class HW31 {
    public static void main(String[] args) {
        List<double[][]> eqs = new LinkedList<>();
        eqs.add(new double[][]{{2, 2, -1, 1, 4}, {4, 3, -1, 2, 6}, {8, 5, -3, 4, 12}, {3, 3, -2, 2, 6}});
        eqs.add(new double[][]{{1, 7, -9, -8, -7}, {-3, -18, 23, 28, 5}, {0, -3, 6, -1, 8}, {-1, -1, 1, 18, -29}});
        eqs.add(new double[][]{{3, -3, 7, -4, 0}, {-6, 9, -21, 9, 9}, {9, -12, 30, -22, -2}, {6, 0, 6, -31, 37}});
        eqs.add(new double[][]{{9, -5, -6, 3, -8}, {1, -7, 1, 0, 38}, {3, -4, 9, 0, 47}, {6, -1, 9, 8, -8}});
        eqs.add(new double[][]{{-6, -5, -3, -8, 101}, {5, -1, -5, -4, 51}, {-6, 0, 5, 5, -53}, {-7, -2, 8, 5, -63}});
        for (double[][] eq : eqs) {
            gaussMethod(eq);
        }
    }

    public static void gaussMethod(double[][] matrix) {
        int n = matrix.length;
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double c = -matrix[j][i] / matrix[i][i];
                for (int k = i; k < n + 1; k++) {
                    {
                        matrix[j][k] += c * matrix[i][k];
                    }
                }
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            x[i] = matrix[i][n] / matrix[i][i];
            for (int k = i - 1; k >= 0; k--) {
                matrix[k][n] -= matrix[k][i] * x[i];
            }
        }
        for (double currX : x) {
            System.out.printf("%.2f ", currX);
        }
        System.out.println("\n");
    }
}