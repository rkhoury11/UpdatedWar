
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PrimaryController {

    @FXML
    private GridPane attackLabelPane, defendLabelPane;
    @FXML
    private HBox mainBox, diceRollBox;
    @FXML
    private TextArea textBox;
    @FXML
    private Button startButton, rollButton, plusTroopButton, addTroopsButton, fightButton, finishTurnButton;
    @FXML
    private Label playerTurn, roundCount, playerCount, attackRollLabel, defendRollLabel;
    @FXML
    private Label attack3, attack1, attack2, defend3, defend1, defend2;
    @FXML
    ChoiceBox<Integer> playerCountChoice = new ChoiceBox<Integer>();

    private List<Integer> attackValues = new ArrayList<Integer>();
    private List<Integer> defenseValues = new ArrayList<Integer>();
    private HashMap<Integer, Color> playerColors = new HashMap<Integer, Color>(); // store list of colors for each
                                                                                  // players
    private HashMap<Integer, Integer> playerTerritoryCount = new HashMap<Integer, Integer>(); // store list of colors
                                                                                              // for each players
    boolean gameStarted; // track game state
    boolean addingTroops; // GAME_STATE
    boolean inFight; // GAME_STATE
    boolean admin; // track if in admin menu
    boolean gameWon; // tracking if game has been won
    private int troopsAvailable;
    private int round; // track round count
    private int turn; // track turn value
    private int pc; // track player count selection
    private int attack1value; // track value of dice roll
    private int attack2value;
    private int attack3value;
    private int defend1value; // track value of dice roll
    private int defend2value;
    private int defend3value;
    private TerritoryMap map = new TerritoryMap(); // map of images for game

    @FXML
    public void initialize() throws Exception {

        // each player gets a primary color for owned tiles
        playerColors.put(1, Color.BLUE);
        playerColors.put(2, Color.GREEN);
        playerColors.put(3, Color.RED);
        playerColors.put(4, Color.YELLOW);
        playerColors.put(5, Color.VIOLET);
        playerColors.put(6, Color.ORANGE);

        // init player count map
        playerTerritoryCount.put(1, 0);
        playerTerritoryCount.put(2, 0);
        playerTerritoryCount.put(3, 0);
        playerTerritoryCount.put(4, 0);
        playerTerritoryCount.put(5, 0);
        playerTerritoryCount.put(6, 0);

        // put map on board
        mainBox.getChildren().add(map);

        resetGame();
    }

    // reset game functionality
    public void resetGame() {

        // reset UI nodes
        startButton.setVisible(true);
        playerCountChoice.getItems().clear();
        playerCountChoice.getItems().addAll(3, 4, 5, 6);
        playerCountChoice.hide();
        rollButton.setVisible(false);
        fightButton.setVisible(false);
        TerritoryMap.selectedTerritory = null;
        TerritoryMap.targetTerritory = null;
        map.setVisible(false);
        plusTroopButton.setVisible(false);
        addTroopsButton.setVisible(true);
        addTroopsButton.setVisible(false);
        finishTurnButton.setVisible(false);
        playerTurn.setText(String.valueOf(turn));
        roundCount.setText(String.valueOf(round));
        playerCount.setText(null);
        textBox.setText("Welcome to WAR! Select amount of players, then press 'start game' to get started.");
        for (Node children : attackLabelPane.getChildren()) {
            children.setVisible(false);
        }
        for (Node children : defendLabelPane.getChildren()) {
            children.setVisible(false);
        }

        for (Territory t : TerritoryMap.territoryList) {
            t.setTroop(1);
        }

        // reset game started, round, and turn values
        gameStarted = false;
        round = 0;
        turn = 0;
        gameWon = false;
    }

    // reset button functionality
    @FXML
    private void resetButtonPress() throws Exception {
        resetGame();
    }

    // start button functionality
    @FXML
    private void startButtonPress() throws Exception {
        // must select a player count to start
        if (playerCountChoice.getValue() == null) {
            textBox.setText("Select player count and then press 'Start Game'");
        } else {
            // distribute territories and initialize round and turn data
            gameStarted = true;
            gameWon = false;
            turn = 1;
            round = 1;
            pc = (int) playerCountChoice.getValue();

            // create new territory map
            distributeTerritories();
            map.setVisible(true);
            TerritoryMap.currentTurn = turn;

            // set UI node values
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            playerCount.setText(String.valueOf(pc));
            startButton.setVisible(false);
            textBox.setText("Game has started. It is now player " + String.valueOf(turn) + "'s turn");

            addTroopsButton.setVisible(true);
        }
    }

    // finish turn button functionality
    @FXML
    private void finishTurnButtonPress() throws IOException {
        if (gameStarted == true && addingTroops == false && gameWon == false) {

            // if all players have completed their turn, go to next round
            if (turn == pc) {
                textBox.clear();
                round++;
                turn = 1;
            } else {
                turn++;
            }

            // reset turn and round values
            textBox.setText(null);
            playerTurn.setText(String.valueOf(turn));
            roundCount.setText(String.valueOf(round));
            textBox.appendText("It is now player " + String.valueOf(turn) + "'s turn");

            // reset selected and target territory values
            TerritoryMap.selectedTerritory = null;
            TerritoryMap.targetTerritory = null;
            TerritoryMap.currentTurn = turn;

            // reset UI nodes
            addTroopsButton.setVisible(true);
            plusTroopButton.setVisible(false);
            addingTroops = false;
            TerritoryMap.inAddingTroopsMode = false;
            rollButton.setVisible(false);
            finishTurnButton.setVisible(false);
            fightButton.setVisible(false);
            for (Node children : attackLabelPane.getChildren()) {
                children.setVisible(false);
            }
            for (Node children : defendLabelPane.getChildren()) {
                children.setVisible(false);
            }
        }
    }

    @FXML
    private void distributeTerritories() throws IOException {

        // fill list to use as indexes of territoryList
        List<Integer> distribution = new ArrayList<Integer>();
        for (int i = 0; i < 42; i++) {
            distribution.add(i);
        }

        // keep track of player
        int curPlayer = 1;

        // distrubute all territories in list
        while (!(distribution.isEmpty())) {
            // rotate until pc is met
            if (curPlayer > pc) {
                curPlayer = 1;
            }

            // random shuffle index positions
            Collections.shuffle(distribution);

            // set territory color and ownership according to curPlayer
            TerritoryMap.territoryList.get(distribution.get(0)).setColor(playerColors.get(curPlayer));
            TerritoryMap.territoryList.get(distribution.remove(0)).owned = curPlayer;
            playerTerritoryCount.put(curPlayer, playerTerritoryCount.get(curPlayer) + 1);
            curPlayer++;
        }
    }

    @FXML
    private void adminButtonPress() throws IOException {
        if (admin == false) {
            for (Territory t : TerritoryMap.territoryList) {
                t.setName(null);
            }
        } else {
            for (Territory t : TerritoryMap.territoryList) {
                t.setName(t.sName);
            }
        }
    }

    @FXML
    private void addTroopsButtonPress() throws IOException {
        if (gameWon == false) {
            addingTroops = true;
            TerritoryMap.inAddingTroopsMode = true;
            troopsAvailable = (playerTerritoryCount.get(turn) / 2);
            plusTroopButton.setVisible(true);
            addTroopsButton.setVisible(false);
            textBox.setText("You have " + troopsAvailable + " troops available to assign to territories");

            for (Territory t : TerritoryMap.territoryList) {
                if (t.owned != turn) {
                    t.setBrightness(-0.80);
                }
            }
        }

    }

    @FXML
    void plusTroopButtonPress() {
        troopsAvailable--;
        if (troopsAvailable == 0) {
            TerritoryMap.selectedTerritory.addTroop();
            if (round == 1) {
                textBox.setText("You have added all available troops. Press 'Finish Turn' to finish your turn!");
            }

            // update UI nodes
            for (Territory t : TerritoryMap.territoryList) {
                t.setBrightness(0);
            }
            TerritoryMap.targetTerritory = null;
            TerritoryMap.selectedTerritory = null;
            TerritoryMap.inAddingTroopsMode = false;
            addingTroops = false;
            plusTroopButton.setVisible(false);
            finishTurnButton.setVisible(true);
            if (round != 1) {
                fightButton.setVisible(true);
                textBox.setText(
                        "You have added all available troops. Select one of your territories and an enemy to, then press the 'Fight' button to ATTACK!");
            }
        } else {
            TerritoryMap.selectedTerritory.addTroop();
            textBox.setText("You have " + troopsAvailable + " troops available to assign to territories");
        }
    }

    @FXML
    private void fightButtonPress() throws Exception {
        if (TerritoryMap.selectedTerritory != null && TerritoryMap.targetTerritory != null
                && TerritoryMap.targetTerritory.owned != turn) {
            // set number of dice based on troop count of territory

            if (TerritoryMap.selectedTerritory.getTroopCount() > 1) {

                // attack1.setVisible(true);
                // if (TerritoryMap.selectedTerritory.getTroopCount() == 3 ) {
                // attack2.setVisible(true);
                // } else if (TerritoryMap.selectedTerritory.getTroopCount() >= 4) {
                // attack2.setVisible(true);
                // attack3.setVisible(true);
                // }
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
        // roll Dice
        // display attacking values
        attack1value = Dice.roll();
        attack1.setText(String.valueOf(attack1value));
        attack2value = Dice.roll();
        attack2.setText(String.valueOf(attack2value));
        attack3value = Dice.roll();
        attack3.setText(String.valueOf(attack3value));

        // display defensive values
        defend1value = Dice.roll();
        defend1.setText(String.valueOf(defend1value));
        defend2value = Dice.roll();
        defend2.setText(String.valueOf(defend2value));
        defend3value = Dice.roll();
        defend3.setText(String.valueOf(defend3value));

        ///////////////////
        attackValues.clear();
        defenseValues.clear();
        ///////////////////

        rollButton.setVisible(false);
        for (Node children : attackLabelPane.getChildren()) {
            children.setVisible(true);
        }
        for (Node children : defendLabelPane.getChildren()) {
            children.setVisible(true);
        }

        if (TerritoryMap.selectedTerritory.getTroopCount() >= 4) {
            attackValues.addAll(Arrays.asList(
                    attack1value, attack2value, attack3value));
        } else if (TerritoryMap.selectedTerritory.getTroopCount() == 3) {
            attack3.setVisible(false);
            attackValues.addAll(Arrays.asList(
                    attack1value, attack2value));
        } else if (TerritoryMap.selectedTerritory.getTroopCount() == 2) {
            attack3.setVisible(false);
            attack2.setVisible(false);
            attackValues.addAll(Arrays.asList(
                    attack1value));
        }

        if (TerritoryMap.targetTerritory.getTroopCount() >= 3) {
            defenseValues.addAll(Arrays.asList(
                    defend1value, defend2value, defend3value));
        } else if (TerritoryMap.targetTerritory.getTroopCount() == 2) {
            defend3.setVisible(false);
            defenseValues.addAll(Arrays.asList(
                    defend1value, defend2value));
        } else if (TerritoryMap.targetTerritory.getTroopCount() == 1) {
            defend3.setVisible(false);
            defend2.setVisible(false);
            defenseValues.addAll(Arrays.asList(
                    defend1value));
        }

        // sort both lists from biggest to smallest values
        Collections.sort(attackValues, Collections.reverseOrder());
        Collections.sort(defenseValues, Collections.reverseOrder());

        if (attackValues.size() > defenseValues.size()) {
            for (int i = 0; i < defenseValues.size(); i++) {
                if (attackValues.get(i) > defenseValues.get(i)) {
                    TerritoryMap.targetTerritory.setTroop(TerritoryMap.targetTerritory.getTroopCount() - 1);
                } else {
                    TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount() - 1);
                }

                if (TerritoryMap.targetTerritory.getTroopCount() < 1) {
                    TerritoryMap.targetTerritory.owned = turn;
                    TerritoryMap.targetTerritory.setColor(playerColors.get(turn));
                    TerritoryMap.targetTerritory.setTroop(2);
                    playerTerritoryCount.put(turn, playerTerritoryCount.get(turn) + 1);
                    if (playerTerritoryCount.get(turn) >= 24){
                        gameWon = true;
                        textBox.setText("Player " + turn + " has won! Exit the window, or press 'reset game' to start a new game!");
                        break;
                    }
                    TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount() - 1);
                    break;
                }
            }
        } else {
            for (int i = 0; i < attackValues.size(); i++) {
                if (attackValues.get(i) > defenseValues.get(i)) {
                    TerritoryMap.targetTerritory.setTroop(TerritoryMap.targetTerritory.getTroopCount() - 1);
                } else {
                    TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount() - 1);
                }

                if (TerritoryMap.targetTerritory.getTroopCount() < 1) {
                    TerritoryMap.targetTerritory.owned = turn;
                    TerritoryMap.targetTerritory.setColor(playerColors.get(turn));
                    TerritoryMap.targetTerritory.setTroop(2);
                    playerTerritoryCount.put(turn, playerTerritoryCount.get(turn) + 1);
                    if (playerTerritoryCount.get(turn) >= 24){
                        gameWon = true;
                        textBox.setText("Player " + turn + " has won! Exit the window, or press 'reset game' to start a new game!");
                        break;
                    }
                    TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount() - 1);
                    break;
                }
            }
        }

        inFight = false;

        // if(attackRollValue > defendRollValue) {
        // TerritoryMap.targetTerritory.owned = turn;
        // TerritoryMap.targetTerritory.setColor(playerColors.get(turn));
        // TerritoryMap.targetTerritory.setTroop(1);
        // //
        // TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount()
        // + 1);
        // playerTerritoryCount.put(turn, playerTerritoryCount.get(turn) + 1);
        // TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount()
        // - 1);

        // } else {
        // TerritoryMap.selectedTerritory.setTroop(TerritoryMap.selectedTerritory.getTroopCount()
        // - 1);
        // }
    }
}