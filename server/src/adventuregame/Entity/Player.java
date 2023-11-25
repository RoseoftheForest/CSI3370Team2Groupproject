package adventuregame.Entity;

import adventuregame.Game;
import adventuregame.Response;
import adventuregame.Settings;
import adventuregame.Game.PlayerClass;
import adventuregame.Item.Item;
import adventuregame.Item.ShopItem;
import adventuregame.Response.Action;
import adventuregame.Room.Room;

public class Player extends Entity {
    private int id;
    private int depth;
    private Game.PlayerClass playerClass;
    private int money;
    private Room currentRoom;
    private Settings settings;
    private boolean authenticated;
    public static Stats DEFAULT_STATS = new Stats(3, 2, 1, 1, 20);



    public Player(int id, String name) {
        super(name, DEFAULT_STATS);
        this.id = id;
        setDepth(0);
        setMoney(0);
        setClass(PlayerClass.FIGHTER);
        setSettings(new Settings());
        this.authenticated = false;
        setCurrentRoom(null);
    }

    public int getID() {
        return this.id;
    }
    public int getMoney() {
        return this.money;
    }
    public int getDepth() {
        return this.depth;
    }
    public Settings getSettings() {
        return this.settings;
    }
    public PlayerClass getPlayerClass() {
        return this.playerClass;
    }
    public Room getCurrentRoom() {
        return this.currentRoom;
    }
    public boolean isLoggedIn() {
        return this.authenticated;
    }

    public void addMoney(int money) {
        this.money += money;
    }
    public void incrementDepth() {
        this.depth += 1;
    }
    public void logIn() {
        this.authenticated = true;
    }
    public void logOut() {
        this.authenticated = false;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public void setClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    
    
    public Response buyItem(ShopItem item) {
        Response response = new Response();
        response.setNextAction(Action.SHOP);
        if (getMoney() < item.getCost()) {
            response.addMessage("You don't have enough money to buy that!");
            response.setResult(false);
            return response;
        }
        this.money -= item.getCost();
        response.combineResponse(item.buy());
        if (!response.getResult()) {
            return response;
        }
        collectItem(item);
        return response;
    }

    public void collectItem(Item item) {
        if (item == null) {
            return;
        }
        getStats().applyModifiers(item.getModifiers());
    }

    public Response defeatMonster(Monster monster) throws Exception {
        Response response = new Response();
        int droppedMoney = monster.getDroppedMoney();
        addMoney(droppedMoney);
        response.addMessage(monster.getName() + " dropped " + droppedMoney + " coins. You now have " + getMoney() + " coins");
        Game game = Game.instance();
        Item droppedItem = game.getItem(monster);
        collectItem(droppedItem);
        response.addMessage(monster.getName() + " dropped a " + droppedItem.getName() + " and gave you " + droppedItem.stringifyModifiers());
        return response;

        // move to Game
        // collectItem(monster.dropItem());
    }

    public void reset() {
        this.depth = 0;
        this.money = 0;
        super.setStats(DEFAULT_STATS);
        super.heal();
    }

}
