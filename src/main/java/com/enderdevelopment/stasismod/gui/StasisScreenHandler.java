package com.enderdevelopment.stasismod.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class StasisScreenHandler extends ScreenHandler {
  private final Inventory inventory;

  public StasisScreenHandler(int syncId, PlayerInventory playerInventory) {
    this(syncId, playerInventory, new SimpleInventory(1));
  }

  public StasisScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
    super(StasisMod.STASIS_SCREEN_HANDLER, syncId);
    this.inventory = inventory;
    inventory.onOpen(playerInventory.player);

    this.addSlot(new Slot(inventory, 0, 80, 35));

    int m;
    int l;
    for (m = 0; m < 3; ++m) {
      for (l = 0; l < 9; ++l) {
        this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
      }
    }

    for (m = 0; m < 9; ++m) {
      this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
    }
  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return this.inventory.canPlayerUse(player);
  }

  @Override
  public ItemStack quickMove(PlayerEntity player, int slot) {
    ItemStack itemStack = ItemStack.EMPTY;
    Slot slot2 = this.slots.get(slot);
    if (slot2 != null && slot2.hasStack()) {
      ItemStack itemStack2 = slot2.getStack();
      itemStack = itemStack2.copy();
      if (slot < this.inventory.size()) {
        if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
          return ItemStack.EMPTY;
        }
      } else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
        return ItemStack.EMPTY;
      }

      if (itemStack2.isEmpty()) {
        slot2.setStack(ItemStack.EMPTY);
      } else {
        slot2.markDirty();
      }
    }

    return itemStack;
  }
}