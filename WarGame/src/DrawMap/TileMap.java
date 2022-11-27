package DrawMap;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TileMap extends Pane {
    private static int size = 30; //length of one side of rectangle tile (i.e. h x w)
    public List<List<Tile>> tileList = new ArrayList<List<Tile>>(); //list used to store all tiles to access later
    List<Tile> ownedTiles = new ArrayList<Tile>(); //list containing the tile that were clicked on in a turn
    public Color playerColor = Color.BLACK; //color of owned territories by the current player
    public Color secondaryColor = Color.BLACK; //color of contested territories by the current player
    public int playerNumber; //the integer value of the current turn
    public Tile selectedTile; 
    public Tile targetTile;
    
    //Tile class
    public class Tile extends Rectangle {
        public int owned; //track if tile is owned already
        public Text text = new Text(" "); //text for tile values
        public int value; //value of the tile (from above text)
        public StackPane p = new StackPane(); //pane for tile shape and text
        public boolean clicked = false;
       
        //constructor
        Tile(double x, double y) {
            //set size of tile with global size up top
            setWidth(size);
            setHeight(size);
            
            //set position of tile on the pane
            p.setLayoutX(x);
            p.setLayoutY(y);
            text.setPickOnBounds(false);
            
            //set initial colors of tile
            setFill(Color.GRAY);
            setStrokeWidth(2);
            setStroke(Color.BLACK);
            
            //when tile is clicked, change to red, when clicked again change to beige
            setOnMouseClicked(e -> {
                if(playerNumber == owned) {
                    if(clicked == false) {
                        selectedTile = this;
                        for(Tile t : getNeighbors(this)) {
                            if (t.owned != playerNumber){
                                t.setStroke(Color.YELLOW);
                            }
                            
                        }
                        clicked = true;                  
                    } else {
                        for(Tile t : getNeighbors(this)) {
                            t.setStroke(Color.BLACK);
                        }
                        selectedTile = null;
                        clicked = false;
                    }
                } else if (getNeighbors(selectedTile).contains(this) && owned != playerNumber && selectedTile != null) {
                    targetTile = this;
                    for(Tile t : getNeighbors(selectedTile)) {
                        t.setStroke(Color.BLACK);
                    }
                    targetTile.setStroke(Color.RED);
                    selectedTile.setStroke(Color.YELLOW);
                }
            });
        
            //set mouse entered and exited for the hover over effect
            setOnMouseEntered(e -> {
                if((playerNumber == owned && selectedTile == null) || (getNeighbors(selectedTile).contains(this) && owned != playerNumber && selectedTile != null)) {
                    setCursor(Cursor.HAND);
                }
            });
            setOnMouseExited(e -> {
                setCursor(Cursor.DEFAULT);
            });
            
            //add text and shape to pane
            p.getChildren().addAll(this, text);
        }
    }
    
    //take in mapSize to determine length of square map
    public TileMap(int mapSize, Color col) {
        
        //nested for loop to create and display each tile
        for(int x = 0; x < mapSize; x++) {
            List<Tile> tmp = new ArrayList<Tile>(); //tmp list to store the tiles in col x
            tileList.add(x,tmp); //add col to main list
            for(int y = 0; y < mapSize; y++) {
                int xCoord = x * size; //create pixel coordinate of x
                int yCoord = y * size; //create pixel coordinate of y
                Tile tile = new Tile(xCoord, yCoord); //create new tile in that spot
                tmp.add(y, tile); //add tile to col list to access later
                tile.getProperties().put("x", x); //put the x coordinate in the tile's properties
                tile.getProperties().put("y", y); //put the y coordinate in the tile's properties
                getChildren().add(tile.p); //add tile to grid
            }
        }
    }
    
    //return the coordinates of a tile in string format
    public String getTileCoordinates(Tile t) {
        return ("(" + t.getProperties().get("x") + "," + t.getProperties().get("y") + ")");
    }
    
    //return the list of currently owned tiles (tiles that have been clicked on)
    public List<Tile> getOwnedTiles() {
        return ownedTiles;
    }
    
    //fill neighbors of a tile with secondary color only if they are in bounds of map
    public List<Tile> getNeighbors(Tile t) {
        
        List<Tile> neighbors = new ArrayList<Tile>();
        int xCoordinate = (int)t.getProperties().get("x");
        int yCoordinate = (int)t.getProperties().get("y");
        
        for(int i = -1; i <= 1; i++) {
            if(xCoordinate + i >= 0 && xCoordinate + i <= tileList.size()-1) {
                for(int j=-1; j<=1; j++) {
                    if(yCoordinate + j >= 0 && yCoordinate + j <= tileList.size()-1 && (tileList.get(xCoordinate + i).get(yCoordinate + j) != t)) {
                        neighbors.add((tileList.get(xCoordinate + i).get(yCoordinate + j)));
                    }
                }
            }
        }
        return neighbors;
        // int xCoordinate = (int)t.getProperties().get("x");
        // int yCoordinate = (int)t.getProperties().get("y");
        // if ((xCoordinate - 1 ) >= 0){
        //     tileList.get(xCoordinate - 1).get(yCoordinate).setFill(Color.LIGHTGRAY);
        // }
        // if ((xCoordinate + 1 ) <= tileList.size() - 1){
        //     tileList.get(xCoordinate + 1).get(yCoordinate).setFill(Color.LIGHTGRAY);
        // }
        // if ((yCoordinate - 1 ) >= 0){
        //     tileList.get(xCoordinate).get(yCoordinate - 1).setFill(Color.LIGHTGRAY);
        // }
        // if ((yCoordinate + 1 ) >= tileList.size() -1){
        //     tileList.get(xCoordinate).get(yCoordinate + 1).setFill(Color.LIGHTGRAY);
        // }
    }
}

// click on one of your items first
// all of your other items become ineditable
// must click on a neighbor

