package adventuregame;

import adventuregame.Game.BackgroundColor;
import adventuregame.Game.SoundOption;
import adventuregame.Game.TextSizeOption;
import adventuregame.Game.TextSpeedOption;

public class Settings {
    private final static TextSpeedOption DEFAULT_TEXT_SPEED = adventuregame.Game.TextSpeedOption.NORMAL;
    private final static TextSizeOption DEFAULT_TEXT_SIZE = adventuregame.Game.TextSizeOption.MEDIUM;
    private final static SoundOption DEFAULT_VOLUME = adventuregame.Game.SoundOption.VOLUME5;
    private final static BackgroundColor DEFAULT_BACKGROUND_COLOR = adventuregame.Game.BackgroundColor.BLACK;

    TextSpeedOption textSpeed;
    TextSizeOption textSize;
    SoundOption soundOption;
    BackgroundColor backgroundColor;
    
    public Settings() {
        textSpeed = DEFAULT_TEXT_SPEED;
        textSize = DEFAULT_TEXT_SIZE;
        soundOption = DEFAULT_VOLUME;
        backgroundColor = DEFAULT_BACKGROUND_COLOR;
    }

    public TextSpeedOption getTextSpeed() {
        return this.textSpeed;
    }
    public TextSizeOption getTextSize() {
        return this.textSize;
    }
    public SoundOption getVolume() {
        return this.soundOption;
    }
    public BackgroundColor getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setSetting(int value) {

    }
}