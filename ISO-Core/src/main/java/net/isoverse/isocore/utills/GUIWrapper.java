package net.isoverse.isocore.utills;

import net.isoverse.isocore.utills.listeners.InventoryClickListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;

public class GUIWrapper {

    public Inventory inventory;
    Map<Integer,Button> buttons = new HashMap<Integer,Button>();
    Material background;
    int size;
    String name;
    int currentPage = 0;
    int pages;
    Boolean paged;



    public GUIWrapper(Player player, int size, String name, Material background) {
        init(player,size,name,background,0);
    }

    public GUIWrapper(Player player, int size, String name, Material background, int pages) { //SIZE IS FOR HOW MANY ROWS NOW so use 1, 2, 3 instead of 9, 18, 27
        init(player,size,name,background,pages);
    }

    public void init(Player player, int size, String name, Material background, int pages){
        if(pages<=1) {
            if (size > 6) {
                throw new IllegalArgumentException("A gui has at most 6 rows!");
            }

            paged = false;
            this.pages = pages;
            this.size = size;
            this.name = name;
            this.background = background;

            inventory = Bukkit.createInventory(player, size * 9, Component.text(name));
            InventoryClickListener.openGuis.add(this);
            coverBackGround();
            player.openInventory(inventory);
        }else{
            if (size > 6) {
                throw new IllegalArgumentException("A gui has at most 6 rows!");
            }
            if(size>5){
                throw new IllegalArgumentException("A paged gui needs at least 1 row for the page navigation!");
            }

            paged = true;
            this.pages = pages;
            this.size = size;
            this.name = name;
            this.background = background;

            inventory = Bukkit.createInventory(player, (size+1) * 9, Component.text(name));
            InventoryClickListener.openGuis.add(this);
            coverBackGround();
            player.openInventory(inventory);


            addButton((clickType) -> {
                switchPage(currentPage-1);
            }, Material.ARROW, ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Previous Page",new String[0],-1);
            addButton((clickType) -> {
                switchPage(currentPage+1);
            }, Material.ARROW, ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Next Page",new String[0],-9);
        }
    }

    public void switchPage(int newPage) {
        if(newPage>=0&&newPage<pages) {
            currentPage = newPage + 0; //for copy
            coverBackGround();
            renderButtons();
        }
    }
    public void renderButtons() {
        if(!paged){
            for (int i=0;i<(size*9);i++){
                if(buttons.containsKey(i)){
                    inventory.setItem(i,buttons.get(i).toItemStack());
                }
            }
        }else{
            for (int i =- 1; i >= -9; i--){ //navi bar
                if(buttons.containsKey(i)){
                    inventory.setItem(((i + 1) * -1) + (size * 9), buttons.get(i).toItemStack());
                }
            }

            int offset = (currentPage * (size * 9));
            for (int i = 0; i< (size*9); i++){ // normal buttons
                if(buttons.containsKey(i+offset)){
                    inventory.setItem(i, buttons.get(i+offset).toItemStack());
                }
            }
        }
    }
    public void coverBackGround(){
        ItemStack backgroundItem = new ItemStack(background);
        ItemMeta meta = backgroundItem.getItemMeta();
        meta.displayName(Component.text(""));
        backgroundItem.setItemMeta(meta);
        if(!paged) {
            for (int i = 0; i < size * 9; i++) {
                inventory.setItem(i, backgroundItem);
            }
        }else{
            for (int i = 0; i < (size + 1) * 9; i++) {
                inventory.setItem(i, backgroundItem);
            }
        }
    }
    public void close(){
        inventory.close();
    }
    public void addButton(GuiButtonOnClick function, Material material, String name, String[] lore, int slot) {
        Button button = new Button();
        button.function = function;
        button.material = material;
        button.name = ChatColor.translateAlternateColorCodes('&', name);
        button.lore = Arrays.stream(lore).map(s -> ChatColor.translateAlternateColorCodes('&', s)).toArray(size -> new String[size]);;
        button.slot = slot;
        addButton(button);
    }

    public void addButton(Button button) {
        buttons.put(button.slot,button);
        renderButtons();
    }
    public void clickSlot(int slot, ClickType clickType) throws InterruptedException, IOException {
        if(!paged) {
            if (buttons.containsKey(slot)) {
                buttons.get(slot).function.click(clickType);
            }
        }else{
            if(slot >= size * 9){// is a navigation button
                if (buttons.containsKey(((slot - (size * 9)) + 1) * -1)) {
                    buttons.get(((slot - (size * 9)) + 1) * -1).function.click(clickType);
                }
            }else {
                int offset = (currentPage * (size * 9));
                if (buttons.containsKey(slot + offset)) {
                    buttons.get(slot + offset).function.click(clickType);
                }
            }
        }
    }
    class Button{
        GuiButtonOnClick function;
        Material material;
        String name;
        String[] lore;
        int slot;
        public @Nullable ItemStack toItemStack(){
            ItemStack temp = new ItemStack(material);
            ItemMeta meta = temp.getItemMeta();
            meta.displayName(Component.text(name));
            List<Component> loreList = new ArrayList<Component>();
            for (String loreLine: lore) {
                loreList.add(Component.text(loreLine));
            }
            meta.lore(loreList);
            temp.setItemMeta(meta);
            return temp;
        }
    }
    public interface GuiButtonOnClick {
        void click(ClickType clickType) throws InterruptedException, IOException;
    }
}
