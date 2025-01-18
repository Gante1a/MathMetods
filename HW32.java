import java.util.*;

public class HW32 {

    final static double EPS = Math.pow(10, -15);
    final static int L = 2;
    static final String[] normList = new String[]{"||X||∞", "||X||₁", "||X||₂ₗ"};

    public static void main(String[] args) {
        System.out.println("метод простых итераций" + "\n");
        List<double[][]> eqs = new LinkedList<>();
        eqs.add(new double[][]{{12, -3, -1, 3, -31}, {5, 20, 9, 1, 90}, {6, -3, -21, -7, 119}, {8, -7, 3, -27, 71}});
        eqs.add(new double[][]{{28, 9, -3, -7, -159}, {-5, 21, -5, -3, 63}, {-8, 1, -16, 5, -45}, {0, -2, 5, 8, 24}});
        eqs.add(new double[][]{{21, 1, -8, 4, -119}, {-9, -23, -2, 4, 79}, {7, -1, -17, 6, -24}, {8, 8, -4, -26, -52}});
        eqs.add(new double[][]{{14, -4, -2, 3, 38}, {-3, 23, -6, -9, -195}, {-7, -8, 21, -5, -27}, {-2, -2, 8, 18, 142}});

        for (double[][] eq : eqs) {
            for (int norm = 1; norm <= 3; norm++) {
                System.out.println("количество итераций для " + normList[norm - 1] + " " + simpleIteration(eq, norm));
            }
            System.out.println("\n");
        }

        System.out.println("\nметод зейделя");
        for (double[][] matrix : eqs) {
            for (int norm = 1; norm <= 3; norm++) {
                System.out.println("количество итераций для " + normList[norm - 1] + " " + zeidelMethod(matrix, norm));
            }
            System.out.println("\n");
        }
    }

    public static int simpleIteration(double[][] matrix, int norm) {
        int iteration = 0;
        double[] currentX = new double[matrix[0].length - 1];
        double currentEps;
        double[] oldX = new double[currentX.length];
        double[][] matrixB = new double[matrix.length][matrix[0].length - 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    matrixB[i][j] = 0;
                } else {
                    matrixB[i][j] = -matrix[i][j] / matrix[i][i];
                }
            }
        }
        do {
            for (int i = 0; i < matrix.length; i++) {
                double sum = 0;
                for (int j = 0; j < matrix.length; j++) {
                    sum += matrixB[i][j] * oldX[j];
                }
                currentX[i] = sum + matrix[i][matrix[0].length - 1] / matrix[i][i];
            }
            currentEps = findNorm(norm, currentX, oldX);
            System.arraycopy(currentX, 0, oldX, 0, oldX.length);
            iteration++;
        } while (currentEps > EPS);
        if (norm == 1) {
            for (double x : currentX) {
                System.out.printf("%.2f\t", x);
            }
            System.out.println("\n");
        }
        return iteration;
    }

    public static int zeidelMethod(double[][] matrix, int normNumber) {
        int iteration = 0;
        double currentEps;
        double[] oldX = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            oldX[i] = 0.0;
        }
        do {
            double[] currentX = new double[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                currentX[i] = matrix[i][matrix.length];
                for (int j = 0; j < matrix.length; j++) {
                    if (j < i) {
                        currentX[i] -= matrix[i][j] * currentX[j];
                    }
                    if (j > i) {
                        currentX[i] -= matrix[i][j] * oldX[j];
                    }
                }
                currentX[i] /= matrix[i][i];
            }
            iteration++;
            currentEps = findNorm(normNumber, currentX, oldX);
            oldX = currentX;
        } while (currentEps > EPS);
        if (normNumber == 1) {
            for (int i = 0; i < matrix.length; i++) {
                System.out.printf("%.2f\t", oldX[i]);
            }
            System.out.println("\n");
        }
        return iteration;
    }

    public static double findNorm(int numOfNorm, double[] currentX, double[] oldX) {
        double norm = 0;
        int size = currentX.length;
        switch (numOfNorm) {
            case (1) -> {
                double[] values = new double[size];
                for (int i = 0; i < size; ++i) {
                    values[i] = Math.abs(currentX[i] - oldX[i]);
                }
                DoubleSummaryStatistics stat = Arrays.stream(values).summaryStatistics();
                return stat.getMax();
            }
            case (2) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.abs(currentX[i] - oldX[i]);
                }
                return norm;
            }
            case (3) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.pow(currentX[i] - oldX[i], L * 2);
                }
                return Math.pow(norm, 1. / (L * 2));
            }
        }
        return 0;
    }
}
