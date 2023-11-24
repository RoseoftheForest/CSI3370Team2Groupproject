package adventuregame.Item;

import adventuregame.Response;
import adventuregame.Entity.Stats;

public class ShopItem extends Item {
    private int cost;
    private int quantity;

    public ShopItem(int id, String name, String description, Stats modifiers, int cost) {
        super(id, name, description, modifiers);
        this.cost = cost;
        this.quantity = 1;
    }

    public int getCost() {
        return this.cost;
    }
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Response buy() {
        Response response = new Response();
        if (getQuantity() < 1) {
            response.addMessage("There are no more " + getName() + " left!");
            response.setResult(false);
            return response;
        }
        setQuantity(getQuantity()-1);
        response.addMessage("You purchased a " + getName());
        response.addMessage("You gained " + stringifyModifiers());
        response.setResult(true);
        return response;
    }
    
}
