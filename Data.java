public class Data {

    double left;
    double right;
    double approximation;

    public Data(double left, double right, double approximation) {
        this.left = left;
        this.right = right;
        this.approximation = approximation;
    }

    public Data(double approximation) {
        this.approximation = approximation;
    }
}