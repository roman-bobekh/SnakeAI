// import libraries
import java.util.Random;
import java.util.Arrays;

class Apple {
  int[] pos = new int[2];
  
  Apple(Snake snake) {
    generate(snake);
  }
  
  // generate apple in a free field
  void generate(Snake snake) {
    Random r = new Random();
    
    pos[0] = r.nextInt(15);
    pos[1] = r.nextInt(15);
    
    for (int i = 0; i < 300 && snake.inSnake(pos); i++) {
      pos[0] = r.nextInt(15);
      pos[1] = r.nextInt(15);
    }
  }
  
  // draw apple
  void draw() {
    noStroke();
    fill(255, 0, 0);
    rect(pos[0] * 50 + 2, pos[1] * 50 + 2, 46, 46); 
  }
}
