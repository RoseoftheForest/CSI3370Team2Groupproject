package adventuregame;

import adventuregame.Entity.Player;
import adventuregame.Room.ShopRoom;

public class GameController {
    Game game;

    public GameController() {
        game = Game.instance();
    }
    
    public void startNewGame(int playerID) {
        Player p = game.getPlayer(playerID);
        p.reset();
        game.nextRoom(p);
    }

    public Response useBasicAttack(int playerID) {
        Player p = game.getPlayer(playerID);
        return game.useAttack(p, p.getBasicAttack());
    }
    public Response useSpecialAttack(int playerID) {
        Player p = game.getPlayer(playerID);
        return game.useAttack(p, p.getSpecialAttack());
    }
    
    public Response buyItem(int playerID, int position) {
        Player p = game.getPlayer(playerID);
        Response response = new Response();
        if (p.getCurrentRoom().getClass() != ShopRoom.class) {
            return response;
        }
        ShopRoom room = (ShopRoom) p.getCurrentRoom();
        
        return p.buyItem(room.getItem(position));
    }
    
    public Response nextRoom(int playerID) {
        Player p = game.getPlayer(playerID);
        return game.nextRoom(p);
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
