import java.util.ArrayList;
import java.util.Arrays;

class Snake {
  // initialize variables
  int score = 0;
  int[] dir = {1, 0};
  int dirNum = 3;
  int[] pos = {0, 0};
  int noFood = 0;
  ArrayList<int[]> fields = new ArrayList();
  
  Snake() {
    addToFields();
  }
    
  // add position to fields
  void addToFields() {
    int[] field = new int[2];
    field[0] = pos[0];
    field[1] = pos[1];
    
    fields.add(field);
  }
  
  // turn in desired direction
  void turn(char s) {
    int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    int[] newDir = {0, 0};
    int newDirNum = 0;
    
    switch(s) {
      case 'w':
        newDir = dirs[3];
        newDirNum = 0;
        break;
      case 'a':
        newDir = dirs[2];
        newDirNum = 1;
        break;
      case 's':
        newDir = dirs[1];
        newDirNum = 2;
        break;
      case 'd':
        newDir = dirs[0];
        newDirNum = 3;
        break;
    }
    
    if (dir[0] + newDir[0] != 0) {
      dir = newDir; 
      dirNum = newDirNum;
    }
  }
  
  // check if x is in snake
  boolean inSnake(int[] x) {
    for (int[] field : fields) {
      if (Arrays.equals(field, x)) {
         return true;
      }
    }
    
    return false;
  }
    
  boolean move(Apple apple) {
    // change position
    pos[0] += dir[0];
    pos[1] += dir[1];

    // check collision with tail
    for (int[] field : fields) {
      if (Arrays.equals(pos, field)) {
        return true;
      }
    }
    
    addToFields();
    
    // check if apple is being eaten
    if (Arrays.equals(pos, apple.pos)) {
      score += 1;
      apple.generate(this);
      noFood = 0;
    } else {
      fields.remove(0);
      noFood += 1;
    }
    
    // check if no food has been eaten since 100 frames
    if (noFood > 300) {
      return true;
    }
    
    // check collision with border
    if (pos[0] > 14 || pos[0] < 0 || pos[1] > 14 || pos[1] < 0) {
      return true;
    }
    
    // die if too high score
    if (score > 500) {
      return true; 
    }
    
    return false;
  }
  
  // draw snake
  void draw() {
    noStroke();
    fill(0, 255, 0);
    
    for (int[] field : fields) {
       rect(field[0] * 50 + 2, field[1] * 50 + 2, 46, 46);
    }
  }
}
