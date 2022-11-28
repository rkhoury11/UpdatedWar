import java.util.HashSet;
import java.util.Set;

import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class Territory extends StackPane {
    public int owned; //store which player owns this territory
    public Set<Territory> borders; //store bordering territories
    public String sName;
    //public boolean addedDudes = true;
    //public int dudeCount;
    

    public Territory(
        String imageUrl, int x, int y, int w, int h,
        String territoryName, int nameSize, int nameX, int nameY,
        String territoryTroops, int scoreSize, int scoreX, int scoreY) 
    {

        //init image and values for image placement
        ImageView image = new ImageView(imageUrl);
        image.setFitWidth(w/(1.8));
        image.setFitHeight(h/(1.8));
        image.setPickOnBounds(false); // Disable hittest on transparent pixels
        image.setCache(true);
        image.setCacheHint(CacheHint.SPEED);        
        image.setPreserveRatio(true);

        sName = territoryName;
        //init name text and placement values
        Text name = new Text(territoryName);
        name.setFont(Font.font(nameSize/(1.8)));
        name.setMouseTransparent(true);
        name.setTranslateX(nameX/(1.8));
        name.setTranslateY(nameY/(1.8));
        
        //init score text and placement values
        Text troop = new Text(territoryTroops);
        troop.setFont(Font.font(scoreSize/(1.8)));
        troop.setMouseTransparent(true);
        troop.setTranslateX(scoreX/(1.8));
        troop.setTranslateY(scoreY/(1.8));

        //add to Pane and position correctly
        setPickOnBounds(false);
        getChildren().addAll(image, name, troop);
        relocate(x/(1.8), y/(1.8));

        borders = new HashSet<Territory>(); //to store bordering territories

        //mouse hover in effect
        image.setOnMouseEntered(event -> {
            //NOT IN ADDING TROOPS MODE
            if(TerritoryMap.targetTerritory == null) {
                if(TerritoryMap.inAddingTroopsMode == false) {
                    //NO SELECTED TERRITORY
                    if(TerritoryMap.selectedTerritory == null) {
                        //OWN CURRENT TILE
                        if (owned == TerritoryMap.currentTurn) {
                            setBrightness(+0.50);
                            setCursor(Cursor.HAND);
                        }
                    }
                    
                    //THERE IS A SELECTED TERRITORY
                    else {
                        //THE CURRENT TILE IS WITHIN SELECTED TILE BORDERS
                        if (TerritoryMap.selectedTerritory.borders.contains(this)) {
                            //YOU OWN IT
                            if (owned != TerritoryMap.currentTurn) {
                                setBrightness(+0.50);
                                setCursor(Cursor.HAND);
                            }
                        } 
                    }
                } 
                
                //IN ADDING TROOPS MODE
                else {
                    //NO SELECTED TERRITORY
                    if(TerritoryMap.selectedTerritory == null) {
                        //OWN CURRENT TILE
                        if (owned == TerritoryMap.currentTurn) {
                            setBrightness(+0.50);
                            setCursor(Cursor.HAND);
                        }
                    }
                } 
            }
        });

        //mouse hover out effect
        image.setOnMouseExited(event -> {
            if(TerritoryMap.targetTerritory == null) {
                //NOT IN ADDING TROOPS MODE
                if(TerritoryMap.inAddingTroopsMode == false) {
                    //NO SELECTED TERRITORY
                    if(TerritoryMap.selectedTerritory == null) {
                        // OWN CURRENT TILE
                        if (owned == TerritoryMap.currentTurn) {
                            setBrightness(0);
                            setCursor(Cursor.DEFAULT);
                        }
                    }
                    
                    //THERE IS A SELECTED TERRITORY
                    else {
                        //THE CURRENT TILE IS WITHIN SELECTED TILE BORDERS
                        if (TerritoryMap.selectedTerritory.borders.contains(this)) {
                            //YOU OWN IT
                            if (owned != TerritoryMap.currentTurn) {
                                setBrightness(0);
                                setCursor(Cursor.DEFAULT);
                            }
                        } 
                    }
                } 
                
                //IN ADDING TROOPS MODE
                else {
                    //NO SELECTED TERRITORY
                    if(TerritoryMap.selectedTerritory == null) {
                        //OWN CURRENT TILE
                        if (owned == TerritoryMap.currentTurn) {
                            setBrightness(0);
                            setCursor(Cursor.DEFAULT);
                        }
                    }
                }
            }
        });

        

        //territory clicked functionality
        image.setOnMouseClicked(event -> {
            //NOT IN ADDING TROOPS MODE
            if(TerritoryMap.inAddingTroopsMode == false) {
                //NO SELECTED TERRITORY
                if(TerritoryMap.selectedTerritory == null) {
                    //OWN CURRENT TILE
                    if (owned == TerritoryMap.currentTurn) {
                        //SET SELECTED TILE TO THIS ONE
                        TerritoryMap.selectedTerritory = this;
                        //DARKEN ALL BUT THIS ONE AND THE BORDERING TILES
                        for(Territory t : TerritoryMap.territoryList) {
                            if(borders.contains(t) == false || t.owned == TerritoryMap.currentTurn) {
                                t.setBrightness(-0.80);
                            }
                        }
                        TerritoryMap.selectedTerritory.setBrightness(0);
                    }
                } 
                
                    else if (this == TerritoryMap.targetTerritory) {
                        TerritoryMap.targetTerritory = null;
                        for(Territory t : TerritoryMap.selectedTerritory.borders) {
                            t.setBrightness(0);
                        }
                    }
                
                //THERE IS A SELECTED TERRITORY
                else {
                    //THE CURRENT TILE IS WITHIN SELECTED TILE BORDERS
                    if (TerritoryMap.selectedTerritory.borders.contains(this)) {
                        //YOU DO NOT OWN IT
                        if (owned != TerritoryMap.currentTurn) {
                            //SET TARGET TILE TO THIS ONE
                            TerritoryMap.targetTerritory = this;
                            
                            //DARKEN ALL TILES BUT SELECTED AND TARGET 
                            for(Territory t : TerritoryMap.territoryList) {
                                if(t != TerritoryMap.selectedTerritory && t != TerritoryMap.targetTerritory) {
                                    t.setBrightness(-0.80);
                                }
                            }
                        } 
                    } 
                    
                    //THE CURRENT TILE IS THE SELECTED TERRITORY
                    else if (this == TerritoryMap.selectedTerritory){
                        //RESET SELECTED AND TARGET TILES
                        TerritoryMap.selectedTerritory = null;
                        TerritoryMap.targetTerritory = null;

                        //RESET ALL TILE BRIGHTNESS
                        for(Territory t : TerritoryMap.territoryList) {
                            t.setBrightness(0);
                        }
                    }
                }
            } 
            
            //IN ADDING TROOPS MODE
            else {
                //NO SELECTED TERRITORY
                if(TerritoryMap.selectedTerritory == null) {
                    //OWN CURRENT TILE
                    if (owned == TerritoryMap.currentTurn) {
                        //SET SELECTED TILE TO THIS ONE
                        TerritoryMap.selectedTerritory = this;

                        //DARKEN ALL BUT THIS TILE
                        for(Territory t : TerritoryMap.territoryList) {
                            if(t != this) {
                                t.setBrightness(-0.80);
                            }
                        }
                    }
                }

                //THERE IS A SELECTED TILE
                else {
                    //YOU CLICK ON THE SELECTED TILE
                    if(this == TerritoryMap.selectedTerritory) {
                        //RESET SELECTED TILE
                        TerritoryMap.selectedTerritory = null;
                        //BRIGHTEN OWNED TILES
                        for(Territory t : TerritoryMap.territoryList) {
                            if(t.owned == TerritoryMap.currentTurn) {
                                t.setBrightness(0);
                            }
                        }
                    }
                }
            }
        });
    }


    public void addBorders(Territory... args) {
        for (Territory border : args) {
            borders.add(border);
        }
    }

    public ImageView getImage() {
        Node node = getChildren().get(0); // NOTE: addMapNode MUST insert image at position 0
        if (node instanceof ImageView) {
            return (ImageView) node;
        }
        return null;
    }

    public void setColor(Color color) {
        ImageView imageView = getImage();
        if (imageView instanceof ImageView) {
            Image image = imageView.getImage();
            PixelReader pixelReader = image.getPixelReader();
            WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color actualColor = pixelReader.getColor(x, y);
                    if (actualColor.getOpacity() != 0)
                        pixelWriter.setColor(x, y, color);
                }
            }
            imageView.setImage(writableImage);
        }
    }

    public void setName(String name) {
        Text text = getName();
        if (text instanceof Text) {
            text.setText(name);
        }
    }

    public Text getName() {
        Node node = getChildren().get(1); // NOTE: addMapNode MUST insert name at position 1
        if (node instanceof Text) {
            return (Text) node;
        }
        return null;
    }

    public void setTroop(int score) {
        Node node = getChildren().get(2); // NOTE: addMapNode MUST insert score at position 2
        if (node instanceof Text) {
            ((Text) node).setText(Integer.toString(score));
        }
    }

    public void addTroop() {
        Node node = getChildren().get(2); // NOTE: addMapNode MUST insert score at position 2
        if (node instanceof Text) {
            ((Text) node).setText(String.valueOf(getTroopCount() + 1));
        }
    }

    public int getTroopCount() {
        Node node = getChildren().get(2); // NOTE: addMapNode MUST insert score at position 2
        if (node instanceof Text) {
            String text = ((Text)node).getText();
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setBrightness(double value) {
        ImageView image = getImage(); 
        if (image instanceof ImageView) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(value); 
            image.setEffect(colorAdjust);
        }
    }    
}