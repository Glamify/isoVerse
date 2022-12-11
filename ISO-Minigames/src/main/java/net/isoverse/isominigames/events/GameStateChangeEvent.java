package net.isoverse.isominigames.events;

import net.isoverse.isominigames.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String game;
    private GameState to;

    public GameStateChangeEvent(String game, GameState to) {
        game = game;
        to = to;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String GetGame() {
        return game;
    }

    public GameState GetState() {
        return to;
    }
}