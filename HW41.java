import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;

import java.util.LinkedList;

public class HW41 {
    static int norm = 1;
    private static final double L = 2;
    public static final double EPS = java.lang.Math.pow(10, (-4));

    public static void main(String[] args) {
        LinkedList<RealMatrix> matrixLinkedList = new LinkedList<>();
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{2, 2, -1, 1}, {4, 3, -1, 2}, {8, 5, -3, 4}, {3, 3, -2, 2}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, 1, -1, 1}, {1, 4, -1, -1}, {-1, -1, 5, 1}, {1, -1, 1, 3}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{2.8, 2.1, -1.3, 0.3}, {-1.4, 4.5, -7.7, 1.3}, {0.6, 2.1, -5.8, 2.4}, {3.5, -6.5, 3.2, -7.9}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, 1, 1, 0, 1}, {1, 3, 1, 1, 0}, {1, 1, 5, -1, -1}, {0, 1, -1, 4, 0}, {1, 0, -1, 0, 4}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, -1, 0, -1, 0, 0}, {-1, 4, -1, 0, -1, 0}, {0, -1, 4, 0, 0, -1}, {-1, 0, 0, 4, -1, 0}, {0, -1, 0, -1, 4, -1}, {0, 0, -1, 0, -1, 4}}, false));
        matrixLinkedList.add(new Array2DRowRealMatrix(new double[][]{{4, -1, 0, 0, 0, 0}, {-1, 4, -1, 0, 0, 0}, {0, -1, 4, 0, 0, 0}, {0, 0, 0, 4, -1, 0}, {0, 0, 0, -1, 4, -1}, {0, 0, 0, 0, -1, 4}}, false));
        LinkedList<RealMatrix> constantsLinkedList = new LinkedList<>();
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{4, 6, 12, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{-2, -1, 0, 1}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 1, 1, 1}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{6, 6, 6, 6, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{0, 5, 0, 6, -2, 6}));
        constantsLinkedList.add(new Array2DRowRealMatrix(new double[]{0, 5, 0, 6, -2, 6}));
        LinkedList<RealMatrix> approxLinkedList = new LinkedList<>();
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 1.1, -1, -1}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{-0.8, 0, 0.3, 0.7}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.1, 0.2, -0.1, 0.3}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.5, 0.7, 1.5, 1.7, 1.4}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{1, 2, 1, 2, 1, 2}));
        approxLinkedList.add(new Array2DRowRealMatrix(new double[]{0.3, 1.5, 0.3, 1.5, 0.2, 1.5}));
        for (int i = 0; i < matrixLinkedList.size(); i++) {
            System.out.println("№" + (i + 1));
            System.out.println("Скорейший спуск");
            speedyDescent(matrixLinkedList.get(i), approxLinkedList.get(i), constantsLinkedList.get(i), norm);
            System.out.println("\nМинимальная невязка");
            minimalResiduals(matrixLinkedList.get(i), approxLinkedList.get(i), constantsLinkedList.get(i), norm);
            System.out.println();
        }
    }

    public static void speedyDescent(RealMatrix matrix, RealMatrix approximations, RealMatrix constants, int choose) {
        RealMatrix oldX;
        double norma;
        double step;
        int k = 0;
        RealMatrix discrepancy;
        if (!matrix.equals(matrix.transpose())) {
            constants = matrix.transpose().multiply(constants);
            for (int i = 0; i < matrix.getRowDimension(); i++) {
                for (int j = 0; j < matrix.getColumnDimension(); j++) {
                    matrix.setEntry(i, j, matrix.preMultiply(matrix.transpose()).getEntry(i, j));
                }
            }
        }
        do {
            discrepancy = (matrix.multiply(approximations)).subtract(constants);
            RealMatrix nowDiscrepancy = matrix.multiply(discrepancy);
            double up = 0;
            double down = 0;
            for (int i = 0; i < discrepancy.getRowDimension(); i++) {
                up += discrepancy.getEntry(i, 0) * discrepancy.getEntry(i, 0);
                down += discrepancy.getEntry(i, 0) * nowDiscrepancy.getEntry(i, 0);
            }
            step = up / down;
            oldX = approximations;
            approximations = approximations.subtract(discrepancy.scalarMultiply(step));
            norma = norm(approximations, oldX, choose);
            k++;
        } while (norma > EPS);
        System.out.println("Количество итераций " + k);
        System.out.println(oldX.getEntry(0, 0) + "; " + oldX.getEntry(1, 0) + "; " + oldX.getEntry(2, 0) + "; " + oldX.getEntry(3, 0));
    }

    public static void minimalResiduals(RealMatrix matrix, RealMatrix approximations, RealMatrix constants, int choose) {
        RealMatrix oldX;
        double norma;
        double step;
        int k = 0;
        RealMatrix discrepancy;
        do {
            discrepancy = (matrix.multiply(approximations)).subtract(constants);
            RealMatrix nowDiscrepancy = matrix.multiply(discrepancy);
            double up = 0;
            double down = 0;
            for (int i = 0; i < discrepancy.getRowDimension(); i++) {
                up += nowDiscrepancy.getEntry(i, 0) * discrepancy.getEntry(i, 0);
                down += nowDiscrepancy.getEntry(i, 0) * nowDiscrepancy.getEntry(i, 0);
            }
            step = up / down;
            oldX = approximations;
            approximations = approximations.subtract(discrepancy.scalarMultiply(step));
            norma = norm(approximations, oldX, choose);
            k++;
        } while (norma > EPS);
        System.out.println("Количество итераций " + k);
        System.out.println(oldX.getEntry(0, 0) + "; " + oldX.getEntry(1, 0) + "; " + oldX.getEntry(2, 0) + "; " + oldX.getEntry(3, 0));
    }

    public static double norm(RealMatrix approximations2, RealMatrix oldApproximations, int c) {
        RealMatrix buffNorm;
        buffNorm = approximations2.subtract(oldApproximations);
        int size = approximations2.getRowDimension();
        double num = 0;
        switch (c) {
            case (1) -> {
                for (int i = 0; i < size; ++i) {
                    buffNorm.setEntry(i, 0, Math.abs(buffNorm.getEntry(i, 0)));
                    if (buffNorm.getEntry(i, 0) > num) {
                        num = buffNorm.getEntry(i, 0);
                    }
                }
            }
            case (2) -> {
                for (int i = 0; i < size; ++i) {
                    num += Math.abs(buffNorm.getEntry(i, 0));
                }
            }
            case (3) -> {
                for (int i = 0; i < size; ++i) {
                    num += Math.pow((buffNorm.getEntry(i, 0)), 2 * L);
                }
                num = Math.pow(num, 1 / (2 * L));
            }
        }
        return num;
    }
}

