package DrawMap;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class Territory extends StackPane {    
    public Set<Territory> borders;

    public Territory() {
        borders = new HashSet<Territory>();
    }

    public void addBorders(Territory... args) {
        for (Territory border : args) {
            borders.add(border);
        }
    }

    static public Territory addToMap(
        String imageUrl, int x, int y, int w, int h, Color color,
        String territoryName, int nameSize, int nameX, int nameY,
        String territoryScore, int scoreSize, int scoreX, int scoreY) 
    {

        ImageView image = new ImageView(imageUrl);
        image.setFitWidth(w);
        image.setFitHeight(h);
        image.setPickOnBounds(false); // Disable hittest on transparent pixels
        image.setCache(true);
        image.setCacheHint(CacheHint.SPEED);        
        image.setPreserveRatio(true);

        Text name = new Text(territoryName);
        name.setFont(Font.font(null, FontWeight.BOLD, nameSize));
        name.setMouseTransparent(true);
        name.setStroke(Color.BLACK);
        name.setOpacity(0.7);
        name.setFill(Color.WHITE);
        name.setTranslateX(nameX);
        name.setTranslateY(nameY);

        Text score = new Text(territoryScore);
        score.setFont(Font.font(null, FontWeight.BOLD, scoreSize));
        score.setMouseTransparent(true);
        score.setStroke(Color.BLACK);
        score.setFill(Color.WHITE);
        score.setTranslateX(scoreX);
        score.setTranslateY(scoreY);

        Territory territory = new Territory();
        territory.getChildren().addAll(image, name, score);
        territory.relocate(x, y);
        territory.setColor(color);
        return territory;
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

    public Text getName() {
        Node node = getChildren().get(1); // NOTE: addMapNode MUST insert name at position 1
        if (node instanceof Text) {
            return (Text) node;
        }
        return null;
    }

    public int getScore() {
        Node node = getChildren().get(2); // NOTE: addMapNode MUST insert score at position 2
        if (node instanceof Text) {
            String text = ((Text)node).getText();
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setName(String name) {
        Text text = getName();
        if (text instanceof Text) {
            text.setText(name);
        }
    }

    public void setScore(int score) {
        Node node = getChildren().get(2); // NOTE: addMapNode MUST insert score at position 2
        if (node instanceof Text) {
            ((Text) node).setText(Integer.toString(score));
        }
    }

    public void setBrightness(double value) {
        ImageView image = getImage(); 
        if (image instanceof ImageView) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(value); 
            image.setEffect(colorAdjust);
        }
    }

    public void enableEvents(boolean enableDragging) {
        final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();

        setOnMousePressed(event -> mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY())));

        if (enableDragging) {
            setOnMouseDragged(event -> {
                double deltaX = event.getSceneX() - mouseAnchor.get().getX();
                double deltaY = event.getSceneY() - mouseAnchor.get().getY();
                relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
                mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));
            });
        }

        ImageView image = getImage(); 
        if (image instanceof ImageView) {

            image.setOnMouseEntered(event -> {
                setBrightness(+0.30);
            });

            image.setOnMouseExited(event -> {
                setBrightness(0);
            });

            image.setOnMouseClicked(event -> {
                int score = getScore();
                setScore(score + 1);
                setColor(Color.rgb(255, 0, 255));

                for (Territory border : borders) {
                    border.setBrightness(+0.50);
                    border.setColor(Color.rgb(0, 255, 255));
                }        
            });

            setOnMouseReleased(event -> {
                
            });

            /* Pick territory by point coordinates
            Point2D pt = new Point2D(event.getSceneX(), event.getSceneY());
            var htNode = pick(pane, pt.getX(), pt.getY(), null);
            var territoryClicked = htNode.getParent();
            var nameClicked = getTerritoryName((Territory) territoryClicked);
            //*/

        }
    }    
}