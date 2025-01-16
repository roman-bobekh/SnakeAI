import java.util.Arrays;

public class NeuroEvolution {
    NeuralNetwork[] nns;
    int population;
    int generation = 1;
    int batchSize;
    double[][] batch;
    int batchTime = 0; 

    public NeuroEvolution(int inputs, int hiddens, int outputs, int layers, int population_, int batchSize_, double learningRate, double frequency, String activation) {
        population = population_;
        batchSize = batchSize_;
        batch = new double[batchSize][population];

        nns = new NeuralNetwork[population];
        for (int i = 0; i < population; i++) {
            nns[i] = new NeuralNetwork(inputs, hiddens, outputs, layers);
            nns[i].learningRate = learningRate;
            nns[i].frequency = frequency;
            nns[i].activation = activation;
        }
    }

    public void evolve(double[] scores) {
        batch[batchTime] = scores;
        batchTime++;
        
        if (batchTime % batchSize == 0) {   
            batchTime = 0;
            
            // calculate average for each nn
            double[] average = new double[population];
            for (int i = 0; i < population; i++) {
                double sum = 0;
                for (int j = 0; j < batchSize; j++) {
                  sum += batch[j][i]; 
                }
                average[i] = sum / batchSize;
            }
            
            // pass the best with a high probability into the next generation and mutate them
            NeuralNetwork[] new_nns = new NeuralNetwork[population];
            double sum = Arrays.stream(average).sum();
            double subtotal;
    
            for (int i = 0; i < population - 1; i++) {
                subtotal = Math.random() * sum;
    
                for (int j = 0; j < population; j++) {
                    subtotal -= average[j];
                    if (subtotal < 0) {
                        new_nns[i] = nns[j].copy();
                        new_nns[i].mutate();
                        break;
                    }
                }
            }
    
            // copy best nn
            int highest = 0;
            for (int i = 0; i < population; i++) {
                if (average[i] > average[highest]) {
                    highest = i;
                }
            }
    
            new_nns[population - 1] = nns[highest].copy();
            
            System.out.println(average[highest]);
    
            nns = new_nns;
            generation++;
        }  
    }
}
