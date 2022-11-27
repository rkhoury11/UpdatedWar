package DrawMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import DrawMap.TileMap.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class PrimaryController {
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button rollButton;
    @FXML 
    private FlowPane mapBox;
    @FXML 
    private ImageView viewer;
    @FXML 
    private TextArea textBox;
    @FXML 
    private Label playerTurn;
    @FXML
    private Label roundCount;
    @FXML 
    private Label playerCount;
    @FXML 
    private Label attackRollLabel;
    @FXML
    private Label defendRollLabel;
    @FXML
    ChoiceBox<Integer> playerCountChoice = new ChoiceBox<Integer>();
    
    private HashMap<Integer, List<Tile>> playerTiles = new HashMap<Integer, List<Tile>>(); //store list of owned tiles per player
    private HashMap<Integer, Color> playerColors = new HashMap<Integer, Color>(); //store list of colors for each players
    private HashMap<Integer, Color> playerSecondaryColors = new HashMap<Integer, Color>(); //store list of secondary colors for each players
    
    
    boolean gameStarted; //track game state
    private int round; //track round count
    private int turn; //track turn value
    private int pc; //track player count selection
    private int attackRollValue; //track value of dice roll
    private int defendRollValue; //track value of dice roll
    private TileMap map; //tile map
    
    //create new maps with size and color
    public TileMap createNewMap(int size, Color col) {
        TileMap map = new TileMap(size, col);
        return map;
    }
    
    //reset game functionality
    public void resetGame() {
        gameStarted = false;
        round = 0;
        turn = 0;
        
        startButton.setVisible(true);
        playerCountChoice.getItems().clear();
        playerCountChoice.getItems().addAll(1,2,3,4);
        playerCountChoice.hide();
        mapBox.getChildren().clear();
        
        //clear all player owned tile lists
        for(List<Tile> l : playerTiles.values()) {
            l.clear();
        }
        
        playerTurn.setText(String.valueOf(turn));
        roundCount.setText(String.valueOf(round));
        playerCount.setText(null);
        textBox.setText("Select amount of players, then press 'start game' to get started.");
        //rollButton.setVisible(false);
    }
    
    @FXML 
    private void resetButtonPress() throws Exception {
        resetGame();
    }
    
    @FXML
    public void initialize() throws Exception {
        
        //each player gets a list for their owned tiles
        playerTiles.put(1, new ArrayList<Tile>());
        playerTiles.put(2, new ArrayList<Tile>());
        playerTiles.put(3, new ArrayList<Tile>());
        playerTiles.put(4, new ArrayList<Tile>());
        
        //each player gets a primary color for owned tiles
        playerColors.put(1, Color.BLUE);
        playerColors.put(2, Color.GREEN);
        playerColors.put(3, Color.PURPLE);
        playerColors.put(4, Color.ORANGE);
        
        //each player gets a secondary color for contested tiles
        playerSecondaryColors.put(1, Color.LIGHTBLUE);
        playerSecondaryColors.put(2, Color.LIGHTGREEN);
        playerSecondaryColors.put(3, Color.LIGHTPINK);
        playerSecondaryColors.put(4, Color.LIGHTYELLOW);
        resetGame();
    }
    
    @FXML
    private void startButtonPress() throws Exception {
        if (playerCountChoice.getValue() == null) {
            textBox.setText("Select player count and then press 'Start Game'");
        } else {
            
            gameStarted = true;
            turn = 1;
            round = 1;
            pc = (int) playerCountChoice.getValue();
            
            map = new TileMap(10, Color.BEIGE);
            map.playerColor = playerColors.get(turn);
            map.secondaryColor = playerSecondaryColors.get(turn);
            map.playerNumber = turn;
            
            mapBox.getChildren().add(map);
            
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            playerCount.setText(String.valueOf(pc));
            
            startButton.setVisible(false);
            
            textBox.setText("Game has started. It is now player " + String.valueOf(turn) + "'s turn");
            distributeTerritories();
        }
    }
    
    
    
    @FXML
    private void finishTurnButtonPress() throws IOException {
        
        if(gameStarted == true) {
            
            //if all players have completed their turn, go to next round
            if (turn == pc) {
                textBox.clear();
                round++;
                turn = 1;
            } else {
                turn++;       
            }
            
            //reset values for next turn
            map.playerNumber = turn;
            map.getOwnedTiles().clear();//clear list for next player to add tiles to
            attackRollValue = 0;
            defendRollValue = 0;
            //attackRollLabel.setText(null);
            //defendRollLabel.setText(null);
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            textBox.appendText("It is now player " + String.valueOf(turn) + "'s turn");
            map.playerColor = playerColors.get(turn);
            map.secondaryColor = playerSecondaryColors.get(turn);
        }
    }
    
    //roll die, set value and display
    @FXML 
    private void rollDiceButtonPress() throws IOException{
        if(map.selectedTile != null && map.targetTile != null) {
            
            attackRollValue = Dice.roll();
            defendRollValue = Dice.roll();
            
            attackRollLabel.setText(String.valueOf(attackRollValue));
            defendRollLabel.setText(String.valueOf(defendRollValue));

            if (attackRollValue > defendRollValue) {
                map.targetTile.setFill(playerColors.get(turn));
                map.targetTile.owned = turn;
                map.targetTile.setStroke(Color.BLACK);
                map.selectedTile.setStroke(Color.BLACK);
            }
        }
    }
    
    @FXML 
    private void distributeTerritories() throws IOException {
        
        List<Integer> nums = new ArrayList<Integer>();
        int curPlayer = 1;
        int y = 0;
        for(int i = 0; i < 10; i++) {
            nums.addAll(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
            
            while (!(nums.isEmpty())) {
                if(curPlayer > pc) {
                    curPlayer = 1;
                }
                Collections.shuffle(nums);
                
                playerTiles.get(curPlayer).add(map.tileList.get(i).get(y));
                
                map.tileList.get(i).get(nums.get(y)).setFill(playerColors.get(curPlayer));
                map.tileList.get(i).get(nums.remove(y)).owned = curPlayer;
                

                curPlayer++;
                
            }
            
        }
    }
}
    

