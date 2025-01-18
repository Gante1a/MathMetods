import java.util.LinkedList;

public class HW12 {
    public static final double eps = Math.pow(10, -8);

    public static void main(String[] args) {
        LinkedList<Function> equations = new LinkedList<>();
        LinkedList<Data> data = new LinkedList<>();
        LinkedList<Function> derivatives = new LinkedList<>();
        equations.add(x -> Math.sin(x));
        equations.add(x -> Math.log(x) - 1);
        data.add(new Data(3));
        data.add(new Data(3));
        derivatives.add(x -> Math.cos(x));
        derivatives.add(x -> x);
        for (int i = 0; i < equations.size(); i++) {
            System.out.println((i + 1) + ".");
            System.out.println("метод ньютона: ");
            newton(equations.get(i), data.get(i).approximation, derivatives.get(i));
        }
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

