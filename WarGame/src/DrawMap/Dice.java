package DrawMap;

import java.util.Random;

public class Dice {
    
    //roll 6-sided die and return value
    public static int roll() {
        int roll = 1 + new Random().nextInt(6);
        return roll;
    } 
}