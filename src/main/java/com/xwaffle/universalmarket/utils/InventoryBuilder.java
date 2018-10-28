package com.xwaffle.universalmarket.utils;

import com.xwaffle.universalmarket.UniversalMarket;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.text.Text;

/**
 * Created by Chase(Xwaffle) on 12/29/2017.
 */
public abstract class InventoryBuilder {

    private final Inventory inventory;
//    private String title = "Inventory";
//    private int rows = 3;

    public InventoryBuilder() {
        this(3);
    }

    private InventoryBuilder(int rows) {
        this("Inventory", rows);
    }

    public InventoryBuilder(String title) {
        this(title, 27);
    }

    protected InventoryBuilder(String title, int rows) {
//        this.title = title;
//        this.rows = rows;
        inventory = Inventory.builder().of(InventoryArchetypes.DOUBLE_CHEST)
                .property("inventorydimension", new InventoryDimension(9, rows))
                .property("inventorytitle", InventoryTitle.of(Text.of(title)))
                .property("inventory_builder", InventoryTitle.of(Text.of(title)))
                .listener(ClickInventoryEvent.class, this::onClickInventoryEvent)
                .build(UniversalMarket.getInstance());
    }

    public InventoryBuilder addItem(ItemStack stack) {
        inventory.query(stack);
        return this;
    }

    public InventoryBuilder setItem(int slot, ItemStack stack) {
        inventory.query(new SlotIndex(slot)).set( stack);
        return this;
    }

    public Inventory build() {
        return inventory;
    }

    public void display(Player player) {
        player.openInventory(inventory, Cause.of(NamedCause.of("Open", UniversalMarket.getInstance())));
    }

    public abstract void onClickInventoryEvent(ClickInventoryEvent event);

}
