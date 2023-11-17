package adventuregame;

import java.util.ArrayList;

public class Choice {
    int id;
    String text;
    ArrayList<Option> options = new ArrayList<Option>();

    public Choice() {

    }

    public void selectOption(Player p, int optionID) {
        getSelectedoption(optionID).makeSelection(p);
    }
    public Option getSelectedoption(int optionID) {
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getID() == optionID) {
                return options.get(i);
            }
        }
        return null;
    }
}