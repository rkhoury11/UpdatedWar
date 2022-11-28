
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
// import javafx.scene.text.Font;

public class PrimaryController {
    
    
    @FXML 
    private HBox mainBox;
    @FXML 
    private TextArea textBox;
    @FXML
    private Button startButton, rollButton, plusTroopButton, addTroopsButton, fightButton;
    @FXML 
    private Label playerTurn, roundCount, playerCount, attackRollLabel, defendRollLabel;

    @FXML
    ChoiceBox<Integer> playerCountChoice = new ChoiceBox<Integer>();


    private HashMap<Integer, Color> playerColors = new HashMap<Integer, Color>(); //store list of colors for each players
    private HashMap<Integer, Integer> playerTerritoryCount = new HashMap<Integer, Integer>(); //store list of colors for each players
    boolean gameStarted; //track game state
    boolean addingTroops;
    boolean inFight;
    boolean admin;
    private int troopsAvailable;
    private int round; //track round count
    private int turn; //track turn value
    private int pc; //track player count selection
    private int attackRollValue; //track value of dice roll
    private int defendRollValue; //track value of dice roll
    private TerritoryMap map = new TerritoryMap(); //map of images for game
    
    @FXML
    public void initialize() throws Exception {
        //each player gets a primary color for owned tiles
        playerColors.put(1, Color.BLUE);
        playerColors.put(2, Color.GREEN);
        playerColors.put(3, Color.RED);
        playerColors.put(4, Color.YELLOW);
        playerColors.put(5, Color.VIOLET);
        playerColors.put(6, Color.ORANGE);

        playerTerritoryCount.put(1 ,0); 
        playerTerritoryCount.put(2 ,0);
        playerTerritoryCount.put(3 ,0);
        playerTerritoryCount.put(4 ,0);
        playerTerritoryCount.put(5 ,0);
        playerTerritoryCount.put(6 ,0);

        mainBox.getChildren().add(map);
        
        resetGame();
    }

    //reset game functionality
    public void resetGame() {
        

        //reset UI nodes
        startButton.setVisible(true);
        playerCountChoice.getItems().clear();
        playerCountChoice.getItems().addAll(3,4,5,6);
        playerCountChoice.hide();
        rollButton.setVisible(false);
        fightButton.setVisible(false);
        TerritoryMap.selectedTerritory = null;
        TerritoryMap.targetTerritory = null;
        map.setVisible(false);
        plusTroopButton.setVisible(false);
        addTroopsButton.setVisible(true);

        for(Territory t : TerritoryMap.territoryList) {
            t.setTroop(1);
        }

        playerTurn.setText(String.valueOf(turn));
        roundCount.setText(String.valueOf(round));
        playerCount.setText(null);
        textBox.setText("Welcome to WAR! Select amount of players, then press 'start game' to get started.");

        //reset game started, round, and turn values
        gameStarted = false;
        round = 0;
        turn = 0;
    }
    
    //reset button functionality
    @FXML 
    private void resetButtonPress() throws Exception {
        resetGame();
    }
    
    //start button functionality
    @FXML
    private void startButtonPress() throws Exception { 
        //must select a player count to start
        if (playerCountChoice.getValue() == null) {
            textBox.setText("Select player count and then press 'Start Game'");
        } else {
            //distribute territories and initialize round and turn data
            gameStarted = true;
            turn = 1;
            round = 1;
            pc = (int) playerCountChoice.getValue();

            //create new territory map
            distributeTerritories();   

            
            map.setVisible(true);
            TerritoryMap.currentTurn = turn;

            //set UI node values
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            playerCount.setText(String.valueOf(pc));
            startButton.setVisible(false);
            textBox.setText("Game has started. It is now player " + String.valueOf(turn) + "'s turn");
        }
    }
    
    //finish turn button functionality
    @FXML
    private void finishTurnButtonPress() throws IOException {
        if(gameStarted == true && addingTroops == false) {
            
            textBox.setText(null);

            //if all players have completed their turn, go to next round
            if (turn == pc) {
                textBox.clear();
                round++;
                turn = 1;
            } else {
                turn++;       
            }
            //reset dice values
            attackRollValue = 0;
            defendRollValue = 0;
            attackRollLabel.setText(null);
            defendRollLabel.setText(null);

            //reset turn and round values
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            textBox.appendText("It is now player " + String.valueOf(turn) + "'s turn");

            //reset selected and target territory values
            TerritoryMap.selectedTerritory = null;
            TerritoryMap.targetTerritory = null;
            TerritoryMap.currentTurn = turn;
            
            

            addTroopsButton.setVisible(true);
            plusTroopButton.setVisible(false);
            addingTroops = false;
            TerritoryMap.inAddingTroopsMode = false;
            rollButton.setVisible(false);
        }
    }
    
    
    
