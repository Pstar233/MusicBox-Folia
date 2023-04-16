package ru.spliterash.musicbox.minecraft.nms.jukebox.versions;

import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import org.bukkit.block.Jukebox;
import org.bukkit.craftbukkit.v1_19_R1.block.CraftJukebox;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import ru.spliterash.musicbox.minecraft.nms.jukebox.IJukebox;

public class V19_2 implements IJukebox {
    private final JukeboxBlockEntity tileEntity;

    public V19_2(Jukebox jukebox) {
        CraftJukebox craft = (CraftJukebox) jukebox;
        tileEntity = craft.getTileEntity();
    }

    public void setJukebox(ItemStack item) {
        net.minecraft.world.item.ItemStack converted = CraftItemStack.asNMSCopy(item);
        tileEntity.setRecord(converted);

    }

    public ItemStack getJukebox() {
        net.minecraft.world.item.ItemStack nmsItem = tileEntity.getRecord();
        if (nmsItem.isEmpty())
            return null;

        return CraftItemStack.asBukkitCopy(nmsItem);
    }
}
