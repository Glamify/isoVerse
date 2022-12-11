package net.isoverse.isocore.stats.rewards;

import org.bukkit.entity.Player;

import java.util.function.Consumer;


public class ConsumerReward implements Reward {

    private final Consumer<Player> consumer;

    public ConsumerReward(Consumer<Player> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void claim(Player player) {
        consumer.accept(player);
    }
}
