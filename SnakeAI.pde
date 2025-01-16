import java.util.Arrays;

// initialize neuroevolution
int population = 500;
NeuroEvolution ne = new NeuroEvolution(9, 4, 4, 3, population, 3, 0.2, 0.1, "tanh");

// initialize variables
Snake[] snakes;
Apple[] apples;
boolean[] alive;
double[] scores;
int speed = 300;

// set up game
void setup() {
  size(750, 750);
  reset();
}

// reset variables
void reset() {
  scores = new double[population];
  alive = new boolean[population];
  Arrays.fill(alive, true);
  
  snakes = new Snake[population];
  apples = new Apple[population];
  
  for (int i = 0; i < population; i++) {
    snakes[i] = new Snake();
    apples[i] = new Apple(snakes[i]);
  }
}

void draw() { 
  for (int x = 0; x < speed; x++) {
    // control speed
    if (mousePressed) {
      speed = mouseX;
      if (speed < 1) {
        speed = 1;
      }
    }
    
    // check end of generation
    boolean end = true;
    for (boolean i : alive) {
      if (i) {
        end = false;
      }
    }
      
    if (end) {
      ne.evolve(scores);
      reset(); 
    }
    
    for (int i = 0; i < population; i++) {
      if (alive[i]) {
        // create input
        double[] input = new double[9];
        
        int[][] dirs = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
        
        for (int j = 0; j < dirs.length; j++) {
          int[] field = {snakes[i].pos[0] + dirs[j][0], snakes[i].pos[1] + dirs[j][1]};
          
          if (snakes[i].inSnake(field) || field[0] < 0 || field[0] > 14 || field[1] < 0 || field[1] > 14) {
            input[j] = 0;
          } else {
            input[j] = 1;
          }
        }
        
        input[4] = snakes[i].pos[0];
        input[5] = snakes[i].pos[1];
        input[6] = apples[i].pos[0];
        input[7] = apples[i].pos[1];
        input[8] = snakes[i].dirNum;
        
        /*int[][] dirs = {{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};
        
        for (int j = 0; j < 8; j++) {
          int distance = 14;
          int[] pos = new int[2];
          pos[0] = snakes[i].pos[0];
          pos[1] = snakes[i].pos[1];
          
          for (int k = 1; k < 14 && distance == 14; k++) {
            if (snakes[i].inSnake(pos)) {
              distance = k;
            }
            
            pos[0] += dirs[j][0];
            pos[1] += dirs[j][1];
          }
          
          input[j + 5] = distance / 14;
        }*/
        
        // save guess
        double[] output = ne.nns[i].guess(input);
        
        // get best direction and turn
        int best = 0;
        for (int j = 1; j < 4; j++) {
          if (output[j] > output[best]) {
            best = j;
          }
        }
        
        switch(best) {
          case 0:
            snakes[i].turn('w');
            break;
          case 1:
            snakes[i].turn('a');
            break;
          case 2:
            snakes[i].turn('s');
            break;
          case 3:
            snakes[i].turn('d');
            break;
        }
        
        // move and check death
        if (snakes[i].move(apples[i])) {
          alive[i] = false;
          scores[i] = snakes[i].score + 1;
        }
      }
    }
  }
  
  // render
  background(0);
  textAlign(CENTER, CENTER);
  textSize(50);
  
  for (int i = 0; i < population; i++) {
    if (alive[i]) {
      snakes[i].draw();
      apples[i].draw();
      fill(255);
      text(snakes[i].score, 375, 50);
      break;
    }
  }
  
  fill(255);
  text("Generation " + ne.generation, 375, 700);
}
