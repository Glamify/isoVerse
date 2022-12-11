package net.isoverse.isocore.spells;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.isoverse.isocore.ISOCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpellsManager {

    public SpellsManager(SpellType spellType, String player) {
        Player firstPlayer = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(spellType.name());
        out.writeUTF(player);
        firstPlayer.sendPluginMessage(ISOCore.getInstance(), "iso:spells", out.toByteArray());
    }

    public SpellsManager(SpellType spellType, String player, String target) {
        Player firstPlayer = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(spellType.name());
        out.writeUTF(player);
        out.writeUTF(target);
        firstPlayer.sendPluginMessage(ISOCore.getInstance(), "iso:spells", out.toByteArray());
    }

}
