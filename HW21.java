import java.util.LinkedList;

public class HW21 {

    final static int K = 8;
    final static int A = 2;
    final static double step = 0.01;
    final static int minRange = -1000;
    final static int maxRange = 1000;
    final static double EPS = Math.pow(10, -K);

    public static void main(String[] args) {
        LinkedList<Function> equations = new LinkedList<>();
        LinkedList<Function> derivatives = new LinkedList<>();
        equations.add(x -> Math.pow(x, 3) + Math.pow(x, 2) - x + 0.5);
        equations.add(x -> Math.pow(Math.E, x) / A - x - 1);
        equations.add(x -> Math.pow(x, 3) - 20 * x + 1);
        equations.add(x -> Math.pow(2, x) + Math.pow(x, 2) - 2);
        equations.add(x -> x * Math.log(x + 2) - 1 + Math.pow(x, 2));
        equations.add(x -> Math.pow(x, 3) / A - A * Math.cos(x));
        derivatives.add(x -> 3 * Math.pow(x, 2) + 2 * x - 1);
        derivatives.add(x -> Math.pow(Math.E, x) / A - 1);
        derivatives.add(x -> 3 * Math.pow(x, 2) - 20);
        derivatives.add(x -> Math.pow(2, x) * Math.log(2) + 2 * x);
        derivatives.add(x -> 2 * x + x / (x + 2) + Math.log(x + 2));
        derivatives.add(x -> 3 * Math.pow(x, 2) / A + A * Math.sin(x));
        for (int i = 0; i < equations.size(); i++) {
            System.out.println((i + 1) + ")");
            findDots(equations.get(i), derivatives.get(i));
        }
    }

    private static void simpleIteration(Function equation, Function derivative, double x) {
        double lambdaD = derivative.calculate(x);
        double lambda;
        if (lambdaD > 0){
            lambda = 0.001;
        } else {
            lambda = -0.001;
        }
        double x0;
        int iteration = 0;
        do {
            x0 = x;
            x = x0 - lambda * equation.calculate(x0);
            iteration++;
        } while ((Math.abs(x - x0)) > EPS);
        System.out.println("количество итераций " + iteration);
        System.out.println("текущее приближение " + x + "\n");
    }

    private static void findDots(Function equation, Function derivative) {
        int count = 1;
        for (double i = minRange; i <= maxRange; i += step) {
            if (equation.calculate(i) * equation.calculate(i + step) < 0 || equation.calculate(i) == 0) {
                System.out.println(count++ + " корень:");
                simpleIteration(equation, derivative, i);
            }
        }
    }
}
