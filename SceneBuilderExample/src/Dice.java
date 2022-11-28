

import java.util.Random;

public class Dice {
    
    //roll 6-sided die and return value
    public static int roll() {
        //random between 1-6
        int roll = 1 + new Random().nextInt(6);
        return roll;
    } 
}
