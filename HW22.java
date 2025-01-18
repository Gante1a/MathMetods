import java.util.LinkedList;
import java.util.List;

public class HW22 {
    final static int K = 8;
    final static double EPS = Math.pow(10, -K);

    public static void main(String[] args) {
        LinkedList<FunctionNewton> task21 = new LinkedList<>();
        LinkedList<Dot> dots21 = new LinkedList<>();
        LinkedList<FunctionNewton> task22 = new LinkedList<>();
        LinkedList<Dot> dots22 = new LinkedList<>();
        task21.add((x, y, a, alpha, betta) -> Math.tan(x * y + a) - Math.pow(x, 2));
        task21.add((x, y, a, alpha, betta) -> Math.pow(x, 2) * alpha + Math.pow(y, 2) * betta - 1);
        task21.add((x, y, a, alpha, betta) -> (1 / Math.pow(Math.cos(x * y + a), 2)) * y - 2 * x);
        task21.add((x, y, a, alpha, betta) -> (1 / Math.pow(Math.cos(x * y + a), 2)) * x);
        task21.add((x, y, a, alpha, betta) -> 2 * x * alpha);
        task21.add((x, y, a, alpha, betta) -> 2 * y * betta);
        dots21.add(new Dot(0.2, 0.6, 2, 0.8, 0.5));
        dots21.add(new Dot(0.4, 0.8, 2, -0.3, 0.6));
        dots21.add(new Dot(0.3, 0.2, 3, 1, 0.5));
        dots21.add(new Dot(0, 0.6, 2, 0.6, 0.6));
        task22.add((x, y, a, alpha, betta) -> Math.pow(x, 2) + Math.pow(y, 2) - 2);
        task22.add(((x, y, a, alpha, betta) -> Math.exp(x - 1) + Math.pow(y, 3) - 2));
        task22.add((x, y, a, alpha, betta) -> 2 * x);
        task22.add((x, y, a, alpha, betta) -> 2 * y);
        task22.add((x, y, a, alpha, betta) -> Math.exp(x - 1));
        task22.add((x, y, a, alpha, betta) -> 2 * Math.pow(y, 2));
        dots22.add(new Dot(0, 0, 0, -0.5, 1));
        for (int i = 0; i < dots21.size(); i++) {
            System.out.println("\n№2.1." + (i + 1));
            newton(task21, dots21.get(i));
        }
        System.out.println("№2.2");
        newton(task22, dots22.get(0));
    }

    public static void newton(List<FunctionNewton> data, Dot dot) {
        FunctionNewton f1 = data.get(0);
        FunctionNewton f2 = data.get(1);
        FunctionNewton df1x = data.get(2);
        FunctionNewton df1y = data.get(3);
        FunctionNewton df2x = data.get(4);
        FunctionNewton df2y = data.get(5);
        int iteration = 1;
        double x1 = dot.x, y1 = dot.y, a = dot.a, alpha = dot.alpha, betta = dot.betta;
        double x, y;
        do {
            x = x1;
            y = y1;
            double func1 = f1.calculate(x, y, a, alpha, betta);
            double func2 = f2.calculate(x, y, a, alpha, betta);
            double detFunc1x = df1x.calculate(x, y, a, alpha, betta);
            double detFunc1y = df1y.calculate(x, y, a, alpha, betta);
            double detFunc2x = df2x.calculate(x, y, a, alpha, betta);
            double detFunc2y = df2y.calculate(x, y, a, alpha, betta);
            double detA1 = (func1 * detFunc2y) - (func2 * detFunc1y);
            double detA2 = (func2 * detFunc1x) - (func1 * detFunc2x);
            double detJ = (detFunc1x * detFunc2y) - (detFunc1y * detFunc2x);
            x1 = x - (detA1 / detJ);
            y1 = y - (detA2 / detJ);
            iteration++;
        } while (Math.sqrt(Math.pow(f1.calculate(x1, y1, a, alpha, betta), 2) + Math.pow(f2.calculate(x1, y1, a, alpha, betta), 2)) > EPS &&
                Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)) > EPS);
        System.out.println("количество итераций " + iteration);
        System.out.println("текущее приближение " + x1 + "; " + y1);
    }
}
