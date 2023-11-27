package adventuregame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.json.*;

import adventuregame.Entity.Attack;
import adventuregame.Entity.Monster;
import adventuregame.Entity.Player;
import adventuregame.Entity.Stats;
import adventuregame.Item.Item;
import adventuregame.Item.ShopItem;
import adventuregame.Response.Action;
import adventuregame.Room.FightRoom;
import adventuregame.Room.HealRoom;
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
    public final static int MAX_TIER = 3;
    public final static int MIN_TIER = 1;


    private ArrayList<Room> rooms;
    private ArrayList<Player> players;
    private ArrayList<Item> tier1Items;
    private ArrayList<Item> tier2Items;
    private ArrayList<Item> tier3Items;
    private ArrayList<Monster> monsters;
    private static Game uniqueInstance = null;
    
    public static Game instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Game();
        }
        return uniqueInstance;
    }

    private Game() {
        players = new ArrayList<Player>();
        // initialize items, monsters, and rooms
        monsters = new ArrayList<Monster>();
        rooms = new ArrayList<Room>();
        tier1Items = new ArrayList<Item>();
        tier2Items = new ArrayList<Item>();
        tier3Items = new ArrayList<Item>();
        
        JsonObject monsterObject = loadJson("server/src/monsters.json");
        //Gets the monsters array from the json
        JsonArray monsterArray = monsterObject.getJsonArray("monsters");
        // Load the monsters from the array
        loadMonsters(monsterArray);


        JsonObject itemsObject = loadJson("server/src/items.json");
        JsonArray itemsArray = itemsObject.getJsonArray("items");
        loadItems(itemsArray);


        JsonObject roomsObject = loadJson("server/src/rooms.json");
        //Gets the rooms array from the json
        JsonArray roomsArray = roomsObject.getJsonArray("rooms");
        JsonObject roomTypes = roomsObject.getJsonObject("types");
        loadRooms(roomsArray, roomTypes);
    }

    private JsonObject loadJson(String filePath) {
        InputStream file;
        try {
            file = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        JsonReader reader = Json.createReader(file);
        
        JsonObject obj = reader.readObject();
        reader.close();
        // Attempt to close the file
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void loadMonsters(JsonArray monsterArray) {
        int phyAtk;
        int mgcAtk;
        int phyDef;
        int mgcDef;
        int maxHealth;
        Stats monsterStats;
        Monster monster;
        int id;
        String name;
        String desc;
        int tier;
        int minMoney;
        int maxMoney;
        
        for (int i = 0; i < monsterArray.size(); i++) {
            //Creating a new ArrayList instance each time is necessary as clear() leads to bugs
            ArrayList<Integer> stats = new ArrayList<>();

            //Gets the monster and room object at index i
            JsonObject monsterObj = monsterArray.getJsonObject(i);

            //Gets the stats array for the monster at index i
            JsonArray statsArray = monsterObj.getJsonArray("stats");

            //Loop to add stats to array list
            for (int j = 0; j < statsArray.size(); j++) {
                stats.add(statsArray.getInt(j));
            }

            //Extract the individual stats to be put into a Stats object
            phyAtk = stats.get(0);
            mgcAtk = stats.get(1);
            phyDef = stats.get(2);
            mgcDef = stats.get(3);
            maxHealth = stats.get(4);

            //Put the extracted stats into a stats object
            monsterStats = new Stats(phyAtk, mgcAtk, phyDef, mgcDef, maxHealth);

            id = monsterObj.getInt("id");
            name = monsterObj.getString("name");
            desc = monsterObj.getString("description");
            minMoney = monsterObj.getInt("minMoney");
            maxMoney = monsterObj.getInt("maxMoney");
            tier = monsterObj.getInt("tier");

            //Create new monster and add it to array list
            monster = new Monster(id, name, desc, monsterStats, minMoney, maxMoney, tier);

            monsters.add(monster);
            System.out.println("Monster: " + name + "(" + id + ") loaded.\n\tDescription: " + desc + "\n\tStats: " + monsterStats.stringify("\n\t"));
        }
    }
    private void loadRooms(JsonArray roomsArray, JsonObject roomTypes) {
        int typeID;
        String type;
        int id;
        int minDepth;
        int maxDepth;
        for (int i = 0; i < roomsArray.size(); i++) {
            //Gets the room object at index i
            JsonObject roomObj = roomsArray.getJsonObject(i);

            id = roomObj.getInt("id");
            minDepth = roomObj.getInt("minDepth");
            maxDepth = roomObj.getInt("maxDepth");
            typeID = roomObj.getInt("type");
            type = roomTypes.getString("" + typeID);

            if (type.equals(FightRoom.class.getSimpleName())) {
                int monsterID = roomObj.getInt("monster");
                Monster monster = getMonster(monsterID).deepCopy();
                FightRoom fightRoom = new FightRoom(monsterID, minDepth, maxDepth, monster);
                System.out.println("Loaded FightRoom: \n\tID: " + id + " | minDepth: " + minDepth + " | maxDepth: " + maxDepth + "\n\tMonster: " + monster.getName() + " (" + monsterID + ")");
                rooms.add(fightRoom);
                System.out.println("Added FightRoom (" + id + ") to Game\n");
            } else if (type.equals(ShopRoom.class.getSimpleName())) {
                ShopRoom shopRoom = new ShopRoom(id, minDepth, maxDepth);
                System.out.println("Loaded ShopRoom.\n\tID: " + id + " | minDepth: " + minDepth + " | maxDepth: " + maxDepth);
                JsonArray items = roomObj.getJsonArray("items");
                for (int j = 0; j < items.size(); j++) {
                    JsonObject itemObj = items.getJsonObject(j);
                    int itemID = itemObj.getInt("id");
                    int itemCost = itemObj.getInt("cost");
                    int itemQty = itemObj.getInt("quantity");
                    Item item = getItem(itemID);
                    ShopItem shopItem = new ShopItem(itemID, item.getName(), item.getDescription(), item.getModifiers(), itemCost);
                    shopItem.setQuantity(itemQty);
                    System.out.println("Loaded ShopItem:\n\t" + "Item: " + item.getName() + " (" + itemID + ") | Cost: " + itemCost + " | Quantity: " + itemQty);
                    shopRoom.addItem(shopItem);
                    System.out.println("Added ShopItem (" + itemID + ") to ShopRoom");
                }
                rooms.add(shopRoom);
                System.out.println("Added ShopRoom (" + id + ") to Game");
                
            } else if (type.equals(HealRoom.class.getSimpleName())) {
                HealRoom healRoom = new HealRoom(id, minDepth, maxDepth);
                System.out.println("Loaded HealRoom.\n\tID: " + id + " | minDepth: " + minDepth + " | maxDepth: " + maxDepth);
                rooms.add(healRoom);
                System.out.println("Added HealRoom (" + id + ") to Game");
            }
        }
    }
    private void loadItems(JsonArray itemsArray) {
        int phyAtk;
        int mgcAtk;
        int phyDef;
        int mgcDef;
        int maxHealth;
        Stats itemModifiers;
        int itemID;
        String itemName;
        String itemDesc;
        //Loop to add items from json to array list
        for (int i = 0; i < itemsArray.size(); i++) {
            //Gets the item object at index i
            JsonObject itemObj = itemsArray.getJsonObject(i);
            
            //Gets the modifiers array for the item at index i
            JsonArray modifierArray = itemObj.getJsonArray("modifiers");

            //Extract the individual modifiers to be put into a Stats object
            phyAtk = modifierArray.getInt(0);
            mgcAtk = modifierArray.getInt(1);
            phyDef = modifierArray.getInt(2);
            mgcDef = modifierArray.getInt(3);
            maxHealth = modifierArray.getInt(4);

            //Put the extracted modifiers into a stats object
            itemModifiers = new Stats(phyAtk, mgcAtk, phyDef, mgcDef, maxHealth);

            itemID = itemObj.getInt("id");
            itemName = itemObj.getString("name");
            itemDesc = itemObj.getString("description");

            Item item = new Item(itemID, itemName, itemDesc, itemModifiers);
            int tier = itemObj.getInt("tier");
            //Create new item based on tier and add it to appropriate array list
            if (tier == 1) {
                tier1Items.add(item);
            } else if (tier == 2) {
                tier2Items.add(item);
            } else if (tier == 3) {
                tier3Items.add(item);
            } else {
                tier1Items.add(item);
            }
            System.out.println("Loaded Tier " + tier + " Item: " + itemName + " (" + itemID + ")\n\tDescription: " + itemDesc + "\n\tModifiers: " + itemModifiers.stringify("\n\t"));
            //System.out.println("Item of tier " + item.getInt("tier") + " has been added to the list. Its name is " + item.getString("name"));
        }
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

    public Response nextRoom(Player player) {
        Response response = new Response();
        player.incrementDepth();
        Room nextRoom = getValidRoom(player.getDepth()).deepCopy();
        player.setCurrentRoom(nextRoom);
        if (nextRoom.getClass() == FightRoom.class) {
            response.setNextAction(Action.ATTACK);
        } else if (nextRoom.getClass() == ShopRoom.class) {
            response.setNextAction(Action.SHOP);
        } else if (nextRoom.getClass() == HealRoom.class) {
            response.setNextAction(Action.HEAL);
            player.heal();
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

    public Monster getMonster(int id) {
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getID() == id) {
                return monsters.get(i);
            }
        }
        return monsters.get(0);
    }

    public Item getItem(int id) {
        for (int i = 1; i <= MAX_TIER; i++) {
            List<Item> items = getItemsByTier(i);
            for (int j = 0; j < items.size(); j++) {
                if (items.get(j).getID() == id) {
                    return items.get(j);
                }
            }
        }
        return getItemsByTier(1).get(0);
    }
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


    public Response useAttack(Player p, Attack a) {
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


    // TO BE IMPLEMENTED
    public Settings getSetting(int setting) {
        return new Settings();
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

}
