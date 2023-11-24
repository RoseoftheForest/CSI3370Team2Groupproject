package adventuregame;

import java.util.ArrayList;

public class Response {
    private ArrayList<String> messages;
    private boolean result;
    private Action nextAction;

    public static enum Action {
        GAME_OVER, ATTACK, NEXT_ROOM, SHOP
    }

    public Response() {
        this.messages = new ArrayList<String>();
        this.result = false;
    }

    public void addMessage(String message) {
        getMessages().add(message);
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }
    public Action getNextAction() {
        return this.nextAction;
    }
    public String nextMessage() {
        String message = getMessages().getFirst();
        getMessages().removeFirst();
        return message;
    }
    public boolean getResult() {
        return this.result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public void setNextAction(Action action) {
        this.nextAction = action;
    }
    public boolean hasNext() {
        return (getMessages().size() > 0);
    }

    public void combineResponse(Response response) {
        while (response.hasNext()) {
            addMessage(response.nextMessage());
        }
        if (getNextAction() == null) {
            setNextAction(response.getNextAction());
        }
        setResult(getResult() && response.getResult());
    }
}
