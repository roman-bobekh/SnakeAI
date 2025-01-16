public class NeuralNetwork {
    int inputNum;
    int hiddenNum;
    int outputNum;
    int layerNum;

    Matrix[] weights;
    Matrix[] bias;

    double frequency = 0.01;
    double learningRate = 0.1;
    String activation = "sigmoid";

    public NeuralNetwork(int inputNum_, int hiddenNum_, int outputNum_, int layerNum_) {
        layerNum = layerNum_;
        if (layerNum < 1) {
            throw new RuntimeException("Layer amount is less than 1.");
        }

        // Set all instance fields
        inputNum = inputNum_;
        hiddenNum = hiddenNum_;
        outputNum = outputNum_;

        // Initialize and randomize all weights
        weights = new Matrix[layerNum];

        for (int i = 0; i < layerNum; i++) {
            int start;
            int end;
            if (i == 0) {
                start = inputNum;
            } else {
                start = hiddenNum;
            }
            if (i == layerNum - 1) {
                end = outputNum;
            } else {
                end = hiddenNum;
            }

            weights[i] = new Matrix(end, start);
            weights[i].randomize();
        }

        // Add the bias
        bias = new Matrix[layerNum];
        for (int i = 0; i < layerNum; i++) {
            if (i == layerNum - 1) {
                bias[i] = new Matrix(outputNum, 1);
            } else {
                bias[i] = new Matrix(hiddenNum, 1);
            }
            bias[i].randomize();
        }
    }

    public double[] guess(double[] input_arr) {
        // Feed forward the input to get an output
        Matrix[] layers = new Matrix[layerNum + 1];

        layers[0] = Matrix.fromArray(input_arr);

        // Process input into output
        for (int i = 1; i < layerNum + 1; i++) {
            layers[i] = Matrix.multiply(weights[i - 1], layers[i - 1]);
            layers[i].add(bias[i - 1]);
            layers[i].map(0, 0, activation);
        }

        // return output as array
        double[] output_arr = Matrix.toArray(layers[layerNum]);
        return output_arr;
    }

    public void mutate() {
        // change some weights
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].rows; j++) {
                for (int k = 0; k < weights[i].cols; k++) {
                    if (Math.random() < frequency) {
                        weights[i].data[j][k] += Math.random() * learningRate * 2 - learningRate;
                    }
                }
            }
        }

        for (int i = 0; i < bias.length; i++) {
            for (int j = 0; j < bias[i].rows; j++) {
                for (int k = 0; k < bias[i].cols; k++) {
                    if (Math.random() < frequency) {
                        bias[i].data[j][k] += Math.random() * learningRate * 2 - learningRate;
                    }
                }
            }
        }
    }

    public NeuralNetwork copy() {
        // copy neural network

        NeuralNetwork result = new NeuralNetwork(inputNum, hiddenNum, outputNum, layerNum);

        result.frequency = frequency;
        result.learningRate = learningRate;
        result.activation = activation;

        result.weights = new Matrix[layerNum];
        result.bias = new Matrix[layerNum];

        for(int i = 0; i < layerNum; i++) {
            result.weights[i] = weights[i].copy();
            result.bias[i] = bias[i].copy();
        }

        return result;
    }
}