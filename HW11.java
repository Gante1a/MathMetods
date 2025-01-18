import java.util.LinkedList;

public class HW11 {
    public static final double eps = Math.pow(10, -4);
    public static double a = 2, n = 2;

    public static void main(String[] args) {
        LinkedList<Function> equations = new LinkedList<>();
        LinkedList<Data> data = new LinkedList<>();
        LinkedList<Function> derivatives = new LinkedList<>();
        equations.add(x -> Math.sin(x) - 2 * Math.pow(x, 2) + 0.5);
        equations.add(x -> Math.pow(x, n) - a);
        equations.add(x -> Math.sqrt(1 - Math.pow(x, 2)) - Math.exp(x) + 0.1);
        equations.add(x -> Math.pow(x, 6) - 5 * Math.pow(x, 3) - 2);
        equations.add(x -> Math.log(x) - (1 / (1 + Math.pow(x, 2))));
        equations.add(x -> Math.pow(3, x) - 5 * Math.pow(x, 2) + 1);
        data.add(new Data(0, 1, 1));
        data.add(new Data(0, 2, 1));
        data.add(new Data(0, 1, 0));
        data.add(new Data(-1, 0, -1));
        data.add(new Data(0, 2, 1));
        data.add(new Data(0, 1, 1));
        derivatives.add(x -> Math.cos(x) - 4 * x);
        derivatives.add(x -> Math.pow(x, 3));
        derivatives.add(x -> -1 * ((x + Math.sqrt(1 - Math.pow(x, 2))) * Math.exp(x)) / Math.sqrt(1 - Math.pow(x, 2)));
        derivatives.add(x -> 6 * Math.pow(x, 5) - 15 * Math.pow(x, 2));
        derivatives.add(x -> (1 / x) + ((2 * x) / ((1 + Math.pow(x, 2))) * (1 + Math.pow(x, 2))));
        derivatives.add(x -> Math.log(3) * Math.pow(3, x) - 10 * x);
        for (int i = 0; i < equations.size(); i++) {
            System.out.println((i + 1) + ".");
            System.out.println("метод дихотомии: ");
            dichotomy(equations.get(i), data.get(i).left, data.get(i).right);
            System.out.println("метод ньютона: ");
            newton(equations.get(i), data.get(i).approximation, derivatives.get(i));
        }
    }

    public static void dichotomy(Function func, double left, double right) {
        double segment = right - left;
        int iteration = 0;
        while (segment > eps) {
            double currentX = (left + right) / 2;
            if (func.calculate(left) * func.calculate(currentX) < 0) {
                right = currentX;
            } else {
                left = currentX;
            }
            iteration++;
            segment = Math.abs(right - left);
        }
        System.out.println("текущее приближение " + (left + right) / 2);
        System.out.println("номер итерации " + iteration);
    }

    public static void newton(Function func, double approximation, Function derivate) {
        int iteration = 0;
        double currentX = approximation;
        double nextX;
        double diff;
        do {
            double function = func.calculate(currentX);
            double fDerivate = derivate.calculate(currentX);
            nextX = currentX - (function / fDerivate);
            diff = Math.abs(nextX - currentX);
            currentX = nextX;
            iteration++;
        } while (diff > eps);
        System.out.println("текущее приближение " + nextX);
        System.out.println("Номер итерации " + iteration + "\n");
    }
}