    @FXML 
    private void distributeTerritories() throws IOException {
        //fill list to use as indexes of territoryList
        List<Integer> distribution = new ArrayList<Integer>();
        for(int i = 0; i < 42; i++) {
            distribution.add(i);
        }
        
        //keep track of player
        int curPlayer = 1; 
    
        //distrubute all territories in list
        while (!(distribution.isEmpty())) {
            //rotate until pc is met
            if(curPlayer > pc) {
                curPlayer = 1;
            }

            //random shuffle index positions
            Collections.shuffle(distribution);

            //set territory color and ownership according to curPlayer
            TerritoryMap.territoryList.get(distribution.get(0)).setColor(playerColors.get(curPlayer));
            TerritoryMap.territoryList.get(distribution.remove(0)).owned = curPlayer;
            
            playerTerritoryCount.put(curPlayer, playerTerritoryCount.get(curPlayer) + 1);

            curPlayer++;
        }
    }

    @FXML 
    private void adminButtonPress() throws IOException {
        if(admin == false) {
            for(Territory t : TerritoryMap.territoryList) {
                t.setName(null);
            }
        } else {
            for(Territory t : TerritoryMap.territoryList) {
                t.setName(t.sName);
            }
        }
    }

    @FXML 
    private void addTroopsButtonPress() throws IOException {
        addingTroops = true;
        TerritoryMap.inAddingTroopsMode = true;
        troopsAvailable = (playerTerritoryCount.get(turn) / 2);
        plusTroopButton.setVisible(true);
        addTroopsButton.setVisible(false);
        textBox.setText("You have " + troopsAvailable + " troops available to assign to territories");

        for(Territory t : TerritoryMap.territoryList) {
            if(t.owned != turn) {
                t.setBrightness(-0.80);
            }
        }
    }
    
    @FXML void plusTroopButtonPress() {
        troopsAvailable--;
        if(troopsAvailable == 0) {
            TerritoryMap.selectedTerritory.addTroop();
            textBox.setText("You have added all available troops. Select one of your territories and an enemy to, then press the 'Fight' button to ATTACK!");
            for(Territory t : TerritoryMap.territoryList) {
                t.setBrightness(0);
            }
            TerritoryMap.targetTerritory = null;
            TerritoryMap.selectedTerritory = null;
            TerritoryMap.inAddingTroopsMode = false;
            addingTroops = false;
            plusTroopButton.setVisible(false);
            fightButton.setVisible(true);
        } else{
            TerritoryMap.selectedTerritory.addTroop();
            textBox.setText("You have " + troopsAvailable + " troops available to assign to territories");
        }
    //     troopsAvailable--;
    //     if(troopsAvailable > 0) {
    //         textBox.setText("You have " + troopsAvailable + " troops available to assign to territories");
    //         TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount() + 1);
    //     } else{
    //         textBox.setText("You have added all available troops. Select one of your territories and an enemy to, then press the 'Fight' button to ATTACK!");
    //         for(Territory t : TerritoryMap.territoryList) {
    //             t.setBrightness(0);
    //         }
    //         TerritoryMap.targetTerritory = null;
    //         TerritoryMap.selectedTerritory = null;
    //         TerritoryMap.inAddingTroopsMode = false;
    //         addingTroops = false;
    //         plusTroopButton.setVisible(false);
    //         fightButton.setVisible(true);
    //     }
     }

    @FXML
    private void fightButtonPress() throws Exception {
        if(TerritoryMap.selectedTerritory != null && TerritoryMap.targetTerritory != null){
            if (TerritoryMap.selectedTerritory.getTroopCount() > 1 ) {
                rollButton.setVisible(true);
                inFight = true;
                TerritoryMap.inFightMode = true;
                textBox.setText("You're in battle mode. Press 'roll' to conquer your target territory");
            } else {
                textBox.setText("Your territory must have a score greater than 1 to be able to fight!");
                inFight = false;
                TerritoryMap.inFightMode = false;
            }
        }
    }
 
    @FXML 
    private void rollButtonPress() throws IOException {
        //roll Dice
        attackRollValue = Dice.roll();
        defendRollValue = Dice.roll();
        
        //display values
        attackRollLabel.setText(String.valueOf(attackRollValue));
        defendRollLabel.setText(String.valueOf(defendRollValue));

        rollButton.setVisible(false);
        inFight = false;

        if(attackRollValue > defendRollValue) {
            TerritoryMap.targetTerritory.owned = turn;
            TerritoryMap.targetTerritory.setColor(playerColors.get(turn));
            // TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount() + 1);
            playerTerritoryCount.put(turn, playerTerritoryCount.get(turn) + 1);

        } else {
            TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount() - 1);
        }
    }
}