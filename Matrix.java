import java.util.Arrays;
import java.lang.Math;

public class Matrix {
    int rows;
    int cols;
    double[][] data;

    public Matrix(int rows_, int cols_) {
        // constructor for Matrix class
        rows = rows_;
        cols = cols_;

        data = new double[rows][cols];
        for (double[] row : data) {
            Arrays.fill(row, 0);
        }
    }

    public Matrix copy(){
        // copy matrix
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = data[i][j];
            }
        }

        return result;
    }

    public static Matrix fromArray(double[] arr) {
        // load matrix from array
        Matrix result = new Matrix(arr.length, 1);
        for (int i = 0; i < result.rows; i++) {
            result.data[i][0] = arr[i];
        }

        return result;
    }

    public static double[] toArray(Matrix m) {
        // convert matrix to array
        double[] result = new double[m.rows * m.cols];
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                result[i * m.cols + j] = m.data[i][j];
            }
            
        }

        return result;
    }

    public void randomize() {
        // randomize all values in matrix
        map(0, 0, "random");
    }

    public static Matrix transpose(Matrix m) {
        // static transposing
        Matrix result = new Matrix(m.cols, m.rows);
        
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                result.data[j][i] = m.data[i][j];
            }
        }

        return result;
    }

    static double sigmoid(double x) {
        // sigmoid function
        return 1 / (1 + Math.exp(-x));
    }

    static double function(double x, double m, double b, String f) {
        // function for mapping
        switch(f) {
            case "linear":
                return m * x + b;
            case "sigmoid":
                return sigmoid(x);
            case "dsigmoid":
                return x * (1 - x);
            case "tanh":
                return Math.tanh(x);
            case "dtanh":
                return 1 - Math.pow(x, 2);
            case "random":
                return Math.random() * 2 - 1;
            default:
                throw new RuntimeException("Operation is not an available operation.");
        }
    }


    public void map(double m, double b, String f) {
        // mapping a function to each element
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = function(data[i][j], m, b, f);
            }
        }
    }

    public static Matrix map(Matrix m, double n, double b, String f) {
        // mapping a function to each element (static)
        Matrix result = new Matrix(m.rows, m.cols);

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                result.data[i][j] = function(m.data[i][j], n, b, f);
            }
        }

        return result;
    }

    public void multiply(double n) {
        // Scalar multiplication
        map(n, 0, "linear");
    }

    public void add(double n) {
        // Scalar addition
        map(1, n, "linear");
    }

    public static Matrix subtract(Matrix a, Matrix b) {
         // Element-wise subtraction
         if (a.rows != b.rows || a.cols != b.cols) {
            throw new RuntimeException("Dimensions of matrices aren't equal.");
        }

        Matrix result = new Matrix(a.rows, a.cols);
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                result.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }

        return result;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        // Matrix product
        if (a.cols != b.rows) {
            throw new RuntimeException("Matrix 'a' columns and Matrix 'b' rows aren't equal.");
        }

        Matrix result = new Matrix(a.rows, b.cols);

        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                double sum = 0;
                for (int k = 0; k < a.cols; k++) {
                    sum += a.data[i][k] * b.data[k][j];
                }

                result.data[i][j] = sum;
            }
        }

        return result;
    }

    public void multiply(Matrix m) {
        // Element-wise multiplication
        if (rows != m.rows || cols != m.cols) {
            throw new RuntimeException("Dimensions of matrices aren't equal.");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] *= m.data[i][j];
            }
        }
    }

    public void add(Matrix m) {
        // Element-wise addition
        if (rows != m.rows || cols != m.cols) {
            throw new RuntimeException("Dimensions of matrices aren't equal.");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] += m.data[i][j];
            }
        }
    }

    public void print() {
        // Printing matrix
        for (double[] row : data) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println();
    }
}   