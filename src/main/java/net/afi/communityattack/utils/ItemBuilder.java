package net.afi.communityattack.utils;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemstack;
    private ItemMeta meta;
    private List<String> lore;

    public ItemBuilder(Material material){

        itemstack = new ItemStack(material);
        meta = itemstack.getItemMeta();
        lore = new ArrayList<String>();
    }

    public ItemBuilder(Material material, int amount){

        itemstack = new ItemStack(material, amount);
        meta = itemstack.getItemMeta();
    }

    public ItemBuilder setAmount(int amount){
        itemstack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName){
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        this.lore = lore;
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData){
        meta.setCustomModelData(customModelData);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable){
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setType(Material type){
        itemstack.setType(type);
        return this;
    }

    public ItemStack build(){
        meta.setLore(lore);
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public ItemBuilder addLoreLine(String line){
        lore.add(line);
        return this;
    }

    @Nullable
    public ItemBuilder setOwner(OfflinePlayer player){

        if(!(meta instanceof SkullMeta))return null;
        SkullMeta sm = (SkullMeta) meta;
        sm.setOwningPlayer(player);
        meta = sm;
        return this;
    }
}
