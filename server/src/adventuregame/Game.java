package adventuregame;

import java.util.ArrayList;
import java.util.Random;

import adventuregame.Entity.Player;
import adventuregame.Entity.Stats;
import adventuregame.Room.Room;

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
    private static Game uniqueInstance = null;
    

    public static Game instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Game();
        }
        return uniqueInstance;
    }

    private Game() {
        // initialize items, monsters, and rooms
        monsters = new ArrayList<Monster>();
        monsters.add(new Monster("Monster1", "", new Stats(2, 5, 1, 1, 100), 1, 2, 1));
        monsters.add(new Monster("Monster2", "", new Stats(2, 2, 2, 1, 200), 5, 10, 1));
        rooms = new ArrayList<Room>();
        Room room = new FightRoom(1, 1, 5, monsters.get(0));
        Room room2 = new FightRoom(2, 1, 5, monsters.get(1));
        rooms.add(room);
        rooms.add(room2);

        tier1Items = new ArrayList<Item>();
        Item item = new Item(1, "Item1", "", new Stats(0, 0, 0, 0, 100));
        tier2Items = new ArrayList<Item>();
        Item item2 = new Item(2, "BIG ITEM", "it's big", new Stats(0, 0, 0, 0, 200));
        tier1Items.add(item);
        tier1Items.add(item2);

        players = new ArrayList<Player>();
    }

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

    // may need to remove Settings argument in favor of json
    public void selectSettings(int playerID, Settings settings) {
        Player p = getPlayer(playerID);
        if (p == null) {return;}
        p.setSettings(settings);
    }

    // TO BE IMPLEMENTED
    // Should return the default settings if the player hasn't modified the settings, or whatever their settings were if they have saved settings.
    public void loadSettings(int playerID) {
        Player p = getPlayer(playerID);
        if (p == null) {return;}
        p.setSettings(new Settings());
    }

    public Player getPlayer(int playerID) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getID() == playerID) {
                return players.get(i);
            }
        }
        return null;
    }

    public void addPlayer(String name) {
        players.add(new Player(players.size()+1,name));
    }


    public void startNewGame(int playerID) {
        Player p = getPlayer(playerID);
        p.reset();
        nextRoom(playerID);
    }
    public void loadGame(int playerID) {
        deletePlayer(playerID);
        // load player's data here
        Player player = new Player(playerID, "Placeholder");
        player.setDepth(0);
        player.setMoney(0);
        player.setHealth(0);
        player.setClass(null);
        player.setSettings(null);
        player.setStats(null);
        player.setCurrentRoom(getRoom(0));
    }

    public void saveGame(int playerID) {
        Player player = getPlayer(playerID);
        if (player == null) {
            return;
        }
        String name = player.getName();
        int depth = player.getDepth();
        int money = player.getMoney();
        int health = player.getHealth();
        int playerClass = player.getPlayerClass().ordinal();
        int currentRoomID = player.getCurrentRoom().getID();
        // save player data here
        saveSettings(player);
        saveStats(player);
    }
    public void saveSettings(Player player) {
        Settings settings = player.getSettings();
        int backgroundColor = settings.getBackgroundColor().ordinal();
        int textSize = settings.getTextSize().ordinal();
        int textSpeed = settings.getTextSpeed().ordinal();
        int volume = settings.getVolume().ordinal();
        int playerID = player.getID();
        // save settings here
    }
    public void saveStats(Player player) {
        Stats stats = player.getStats();
        int maxHealth = stats.getMaxHealth();
        int phyAtk = stats.getPhyAtk();
        int magAtk = stats.getMagicAtk();
        int phyDef = stats.getPhyDef();
        int magDef = stats.getMagicDef();
        int playerID = player.getID();
        // save stats in database for the player
    }

    public void deletePlayer(int playerID) {
        players.removeIf(p -> (p.getID() == playerID));
    }

    public void dropItem(Monster monster, Player player) {
        List<Item> items = getItemsByTier(monster.getTier());
        Random rand = new Random();
        Item item = items.get(rand.nextInt(items.size()));
        player.collectItem(item);
    }
    public void nextRoom(int playerID) {
        Player player = getPlayer(playerID);
        player.incrementDepth();
        player.setCurrentRoom(getValidRoom(player.getDepth()).deepCopy());
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
