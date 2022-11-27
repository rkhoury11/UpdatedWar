package DrawMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Experiment extends Application {

    Pane pane;
    Scene scene;
    boolean gameStarted;
    private int round;
    private int turn;
    private int numPlayers;
    private int attackRollValue;
    private int defendRollValue;
    private Button finishTurnButton = new Button();
    private Button startButton = new Button();
    private Button resetButton = new Button();
    private Label playerTurn = new Label();
    private Label playerCount = new Label();
    private Label roundCount = new Label();
    private Label attackRollLabel = new Label();
    private Label defendRollLabel = new Label();
    private Button rollButton = new Button();
    private TextArea textBox = new TextArea();
    ChoiceBox<Integer> playerCountChoice = new ChoiceBox<Integer>();

    // List of all territories
    List<Territory> territoryList = new ArrayList<>();
    private HashMap<Integer, List<Territory>> playerTerritories = new HashMap<Integer, List<Territory>>();
    private HashMap<Integer, Color> playerColors = new HashMap<Integer, Color>();


    // South America
    Territory brazil;
    Territory venezuela;
    Territory peru;
    Territory argentina;

    // Noth America
    Territory alaska;
    Territory mackenzie;
    Territory greenland;
    Territory vancouver;
    Territory ottawa;
    Territory labrador;
    Territory california;
    Territory new_york;
    Territory mexico;

    // Europe
    Territory iceland;
    Territory england;
    Territory sweden;
    Territory moscow;
    Territory germany;
    Territory poland;
    Territory france;

    // Africa
    Territory algeria;
    Territory egypt;
    Territory sudan;
    Territory congo;
    Territory south_africa;
    Territory madagascar;
    
    // Asia
    Territory vladivostok;
    Territory siberia;
    Territory chita;
    Territory dudinka;
    Territory omsk;
    Territory aral;
	Territory mongolia;
    Territory china;
    Territory middle_east;
    Territory india;
    Territory vietnam;
    Territory japan;

    // Australia
    Territory sumatra;
    Territory borneo;
    Territory new_guinea;
    Territory australia;

    private void distributeTerritories() {
        List<Integer> distribution = new ArrayList<Integer>();
        int curPlayer = 1;
        for (int i = 1; i <= 42; i++){
            distribution.add(i);
        }
        
        for (int i : distribution) {
            if (curPlayer > numPlayers) {
                curPlayer = 1;
            }

            Collections.shuffle(distribution);
            playerTerritories.get(curPlayer).add(territoryList.get(i));
        }
        

    }

    // reset game function
    private void resetGame() {
        gameStarted = false;
        round = 0;
        turn = 0;

        startButton.setVisible(true);
        playerCountChoice.getItems().clear();
        playerCountChoice.getItems().addAll(3,4,5,6);
        playerCountChoice.hide();
    }

    @Override
    public void start(Stage stage) {
        try {
    
            pane = new Pane();
            BackgroundImage backgroundImage = new BackgroundImage(new Image("file:images/background.png", 1600, 900, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            pane.setBackground(new Background(backgroundImage));

            // Create Territories /////////////////////////////////////////////////////////////////////

            // South America
            brazil = Territory.addToMap("file:images/south_america/brazil.png", 335, 539, 215, 175, Color.ORANGE, "Brazil", 20, 15, -35, "0", 20, 15, -15);
            brazil.enableEvents(false);
            pane.getChildren().add(brazil);
            territoryList.add(brazil);

            venezuela = Territory.addToMap("file:images/south_america/venezuela.png", 311, 497, 148, 76, Color.ORANGE, "Venezuela", 20, -15, -30, "0", 20, -15, -10);
            venezuela.enableEvents(false);
            pane.getChildren().add(venezuela);
            territoryList.add(venezuela);

            peru = Territory.addToMap("file:images/south_america/peru.png", 291, 551, 129, 297, Color.ORANGE, "Peru", 20, 0, -85, "0", 20, 0, -65);
            peru.enableEvents(false);
            pane.getChildren().add(peru);
            territoryList.add(peru);

            argentina = Territory.addToMap("file:images/south_america/argentina.png", 356, 655, 91, 173, Color.ORANGE, "Argentina", 20, -10, -50, "0", 20, -10, -30);
            argentina.enableEvents(false);
            pane.getChildren().add(argentina);
            territoryList.add(argentina);
            
            // Noth America
            alaska = Territory.addToMap("file:images/north_america/alaska.png", 51, 99, 183, 109, Color.DARKSALMON, "Alaska", 20, 20, -10, "0", 20, 20, 10);
            alaska.enableEvents(false);
            pane.getChildren().add(alaska);
            territoryList.add(alaska);
            
            mackenzie = Territory.addToMap("file:images/north_america/mackenzie.png", 221, 131, 275, 115, Color.DARKSALMON, "Mackenzie", 20, 0, 0, "0", 20, 0, 20);
            mackenzie.enableEvents(false);
            pane.getChildren().add(mackenzie);
            territoryList.add(mackenzie);

            greenland = Territory.addToMap("file:images/north_america/greenland.png", 534, 54, 154, 119, Color.DARKSALMON, "Greenland", 20, 0, -25, "0", 20, 0, -5);
            greenland.enableEvents(false);
            pane.getChildren().add(greenland);
            territoryList.add(greenland);

            vancouver = Territory.addToMap("file:images/north_america/winchester.png", 170, 200, 173, 101, Color.DARKSALMON, "Vancouver", 20, 0, 0, "0", 20, 0, 20);
            vancouver.enableEvents(false);
            pane.getChildren().add(vancouver);
            territoryList.add(vancouver);
            
            ottawa = Territory.addToMap("file:images/north_america/ottawa.png", 306, 252, 106, 99, Color.DARKSALMON, "Ottawa", 20, 0, -15, "0", 20, 0, 5);
            ottawa.enableEvents(false);
            pane.getChildren().add(ottawa);
            territoryList.add(ottawa);

            labrador = Territory.addToMap("file:images/north_america/labrador.png", 394, 248, 103, 102, Color.DARKSALMON, "Labrador", 20, 0, -20, "0", 20, 0, 0);
            labrador.enableEvents(false);
            pane.getChildren().add(labrador);
            territoryList.add(labrador);

            california = Territory.addToMap("file:images/north_america/california.png", 149, 288, 170, 117, Color.DARKSALMON, "California", 20, -10, -15, "0", 20, -10, 5);
            california.enableEvents(false);
            pane.getChildren().add(california);
            territoryList.add(california);

            new_york = Territory.addToMap("file:images/north_america/new_york.png", 232, 315, 196, 115, Color.DARKSALMON, "New York", 20, -5, -15, "0", 20, -5, 5);
            new_york.enableEvents(false);
            pane.getChildren().add(new_york);
            territoryList.add(new_york);

            mexico = Territory.addToMap("file:images/north_america/mexico.png", 158, 383, 142, 129, Color.DARKSALMON, "Mexico", 20, -15, -20, "0", 20, -15, 0);
            mexico.enableEvents(false);
            pane.getChildren().add(mexico);
            territoryList.add(mexico);

            // Europe
            iceland = Territory.addToMap("file:images/europe/iceland.png", 618, 168, 103, 51, Color.SEAGREEN, "Iceland", 20, 0, -5, "0", 20, 0, 15);
            iceland.enableEvents(false);
            pane.getChildren().add(iceland);
            territoryList.add(iceland);

            england = Territory.addToMap("file:images/europe/england.png", 625, 247, 83, 71, Color.SEAGREEN, "England", 20, 0, -5, "0", 20, 0, 15);
            england.enableEvents(false);
            pane.getChildren().add(england);
            territoryList.add(england);

            sweden = Territory.addToMap("file:images/europe/sweden.png", 738, 204, 113, 84, Color.SEAGREEN, "Sweden", 20, -5, -30, "0", 20, -5, -10 );
            sweden.enableEvents(false);
            pane.getChildren().add(sweden);
            territoryList.add(sweden);

            moscow = Territory.addToMap("file:images/europe/moscow.png", 810, 215, 115, 156, Color.SEAGREEN, "Moscow", 20, 5, 0, "0", 20, 5, 20);
            moscow.enableEvents(false);
            pane.getChildren().add(moscow);
            territoryList.add(moscow);

            germany = Territory.addToMap("file:images/europe/germany.png", 704, 280, 52, 67, Color.SEAGREEN, "Germany", 20, 0, -15, "0", 20, 0, 5);
            germany.enableEvents(false);
            pane.getChildren().add(germany);
            territoryList.add(germany);

            poland = Territory.addToMap("file:images/europe/poland.png", 765, 310, 78, 98, Color.SEAGREEN, "Poland", 20, 0, -20, "0", 20, 0, 0);
            poland.enableEvents(false);
            pane.getChildren().add(poland);
            territoryList.add(poland);

            france = Territory.addToMap("file:images/europe/france.png", 654, 332, 132, 69, Color.SEAGREEN, "France", 20, -40, 0, "0", 20, -40, 20);
            france.enableEvents(false);
            pane.getChildren().add(france);
            territoryList.add(france);

            // Africa
            algeria = Territory.addToMap("file:images/africa/algeria.png", 585, 424, 182, 164, Color.ORANGERED, "Algeria", 20, 0, -10, "0", 20, 0, 10);
            algeria.enableEvents(false);
            pane.getChildren().add(algeria);
            territoryList.add(algeria);
            
            egypt = Territory.addToMap("file:images/africa/egypt.png", 738, 445, 135, 61, Color.ORANGERED, "Egypt", 20, 0, -10, "0", 20, 0, 10);
            egypt.enableEvents(false);
            pane.getChildren().add(egypt);
            territoryList.add(egypt);

            sudan = Territory.addToMap("file:images/africa/sudan.png", 756, 496, 209, 152, Color.ORANGERED, "Sudan", 20, 0, -30, "0", 20, 0, -10);
            sudan.enableEvents(false);
            pane.getChildren().add(sudan);
            territoryList.add(sudan);

            congo = Territory.addToMap("file:images/africa/congo.png", 727, 579, 146, 101, Color.ORANGERED, "Congo", 20, -5, -10, "0", 20, -5, 10);
            congo.enableEvents(false);
            pane.getChildren().add(congo);
            territoryList.add(congo);

            south_africa = Territory.addToMap("file:images/africa/south_africa.png", 736, 649, 163, 116, Color.ORANGERED, "South Africa", 20, -15, 0, "0", 20, -15, 20);
            south_africa.enableEvents(false);
            pane.getChildren().add(south_africa);
            territoryList.add(south_africa);

            madagascar = Territory.addToMap("file:images/africa/madagascar.png", 880, 643, 63, 83, Color.ORANGERED, "Madagascar", 20, -10, -15, "0", 20, -10, 5);
            madagascar.enableEvents(false);
            pane.getChildren().add(madagascar);
            territoryList.add(madagascar);

            // Asia
            vladivostok = Territory.addToMap("file:images/asia/vladivostok.png", 1150, 106, 398, 248, Color.OLIVE, "Vladivostok", 20, 0, -60, "0", 20, 0, -40);
            vladivostok.enableEvents(false);
            pane.getChildren().add(vladivostok);
            territoryList.add(vladivostok);
            
            chita = Territory.addToMap("file:images/asia/chita.png", 1156, 235, 154, 84, Color.OLIVE, "Chita", 20, 0, -10, "0", 20, 0, 10);
            chita.enableEvents(false);
            pane.getChildren().add(chita);
            territoryList.add(chita);
                                
            china = Territory.addToMap("file:images/asia/china.png", 1060, 293, 308, 192, Color.OLIVE, "China", 20, 15, 10, "0", 20, 15, 30);
            china.enableEvents(false);
            pane.getChildren().add(china);
            territoryList.add(china);
            
            middle_east = Territory.addToMap("file:images/asia/middle_east.png", 834, 359, 205, 162, Color.OLIVE, "Middle East", 20, -10, -20, "0", 20, -10, 0);
            middle_east.enableEvents(false);
            pane.getChildren().add(middle_east);
            territoryList.add(middle_east);
            
            india = Territory.addToMap("file:images/asia/india.png", 1013, 379, 192, 167, Color.OLIVE, "India", 20, 0, -10, "0", 20, 0, 10);
            india.enableEvents(false);
            pane.getChildren().add(india);
            territoryList.add(india);
            
            vietnam = Territory.addToMap("file:images/asia/vietnam.png", 1189, 453, 89, 131, Color.OLIVE, "Vietnam", 20, 0, -30, "0", 20, 0, -10);
            vietnam.enableEvents(false);
            pane.getChildren().add(vietnam);
            territoryList.add(vietnam);
            
            japan = Territory.addToMap("file:images/asia/japan.png", 1398, 344, 99, 124, Color.OLIVE, "Japan", 20, 5, 10, "0", 20, 5, 30);
            japan.enableEvents(false);
            pane.getChildren().add(japan);
            territoryList.add(japan);

            omsk = Territory.addToMap("file:images/asia/omsk.png", 925, 206, 256, 126, Color.OLIVE, "Omsk", 20, -20, 0, "0", 20, -20, 20);
            omsk.enableEvents(false);
            pane.getChildren().add(omsk);
            territoryList.add(omsk);
                
            siberia = Territory.addToMap("file:images/asia/siberia.png", 1052, 115, 245, 140, Color.OLIVE, "Siberia", 20, -15, 0, "0", 20, -15, 20);
            siberia.enableEvents(false);
            pane.getChildren().add(siberia);
            territoryList.add(siberia);

            dudinka = Territory.addToMap("file:images/asia/dudinka.png", 987, 174, 202, 132, Color.OLIVE, "Dudinka", 20, 0, 0, "0", 20, 0, 20);
            dudinka.enableEvents(false);
            pane.getChildren().add(dudinka);
            territoryList.add(dudinka);

            aral = Territory.addToMap("file:images/asia/aral.png", 928, 296, 196, 84, Color.OLIVE, "Aral", 20, 0, -15, "0", 20, 0, 5);
            aral.enableEvents(false);
            pane.getChildren().add(aral);
            territoryList.add(aral);

            mongolia = Territory.addToMap("file:images/asia/mongolia.png", 1150, 313, 134, 53, Color.OLIVE, "Mongolia", 20, 0, -10, "0", 20, 0, 10);
            mongolia.enableEvents(false);
            pane.getChildren().add(mongolia);
            territoryList.add(mongolia);

            // Australia
            sumatra = Territory.addToMap("file:images/australia/sumatra.png", 1189, 612, 87, 74, Color.CORAL, "Sumatra", 20, 0, 0, "0", 20, 0, 20);
            sumatra.enableEvents(false);
            pane.getChildren().add(sumatra);
            territoryList.add(sumatra);
            
            borneo = Territory.addToMap("file:images/australia/borneo.png", 1297, 587, 89, 79, Color.CORAL, "Borneo", 20, 0, -10, "0", 20, 0, 10);
            borneo.enableEvents(false);
            pane.getChildren().add(borneo);
            territoryList.add(borneo);
            
            new_guinea = Territory.addToMap("file:images/australia/new_guinea.png", 1406, 609, 102, 71, Color.CORAL, "New Guinea", 20, 0, -20, "0", 20, 0, 0);
            new_guinea.enableEvents(false);
            pane.getChildren().add(new_guinea);
            territoryList.add(new_guinea);
            
            australia = Territory.addToMap("file:images/australia/australia.png", 1268, 692, 195, 121, Color.CORAL, "Australia", 20, 20, -15, "0", 20, 20, 5);
            australia.enableEvents(false);
            pane.getChildren().add(australia);
            territoryList.add(australia);

            // Set Borders ///////////////////////////////////////////////////////////////////////////

            // South America
            brazil.addBorders(venezuela, peru, argentina, algeria);
            venezuela.addBorders(brazil, peru, mexico);
            peru.addBorders(brazil, venezuela, argentina);
            argentina.addBorders(brazil, peru);

            // North America
            mexico.addBorders(venezuela, new_york, california);
            california.addBorders(mexico, new_york, vancouver, ottawa);
            new_york.addBorders(mexico, california, ottawa, labrador);
            vancouver.addBorders(california, ottawa, mackenzie, alaska);
            ottawa.addBorders(vancouver, labrador, new_york, california, mackenzie);
            labrador.addBorders(greenland, new_york, ottawa);
            mackenzie.addBorders(ottawa, vancouver, alaska, greenland);
            alaska.addBorders(vladivostok, mackenzie, vancouver);
            greenland.addBorders(mackenzie, labrador, iceland);

            // Europe
            iceland.addBorders(england, greenland);
            england.addBorders(iceland, sweden, germany, france);
            sweden.addBorders(england, moscow);
            germany.addBorders(poland, france, england);
            france.addBorders(germany, algeria, england, poland, egypt);
            poland.addBorders(middle_east, egypt, moscow, germany, france);
            moscow.addBorders(poland, middle_east, aral, omsk, sweden);

            // Africa
            egypt.addBorders(france, poland, algeria, sudan, middle_east);
            algeria.addBorders(brazil, egypt, france, sudan, congo);
            sudan.addBorders(egypt, algeria, congo, south_africa, madagascar);
            congo.addBorders(sudan, algeria, south_africa);
            madagascar.addBorders(sudan, south_africa);
            south_africa.addBorders(congo, madagascar, sudan);

            // Asia
            middle_east.addBorders(egypt, moscow, poland, india, aral);
            aral.addBorders(middle_east, omsk, china, moscow, india);
            omsk.addBorders(aral, moscow, china, mongolia, dudinka);
            dudinka.addBorders(siberia, chita, mongolia, omsk);
            siberia.addBorders(chita, dudinka, vladivostok);
            chita.addBorders(dudinka, siberia, vladivostok, mongolia, china);
            mongolia.addBorders(omsk, china, chita, dudinka);
            vladivostok.addBorders(chita, siberia, china, japan, alaska);
            japan.addBorders(vladivostok, china);
            china.addBorders(vietnam, india, japan, vladivostok, mongolia, chita, omsk, aral);
            india.addBorders(sumatra, aral, middle_east, vietnam, china);
            vietnam.addBorders(india, china, borneo);

            // Australia
            australia.addBorders(sumatra, borneo, new_guinea);
            borneo.addBorders(vietnam, new_guinea, australia);
            sumatra.addBorders(india, australia);
            new_guinea.addBorders(borneo, australia);

            ////////////////////////////////////////////////////////////////////////////////

            //setting territories for players
            playerTerritories.put(3, new ArrayList<Territory>());
            playerTerritories.put(4, new ArrayList<Territory>());
            playerTerritories.put(5, new ArrayList<Territory>());
            playerTerritories.put(6, new ArrayList<Territory>());

            //setting colors for each player
            playerColors.put(3, Color.BLUE);
            playerColors.put(4, Color.GREEN);
            playerColors.put(5, Color.PURPLE);
            playerColors.put(6, Color.ORANGE);
            
            // drawing/creating buttons
            finishTurnButton.setText("Finish Turn");
            finishTurnButton.setTranslateX(1450);
            finishTurnButton.setTranslateY(30);
            pane.getChildren().add(finishTurnButton);

            startButton.setText("Start Game");
            startButton.setTranslateX(40);
            startButton.setTranslateY(30);
            pane.getChildren().add(startButton);

            resetButton.setText("Reset Game");
            resetButton.setTranslateX(140);
            resetButton.setTranslateY(30);
            pane.getChildren().add(resetButton);

            playerCountChoice.setTranslateX(140);
            playerCountChoice.setTranslateY(60);
            pane.getChildren().add(playerCountChoice);

            playerCount.setText("# Of Players:");
            playerCount.setFont((Font.font("Arial", 16)));
            playerCount.setTextFill(Color.WHITE);
            playerCount.setTranslateX(40);
            playerCount.setTranslateY(60);
            pane.getChildren().add(playerCount);

            roundCount.setText("Round: " + String.valueOf(round));
            roundCount.setFont((Font.font("Arial", 16)));
            roundCount.setTextFill(Color.WHITE);
            roundCount.setTranslateX(400);
            roundCount.setTranslateY(30);
            pane.getChildren().add(roundCount);

            playerTurn.setText("Turn: " + String.valueOf(turn));
            playerTurn.setFont((Font.font("Arial", 16)));
            playerTurn.setTextFill(Color.WHITE);
            playerTurn.setTranslateX(400);
            playerTurn.setTranslateY(60);
            pane.getChildren().add(playerTurn);

            textBox.setTranslateX(575);
            textBox.setTranslateY(800);
            pane.getChildren().add(textBox);
            ///////////////////////////////////////////////////////////////////////////////////
            
            // creating event handlers for buttons

            resetGame();

            
            startButton.setOnMouseClicked(event -> {
                if (playerCountChoice.getValue() == null) {
                    textBox.setText("Select player count and then press 'Start Game'");
                }else {
                    gameStarted = true;
                    turn = 1;
                    round = 1;
                    numPlayers = (int) playerCountChoice.getValue();

                    playerTurn.setText("Turn: " + String.valueOf(turn));
                    roundCount.setText("Round: " + String.valueOf(round));

                    startButton.setVisible(false);

                    textBox.setText("Game has started. It is now player " + String.valueOf(turn) + "'s turn");
                    //distributeTerritories();
                }
            });

            resetButton.setOnMouseClicked(event -> {
                resetGame();
            });

            ///////////////////////////////////////////////////////////////////////////////////

            scene = new Scene(pane, 1600, 900);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setTitle("War Map");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    // REF: http://fxexperience.com/2016/01/node-picking-in-javafx/
    public Node pick(Node node, double sceneX, double sceneY, Node ignoreNode) {
        Point2D p = node.sceneToLocal(sceneX, sceneY, true /* rootScene */);

        // check if the given node has the point inside it, or else we drop out
        if (!node.contains(p))
            return null;

        // at this point we know that _at least_ the given node is a valid
        // answer to the given point, so we will return that if we don't find
        // a better child option
        if (node instanceof Parent) {
            // we iterate through all children in reverse order, and stop when we find a
            // match.
            // We do this as we know the elements at the end of the list have a higher
            // z-order, and are therefore the better match, compared to children that
            // might also intersect (but that would be underneath the element).
            Node bestMatchingChild = null;
            List<Node> children = ((Parent) node).getChildrenUnmodifiable();
            for (int i = children.size() - 1; i >= 0; i--) {
                Node child = children.get(i);
                p = child.sceneToLocal(sceneX, sceneY, true /* rootScene */);
                if (child != ignoreNode &&
                    child.isVisible() && 
                    !child.isMouseTransparent() 
                    && child.contains(p)) {
                    bestMatchingChild = child;
                    break;
                }
            }

            if (bestMatchingChild != null) {
                return pick(bestMatchingChild, sceneX, sceneY, ignoreNode);
            }
        }

        return node;
    }

    public void bringToFront(Node a) {
        pane.getChildren().remove(a);
        pane.getChildren().add(a);
    }

    public void swap(Node a, Node b) {
        int ia = pane.getChildren().indexOf(a);
        int ib = pane.getChildren().indexOf(b);
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(pane.getChildren());
        Collections.swap(workingCollection, ia, ib);
        pane.getChildren().setAll(workingCollection);

    }

    // private void distributeTerritories() throws IOException {

    // }


    public static void main(String[] args) {
        launch(args);
    }
}