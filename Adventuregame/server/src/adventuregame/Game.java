package adventuregame;

import java.util.ArrayList;

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

    // TO BE IMPLEMENTED
    public static Choice getChoice(int id) {
        return new Choice();
    }

    // TO BE IMPLEMENTED
    // Should return the default settings if the player hasn't modified the settings, or whatever their settings were if they have saved settings.
    public static Settings loadSettings(String playerID) {
        return new Settings();
    }

    public static void loadGame(Player p) {
        startNewGame(p);
    }
    public static void startNewGame(Player p) {
        p.scores = new Scores();
        p.setChoice(choices.get(0)); // set the players next choice to the first choice
    }
}