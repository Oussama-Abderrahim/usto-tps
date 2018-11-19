package events;

import java.io.Serializable;

/**
 * Created by ${} on 19/11/2018.
 */
public class GameEvent implements Serializable
{
    public enum GameEventType {
        GAME_END,
        PLAYER_WON,
    }

    public GameEventType type;
    public String name;
    public String word;

    public GameEvent(GameEventType type, String name, String word)
    {
        this.type = type;
        this.name = name;
        this.word = word;
    }
}