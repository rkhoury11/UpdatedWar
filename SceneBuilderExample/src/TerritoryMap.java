import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.Pane;
// import javafx.scene.paint.Color;

public class TerritoryMap extends Pane {

    static Territory brazil;
    static Territory venezuela;
    static Territory peru;
    static Territory argentina;
    static Territory alaska;
    static Territory mackenzie;
    static Territory greenland;
    static Territory vancouver;
    static Territory ottawa;
    static Territory labrador;
    static Territory california;
    static Territory new_york;
    static Territory mexico;
    static Territory iceland;
    static Territory england;
    static Territory sweden;
    static Territory moscow;
    static Territory germany;
    static Territory poland;
    static Territory france;
    static Territory algeria;
    static Territory egypt;
    static Territory sudan;
    static Territory congo;
    static Territory south_africa;
    static Territory madagascar;
    static Territory vladivostok;
    static Territory siberia;
    static Territory chita;
    static Territory dudinka;
    static Territory omsk;
    static Territory aral;
	static Territory mongolia;
    static Territory china;
    static Territory middle_east;
    static Territory india;
    static Territory vietnam;
    static Territory japan;
    static Territory sumatra;
    static Territory borneo;
    static Territory new_guinea;
    static Territory australia;

    public static Territory selectedTerritory;
    public static Territory targetTerritory;
    public static boolean inAddingTroopsMode = false;
    public static boolean inFightMode = false;

    
    public static List<Territory> territoryList = new ArrayList<Territory>();
    public static List<Territory> NorthAmerica = new ArrayList<Territory>();

    

    public static int currentTurn;
    
