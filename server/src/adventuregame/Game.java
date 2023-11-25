package adventuregame;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.json.*;

import adventuregame.Entity.Attack;
import adventuregame.Entity.Damage;
import adventuregame.Entity.Monster;
import adventuregame.Entity.Player;
import adventuregame.Entity.Stats;
import adventuregame.Item.Item;
import adventuregame.Response.Action;
import adventuregame.Room.FightRoom;
import adventuregame.Room.Room;
import adventuregame.Room.ShopRoom;

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
    public static int MAX_TIER = 3;
    public static int MIN_TIER = 1;


    private ArrayList<Room> rooms;
    private ArrayList<Player> players;
    private ArrayList<Item> tier1Items;
    private ArrayList<Item> tier2Items;
    private ArrayList<Item> tier3Items;
    private ArrayList<Monster> monsters;
    private static Game uniqueInstance = null;
    

    public static Game instance() throws Exception {
        if (uniqueInstance == null) {
            uniqueInstance = new Game();
        }
        return uniqueInstance;
    }

    private Game() throws Exception {
        // initialize items, monsters, and rooms
        monsters = new ArrayList<Monster>();
        rooms = new ArrayList<Room>();
        
        //Reads the json files
        InputStream monsterFile = new FileInputStream("server/src/monsters.json");
        InputStream roomFile = new FileInputStream("server/src/rooms.json");
        JsonReader monsterReader = Json.createReader(monsterFile);
        JsonReader roomReader = Json.createReader(roomFile);
        JsonObject monsterObject = monsterReader.readObject();
        JsonObject roomObject = roomReader.readObject();
        monsterReader.close();
        roomReader.close();
        
        //Gets the monsters and rooms arrays from the json
        JsonArray monsterArray = monsterObject.getJsonArray("monsters");
        JsonArray roomsArray = roomObject.getJsonArray("rooms");

        //Local variables used to hold read information from json
        Monster monsterToAdd;
        Room roomToAdd;
        Stats statsToAdd;
        int phyAtkToAdd;
        int magAtkToAdd;
        int phyDefToAdd;
        int magicDefToAdd;
        int healthToAdd;

        //Loop to add monsters and rooms from json to array lists
        /*
         Note: Merging both the rooms and monster initializations into one for loop only works because there are the same number
         of monsters as there are rooms. As soon as the numbers differ from each other, there will need to be a separate loop
         for the rooms, but the process will be the same.
         */
        for (int i = 0; i < monsterArray.size(); i++) {
            //Creating a new ArrayList instance each time is necessary as clear() leads to bugs
            ArrayList<Integer> stats = new ArrayList<>();

            //Gets the monster and room object at index i
            JsonObject monster = monsterArray.getJsonObject(i);
            JsonObject room = roomsArray.getJsonObject(i);

            //Gets the stats array for the monster at index i
            JsonArray statsArray = monster.getJsonArray("stats");

            //Loop to add stats to array list
            for (int j = 0; j < statsArray.size(); j++) {
                stats.add(statsArray.getInt(j));
            }

            //Extract the individual stats to be put into a Stats object
            phyAtkToAdd = stats.get(0);
            magAtkToAdd = stats.get(1);
            phyDefToAdd = stats.get(2);
            magicDefToAdd = stats.get(3);
            healthToAdd = stats.get(4);

            //Put the extracted stats into a stats object
            statsToAdd = new Stats(phyAtkToAdd, magAtkToAdd, phyDefToAdd, magicDefToAdd, healthToAdd);

            //Create new monster and add it to array list
            monsterToAdd = new Monster(monster.getString("name"), monster.getString("description"), statsToAdd, monster.getInt("minMoney"), monster.getInt("maxMoney"), monster.getInt("tier"));
            monsters.add(monsterToAdd);
            System.out.println("A monster has been added to the list");

            //Create new room and add it to array list
            roomToAdd = new FightRoom(room.getInt("id"), room.getInt("minDepth"), room.getInt("maxDepth"), monsters.get(i));
            rooms.add(roomToAdd);
        }

        tier1Items = new ArrayList<Item>();
        Item item = new Item(1, "Item1", "", new Stats(0, 0, 0, 0, 100));
        tier2Items = new ArrayList<Item>();
        Item item2 = new Item(2, "BIG ITEM", "it's big", new Stats(0, 0, 0, 0, 200));
        tier1Items.add(item);
        tier1Items.add(item2);

        players = new ArrayList<Player>();
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
    public void deletePlayer(int playerID) {
        players.removeIf(p -> (p.getID() == playerID));
    }

    public void startNewGame(int playerID) {
        Player p = getPlayer(playerID);
        p.reset();
        nextRoom(playerID);
    }
    
    // TO BE IMPLEMENTED
    // Need database functionality
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
    // may need to remove Settings argument in favor of json
    public void selectSettings(int playerID, Settings settings) {
        Player p = getPlayer(playerID);
        if (p == null) {return;}
        p.setSettings(settings);
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
    
    // TO BE IMPLEMENTED
    // Should return the default settings if the player hasn't modified the settings, or whatever their settings were if they have saved settings.
    public void loadSettings(int playerID) {
        Player p = getPlayer(playerID);
        if (p == null) {return;}
        p.setSettings(new Settings());
    }
    

    // public void dropItem(Monster monster, Player player) {
    //     List<Item> items = getItemsByTier(monster.getTier());
    //     Random rand = new Random();
    //     Item item = items.get(rand.nextInt(items.size()));
    //     player.collectItem(item);
    // }

    public Item getItem(Monster monster) {
        List<Item> items = getItemsByTier(monster.getTier());
        Random rand = new Random();
        return items.get(rand.nextInt(items.size()));
    }

    public List<Item> getItemsByTier(int tier) {
        if (tier == 3) {
            return tier3Items;
        } else if (tier == 2) {
            return tier2Items;
        } else {
            return tier1Items;
        }
    }

    public Response useBasicAttack(int playerID) throws Exception {
        Player p = getPlayer(playerID);
        return useAttack(p, p.getBasicAttack());
    }
    public Response useSpecialAttack(int playerID) throws Exception {
        Player p = getPlayer(playerID);
        return useAttack(p, p.getSpecialAttack());
    }

    private Response useAttack(Player p, Attack a) throws Exception {
        Response response = new Response();
        if (p.getCurrentRoom().getClass() != FightRoom.class) {
            return response;
        }
        FightRoom room = (FightRoom) p.getCurrentRoom();
        Monster monster = room.getMonster();
        if (a == p.getBasicAttack()) {
            response = p.useBasicAttack(monster);
        } else if (a == p.getSpecialAttack()) {
            response = p.useSpecialAttack(monster);
        } else {
            return response;
        }
        if (!monster.isAlive()) {
            response.combineResponse(p.defeatMonster(monster));
            response.setNextAction(Action.NEXT_ROOM);
            // dropItem(monster, p);
            return response;
        }

        response.combineResponse(monster.attack(p));
        if (!p.isAlive()) {
            response.combineResponse(gameOver(p));
        } else {
            response.setNextAction(Action.ATTACK);
        }
        return response;
    }

    private Response gameOver(Player p) {
        Response response = new Response();
        response.addMessage("You died... Press Restart to try a new dungeon from the beginning.");
        response.setNextAction(Action.GAME_OVER);
        return response;
    }

    public void restart(int playerID) {
        Player p = getPlayer(playerID);
        p.reset();
        // NEEDS MORE IMPLEMENTATION
    }

    // public void basicAttack(int playerID) {
    //     Player p = getPlayer(playerID);
    //     if (p.getCurrentRoom().getClass() != FightRoom.class) {
    //         return;
    //     }
    //     FightRoom room = (FightRoom) p.getCurrentRoom();
        
    //     int playerDamage = p.useBasicAttack(room.getMonster());
    //     System.out.println("Damage: " + playerDamage);
    //     if (!room.getMonster().isAlive()) {
    //         p.defeatMonster(room.getMonster());
    //         dropItem(room.getMonster(), p);
    //         // send defeat monster response
    //         return;
    //     }
    //     Damage monsterAttack = room.getMonster().attack(p);
    //     int monsterDamage = monsterAttack.getAmount();
    //     String attackName = monsterAttack.getName();
    //     if (!p.isAlive()) {
    //         System.out.println("Game Over.");
    //         p.reset();
    //         // send game over
    //     }
    //     System.out.println("Damage: " + monsterDamage + " | Attack: " + attackName);
    //     // send the attack that was used by the monster, the damage it did, and the new health of player and monster
    // }
    // public void specialAttack(int playerID) {
    //     Player p = getPlayer(playerID);
    //     if (p.getCurrentRoom().getClass() != FightRoom.class) {
    //         return;
    //     }
    //     FightRoom room = (FightRoom) p.getCurrentRoom();
        
    //     int playerDamage = p.useSpecialAttack(room.getMonster());
    //     System.out.println("Damage: " + playerDamage);
    //     if (!room.getMonster().isAlive()) {
    //         p.defeatMonster(room.getMonster());
    //         dropItem(room.getMonster(), p);
    //         // send defeat monster response
    //         return;
    //     }

    //     Damage monsterAttack = room.getMonster().attack(p);
    //     int monsterDamage = monsterAttack.getAmount();
    //     String attackName = monsterAttack.getName();
    //     if (!p.isAlive()) {
    //         System.out.println("Game Over.");
    //         p.reset();
    //         // send game over
    //     }
    //     System.out.println("Damage: " + monsterDamage + " | Attack: " + attackName);

    //     // send response
    // }
    public Response buyItem(int playerID, int position) {
        Player p = getPlayer(playerID);
        Response response = new Response();
        if (p.getCurrentRoom().getClass() != ShopRoom.class) {
            return response;
        }
        ShopRoom room = (ShopRoom) p.getCurrentRoom();
        
        return p.buyItem(room.getItem(position));
    }

    public Response nextRoom(int playerID) {
        Player player = getPlayer(playerID);
        Response response = new Response();
        player.incrementDepth();
        Room nextRoom = getValidRoom(player.getDepth()).deepCopy();
        player.setCurrentRoom(getValidRoom(player.getDepth()).deepCopy());
        if (nextRoom.getClass() == FightRoom.class) {
            response.setNextAction(Action.ATTACK);
        } else if (nextRoom.getClass() == ShopRoom.class) {
            response.setNextAction(Action.SHOP);
        }
        return response;
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
