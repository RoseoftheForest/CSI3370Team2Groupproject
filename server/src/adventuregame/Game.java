package adventuregame;

import java.util.ArrayList;
import java.util.Random;

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
    public enum PlayerClass {
        FIGHTER
    }

    private ArrayList<Room> rooms;
    private ArrayList<Player> players;
    
    public void logIn(int playerID) {
        Player p = getPlayer(playerID); // attempt to load player from existing memory
        if (p != null) {
            if (!p.isLoggedIn()) {
                p.logIn();
            }
            return;
        }

        // otherwise, search database for player
        // otherwise, create new player, and save in database
    }
    public void logOut(int playerID) {
        Player p = getPlayer(playerID);
        if (p == null || !p.isLoggedIn()) {return;} // player is not logged in
        p.logOut();
    }

    }

    // TO BE IMPLEMENTED
    // Should return the default settings if the player hasn't modified the settings, or whatever their settings were if they have saved settings.
    public static void loadSettings(Player p) {
        p.setSettings(new Settings());
    }

    public static void loadGame(Player p) {
        startNewGame(p);
    }

    public void nextRoom(Player player) {
        player.incrementDepth();
        player.setCurrentRoom(getValidRoom(player.getDepth()));
    }

    public Room getValidRoom(int depth) {
        if (rooms.size() < 1) {
            return null;
        }
        
        ArrayList<Room> validRooms = new ArrayList<Room>();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).isWithinDepth(depth)) {
                validRooms.add(rooms.get(i));
            }
        }
        if (validRooms.size() < 1) {
            return rooms.get(0); // if there were no valid rooms, send to room 0
        }
        Random rand = new Random();
        return validRooms.get(rand.nextInt(validRooms.size()));
    }

    public Room getRoom(int id) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getID() == id) {
                return rooms.get(i);
            }
        }
        return rooms.get(0);
    }
}
