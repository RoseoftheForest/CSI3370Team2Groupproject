package adventuregame;

import adventuregame.Entity.Attack;
import adventuregame.Entity.Player;
import adventuregame.Response.Action;
import adventuregame.Room.FightRoom;
import adventuregame.Room.Room;
import adventuregame.Room.ShopRoom;

public class GameController {
    private static Game game;
    private GameView view;
    private static GameController uniqueInstance;

    public static GameController instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GameController();
        }
        return uniqueInstance;
    }

    private GameController() {
        game = Game.instance();
        view = GameView.instance();
        view.setMenuScreen();
        view.start();
    }

    public static void newPlayer(String name) {
        int playerID = game.addPlayer(name);
        startNewGame(playerID);
    }
    
    public static void startNewGame(int playerID) {
        instance().view.setPlayerID(playerID);
        Player p = game.getPlayer(playerID);
        System.out.println("Received start new game interaction from " + p.getName() + " (" +  playerID + ")");
        p.reset();
        GameController.nextRoom(playerID);
        System.out.println("Started new game for " + p.getName() + " (" +  playerID + ")");
        
    }

    private static void useAttack(Player p, Attack a) {
        Response response = game.useAttack(p, a);
        instance().view.setFightRoom((FightRoom)p.getCurrentRoom());
        if (response.getNextAction() == Action.ATTACK) {
            
        } else if (response.getNextAction() == Action.NEXT_ROOM) {
            instance().view.setNextRoom();
        } else if (response.getNextAction() == Action.GAME_OVER) {
            instance().view.setGameOver();
        }
        instance().view.displayMessage(response);
    }
    public static void useBasicAttack(int playerID) {
        Player p = game.getPlayer(playerID);
        System.out.println("Received basic attack interaction from " + p.getName() + " (" +  playerID + ")");
        useAttack(p, p.getBasicAttack());
    }
    public static void useSpecialAttack(int playerID) {
        Player p = game.getPlayer(playerID);
        System.out.println("Received special attack interaction from " + p.getName() + " (" +  playerID + ")");
        useAttack(p, p.getSpecialAttack());
    }
    
    public static void buyItem(int playerID, int position) {
        Player p = game.getPlayer(playerID);
        System.out.println("Received buy item interaction from " + p.getName() + " (" +  playerID + ")");
        if (p.getCurrentRoom().getClass() != ShopRoom.class) {
            return;
        }
        ShopRoom room = (ShopRoom) p.getCurrentRoom();
        
        Response response = p.buyItem(room.getItem(position));
        instance().view.displayMessage(response);
    }
    
    public static void nextRoom(int playerID) {
        Player p = game.getPlayer(playerID);
        System.out.println("Received next room interaction from " + p.getName() + " (" +  playerID + ")");
        Response message = new Response();
        Response res = game.nextRoom(p);
        System.out.println("Depth: " + p.getDepth());
        Room room = p.getCurrentRoom();
        if (res.getNextAction() == Action.ATTACK) {
            message.addMessage("This room contains a monster! You must defeat it to progress.");
            instance().view.setFightRoom((FightRoom)room);
        } else if (res.getNextAction() == Action.SHOP) {
            message.addMessage("You stumbled upon a shop. Select any items you would like to buy or click Next Room to progress.");
            instance().view.setShopRoom((ShopRoom)room);
        } else if (res.getNextAction() == Action.HEAL) {
            message.addMessage("Suddenly, you feel revitalized. Click Next Room to progress.");
            instance().view.setHealRoom();
        } else if (res.getNextAction() == Action.NEXT_ROOM) {
            message.addMessage("Click Next Room to progress.");
            instance().view.setNextRoom();
        } else if (res.getNextAction() == Action.GAME_OVER) {
            message.addMessage("You have been defeated...");
            instance().view.setGameOver();
        } else {
            message.addMessage("error");
        }
        instance().view.displayMessage(message);
    }
    
    

    // TO BE IMPLEMENTED
    public static void selectSettings(int playerID, int settingID, int value) {
        Player p = game.getPlayer(playerID);
        Settings s = game.getSetting(settingID);
        s.setSetting(value);
        p.setSettings(s);
    }
    
    // TO BE IMPLEMENTED
    // Need database functionality
    public static void logIn(int playerID) {
        Player p = game.getPlayer(playerID); // attempt to load player from existing memory
        if (p != null) {
            if (!p.isLoggedIn()) {
                p.logIn();
            }
            return;
        }

        // otherwise, search database for player
        // otherwise, create new player, and save in database
    }
    public static void logOut(int playerID) {
        Player p = game.getPlayer(playerID);
        if (p == null || !p.isLoggedIn()) {return;} // player is not logged in
        p.logOut();
    }
    public void loadGame(int playerID) {
        game.deletePlayer(playerID);
        // load player's data here
        Player player = new Player(playerID, "Placeholder");
        player.setDepth(0);
        player.setMoney(0);
        player.setHealth(0);
        player.setClass(null);
        player.setSettings(null);
        player.setStats(null);
        player.setCurrentRoom(game.getRoom(0));
    }
    public static void saveGame(int playerID) {
        Player player = game.getPlayer(playerID);
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
        game.saveSettings(player);
        game.saveStats(player);
    }

    // TO BE IMPLEMENTED
    // Should return the default settings if the player hasn't modified the settings, or whatever their settings were if they have saved settings.
    public static void loadSettings(int playerID) {
        Player p = game.getPlayer(playerID);
        if (p == null) {return;}
        p.setSettings(new Settings());
    }
}
