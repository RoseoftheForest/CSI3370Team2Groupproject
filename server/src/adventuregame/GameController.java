package adventuregame;

import adventuregame.Entity.Player;
import adventuregame.Response.Action;
import adventuregame.Room.FightRoom;
import adventuregame.Room.Room;
import adventuregame.Room.ShopRoom;

public class GameController {
    Game game;
    GameView view;

    public GameController() {
        game = Game.instance();
        view = new GameView();
        view.start();
    }
    
    public void startNewGame(int playerID) {
        Player p = game.getPlayer(playerID);
        p.reset();
        game.nextRoom(p);
    }

    public void useBasicAttack(int playerID) {
        Player p = game.getPlayer(playerID);
        Response response = game.useAttack(p, p.getBasicAttack());
        view.displayMessage(response);
    }
    public void useSpecialAttack(int playerID) {
        Player p = game.getPlayer(playerID);
        Response response = game.useAttack(p, p.getSpecialAttack());
        view.displayMessage(response);
    }
    
    public void buyItem(int playerID, int position) {
        Player p = game.getPlayer(playerID);
        if (p.getCurrentRoom().getClass() != ShopRoom.class) {
            return;
        }
        ShopRoom room = (ShopRoom) p.getCurrentRoom();
        
        Response response = p.buyItem(room.getItem(position));
        view.displayMessage(response);
    }
    
    public void nextRoom(int playerID) {
        Player p = game.getPlayer(playerID);
        Response message = new Response();
        Response res = game.nextRoom(p);
        Room room = p.getCurrentRoom();
        if (res.getNextAction() == Action.ATTACK) {
            message.addMessage("This room contains a monster! You must defeat it to progress.");
            view.setFightRoom((FightRoom)room);
        } else if (res.getNextAction() == Action.SHOP) {
            message.addMessage("You stumbled upon a shop. Select any items you would like to buy or click Next Room to progress.");
            view.setShopRoom((ShopRoom)room);
        } else if (res.getNextAction() == Action.HEAL) {
            message.addMessage("Suddenly, you feel revitalized. Click Next Room to progress.");
            view.setHealRoom();
        } else if (res.getNextAction() == Action.NEXT_ROOM) {
            message.addMessage("Click Next Room to progress.");
            view.setNextRoom();
        } else if (res.getNextAction() == Action.GAME_OVER) {
            message.addMessage("You have been defeated...");
            view.setGameOver();
        } else {
            message.addMessage("error");
        }
        view.displayMessage(message);
    }
    
    

    // TO BE IMPLEMENTED
    public void selectSettings(int playerID, int settingID, int value) {
        Player p = game.getPlayer(playerID);
        Settings s = game.getSetting(settingID);
        s.setSetting(value);
        p.setSettings(s);
    }
    
    // TO BE IMPLEMENTED
    // Need database functionality
    public void logIn(int playerID) {
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
    public void logOut(int playerID) {
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
    public void saveGame(int playerID) {
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
    public void loadSettings(int playerID) {
        Player p = game.getPlayer(playerID);
        if (p == null) {return;}
        p.setSettings(new Settings());
    }
}
