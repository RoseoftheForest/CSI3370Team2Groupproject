package adventuregame;

public class Choice {
    int id;
    String text;
    Option[] options;

    public Choice() {

    }

    public void selectOption(Player p, int optionID) {
        getSelectedoption(optionID).makeSelection(p);
    }
    public Option getSelectedoption(int optionID) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].getID() == optionID) {
                return options[i];
            }
        }
        return null;
    }
}