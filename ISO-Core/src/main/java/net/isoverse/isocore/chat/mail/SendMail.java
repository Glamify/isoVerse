package net.isoverse.isocore.chat.mail;

import com.mongodb.client.model.Updates;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import org.bson.conversions.Bson;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class SendMail {

    public SendMail(PlayerData playerData, String message) {
        PlayerDataWrapper[] playerFile = playerData.get();
        ArrayList<MailWrapper> mail = new ArrayList<>();

        for (MailWrapper mailLoop : playerFile[0].getMail()) {
            mail.add(mailLoop);
        }

        mail.add(new MailWrapper(ISOCore.getInstance().getConfig().getString("SERVER"), System.currentTimeMillis(), false, System.currentTimeMillis(), ChatColor.translateAlternateColorCodes('&', message)));

        Bson update = Updates.set("mail", mail);
        playerData.update(update);
    }
}
