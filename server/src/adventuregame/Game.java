package adventuregame;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.json.*;

public class Game {
    public enum SoundOption {
        VOLUME10,
        VOLUME9,
        VOLUME8,
        VOLUME7,
        VOLUME6,
        VOLUME5,
        VOLUME4,
        VOLUME3,
        VOLUME2,
        VOLUME1,
        VOLUME0,
    }
    public enum TextSpeedOption {
        INSTANT,
        FAST,
        NORMAL,
        SLOW
    }
    public enum TextSizeOption {
        LARGE,
        MEDIUM,
        SMALL
    }
    public enum BackgroundColor {
        BLACK,
        WHITE,
        BLUE
    }
    
    static ArrayList<Choice> choices = new ArrayList<Choice>();

    public static Choice getChoice(int id) {
        return choices.get(id);
    }

    // TO BE IMPLEMENTED
    // Should return the default settings if the player hasn't modified the settings, or whatever their settings were if they have saved settings.
    public static void loadSettings(Player p) {
        p.setSettings(new Settings());
    }

    public static void loadGame(Player p) throws Exception {
        startNewGame(p);
    }
    public static void startNewGame(Player p) throws Exception {
        createChoiceList();
        p.setScores(new Scores());
        p.setChoice(choices.get(0)); // set the players next choice to the first choice
    }
    //Loads up all of the choices into the choices array to be used throughout the game. Should only be run once, when the user starts up the game.
    public static void createChoiceList() throws Exception {
        //Reads the Json file
        InputStream fis = new FileInputStream("game.json");
        JsonReader reader = Json.createReader(fis);
        JsonObject gameObject = reader.readObject();
        reader.close();
        
        //Gets the choices array from the json
        JsonArray choicesArray = gameObject.getJsonArray("choices");        
        
        //Local variables used to hold read information from json
        Option createdOption;
        Choice createdChoice;
        Condition createdCondition;
        Outcome createdOutcome;
          
        //Loop to add choices from json to list in Game class
        for (int i = 0; i < choicesArray.size(); i++) {
            //Creating a new ArrayList instance each time is necessary as clear() leads to bugs.
            ArrayList<Option> optionList = new ArrayList<Option>();

            createdChoice = new Choice();

            //Gets the choice object at index i
            JsonObject object = choicesArray.getJsonObject(i);

            //Gets the options array for the choice at index i
            JsonArray optionsArray = object.getJsonArray("options");

            //Loop to add options to the choice at index i, and initalize options with conditions.
            for (int j = 0; j < optionsArray.size(); j++) {
                ArrayList<Condition> testConditions = new ArrayList<Condition>();
                createdOption = new Option();
                JsonObject optionObject = optionsArray.getJsonObject(j);
                createdOption.id = optionObject.getInt("id");
                createdOption.text = optionObject.getJsonString("text").getString();
                createdCondition = new Condition(optionObject.getInt("next_choice"));
                testConditions.add(createdCondition);
                createdOutcome = new Outcome(testConditions);
                createdOption.outcome = createdOutcome;
                optionList.add(createdOption);
            }
            createdChoice.id = i;
            createdChoice.text = object.getString("text");
            createdChoice.options = optionList;

            //Choice is offically added to ArrayList
            choices.add(createdChoice);
          }
    }
}