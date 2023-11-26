package adventuregame.Room;

import java.util.ArrayList;

import adventuregame.Item.ShopItem;

public class ShopRoom extends Room {
    private ArrayList<ShopItem> items;
    
    public ShopRoom(int id, int min_depth, int max_depth) {
        super(id, min_depth, max_depth);
        items = new ArrayList<ShopItem>();
    }

    public void addItem(ShopItem i) {
        if (items.size() >= 3) {
            return;
        }
        items.add(i);
    }

    public ShopItem getItem(int position) {
        return items.get(position);
    }

    public int getItemCount() {
        return items.size();
    }
}