    public TerritoryMap() {
        alaska = new Territory("file:images/alaska.png", 51, 100, 183, 109, "Alaska", 20, 20, -10, "0", 20, 20, 10);
        algeria = new Territory("file:images/algeria.png", 585, 424, 182, 164, "Algeria", 20, 0, -10, "0", 20, 0, 10);
        aral = new Territory("file:images/aral.png", 928, 296, 196, 84, "Aral", 20, 0, -15, "0", 20, 0, 5);
        argentina = new Territory("file:images/Argentina.png", 356, 655, 91, 173, "Argentina", 20, -10, -50, "0", 20, -10, -30);
        australia = new Territory("file:images/australia.png", 1200, 692, 240, 180, "Australia", 20, 20, -15, "0", 20, 20, 5);
        borneo = new Territory("file:images/borneo.png", 1297, 587, 89, 79, "Borneo", 20, 0, -10, "0", 20, 0, 10);
        brazil = new Territory("file:images/Brazil.png", 335, 539, 215, 175, "Brazil", 20, 15, -35, "0", 20, 15, -15);
        california = new Territory("file:images/california.png", 149, 288, 170, 117, "California", 20, -10, -15, "0", 20, -10, 5);
        china = new Territory("file:images/china.png", 1060, 293, 308, 192, "China", 20, 15, 10, "0", 20, 15, 30);
        chita = new Territory("file:images/chita.png", 1156, 235, 154, 84, "Chita", 20, 0, -10, "0", 20, 0, 10);
        congo = new Territory("file:images/congo.png", 727, 579, 146, 101, "Congo", 20, -5, -10, "0", 20, -5, 10);
        dudinka = new Territory("file:images/dudinka.png", 987, 174, 202, 132, "Dudinka", 20, 0, 0, "0", 20, 0, 20);
        egypt = new Territory("file:images/egypt.png", 738, 445, 135, 61, "Egypt", 20, 0, -10, "0", 20, 0, 10);
        england = new Territory("file:images/england.png", 625, 247, 83, 71, "England", 20, 0, -5, "0", 20, 0, 15);
        france = new Territory("file:images/france.png", 654, 332, 132, 69, "France", 20, -40, 0, "0", 20, -40, 20);
        germany = new Territory("file:images/germany.png", 707, 281, 52, 67, "Germany", 20, 0, -15, "0", 20, 0, 5);
        greenland = new Territory("file:images/greenland.png", 484, 100, 154, 119, "Greenland", 20, 0, -25, "0", 20, 0, -5);
        iceland = new Territory("file:images/iceland.png", 618, 168, 103, 51, "Iceland", 20, 0, -5, "0", 20, 0, 15);
        india = new Territory("file:images/india.png", 1013, 379, 192, 167, "India", 20, 0, -10, "0", 20, 0, 10);
        japan = new Territory("file:images/japan.png", 1398, 344, 99, 124, "Japan", 20, 5, 10, "0", 20, 5, 30);
        labrador = new Territory("file:images/labrador.png", 394, 248, 103, 102, "Labrador", 20, 0, -20, "0", 20, 0, 0);
        mackenzie = new Territory("file:images/mackenzie.png", 221, 131, 275, 115, "Mackenzie", 20, 0, 0, "0", 20, 0, 20);
        madagascar = new Territory("file:images/madagascar.png", 880, 643, 63, 83, "Madagascar", 20, -10, -15, "0", 20, -10, 5);
        mexico = new Territory("file:images/mexico.png", 158, 383, 142, 129, "Mexico", 20, -15, -20, "0", 20, -15, 0);
        middle_east = new Territory("file:images/middle_east.png", 834, 359, 205, 162, "Middle East", 20, -10, -20, "0", 20, -10, 0);
        mongolia = new Territory("file:images/mongolia.png", 1150, 313, 134, 53, "Mongolia", 20, 0, -10, "0", 20, 0, 10);
        moscow = new Territory("file:images/moscow.png", 810, 215, 115, 156, "Moscow", 20, 5, 0, "0", 20, 5, 20);
        new_guinea = new Territory("file:images/new_guinea.png", 1406, 609, 102, 71, "New Guinea", 20, 0, -20, "0", 20, 0, 0);
        new_york = new Territory("file:images/new_york.png", 232, 315, 196, 115, "New York", 20, -5, -15, "0", 20, -5, 5);
        omsk = new Territory("file:images/omsk.png", 925, 206, 256, 126, "Omsk", 20, -20, 0, "0", 20, -20, 20);
        ottawa = new Territory("file:images/ottawa.png", 306, 252, 106, 99, "Ottawa", 20, 0, -15, "0", 20, 0, 5);
        peru = new Territory("file:images/Peru.png", 291, 551, 129, 297, "Peru", 20, 0, -85, "0", 20, 0, -65);
        poland = new Territory("file:images/poland.png", 765, 310, 78, 98, "Poland", 20, 0, -20, "0", 20, 0, 0);
        siberia = new Territory("file:images/siberia.png", 1052, 115, 245, 140, "Siberia", 20, -15, 0, "0", 20, -15, 20);
        south_africa = new Territory("file:images/south_africa.png", 736, 649, 163, 116, "South Africa", 20, -15, 0, "0", 20, -15, 20);
        sudan = new Territory("file:images/sudan.png", 756, 496, 209, 152, "Sudan", 20, 0, -30, "0", 20, 0, -10);
        sumatra = new Territory("file:images/sumatra.png", 1189, 612, 87, 74, "Sumatra", 20, 0, 0, "0", 20, 0, 20);
        sweden = new Territory("file:images/sweden.png", 738, 204, 113, 84, "Sweden", 20, -5, -30, "0", 20, -5, -10 );
        vancouver = new Territory("file:images/winchester.png", 170, 200, 173, 101, "Vancouver", 20, 0, 0, "0", 20, 0, 20);
        venezuela = new Territory("file:images/Venezuela.png", 311, 497, 148, 76, "Venezuela", 20, -15, -30, "0", 20, -15, -10);
        vietnam = new Territory("file:images/vietnam.png", 1189, 453, 89, 131, "Vietnam", 20, 0, -30, "0", 20, 0, -10);
        vladivostok = new Territory("file:images/vladivostok.png", 1150, 106, 398, 248, "Vladivostok", 20, 0, -60, "0", 20, 0, -40);

        NorthAmerica.addAll(Arrays.asList(
            alaska, mackenzie, greenland, vancouver
        ));

        

        getChildren().addAll(Arrays.asList(
            brazil,venezuela,peru,argentina,
            alaska,mackenzie,greenland,vancouver,
            ottawa,labrador,california,new_york,
            mexico,iceland,england,sweden,
            moscow,germany,poland,france,
            algeria,egypt,sudan,congo,
            south_africa,madagascar,vladivostok,
            chita,china,middle_east,
            india,vietnam,japan,omsk,
            siberia,dudinka,aral,mongolia,
            sumatra,borneo,new_guinea,australia
        ));

        territoryList.addAll(Arrays.asList(
            brazil,venezuela,peru,argentina,
            alaska,mackenzie,greenland,vancouver,
            ottawa,labrador,california,new_york,
            mexico,iceland,england,sweden,
            moscow,germany,poland,france,
            algeria,egypt,sudan,congo,
            south_africa,madagascar,vladivostok,
            chita,china,middle_east,
            india,vietnam,japan,omsk,
            siberia,dudinka,aral,mongolia,
            sumatra,borneo,new_guinea,australia
        ));

        

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
    }
}