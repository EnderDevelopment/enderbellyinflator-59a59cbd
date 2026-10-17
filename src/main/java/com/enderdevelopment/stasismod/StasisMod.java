package com.enderdevelopment.stasismod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class StasisMod implements ModInitializer {
  public static final String MOD_ID = "stasis_mod";

  public static final Block STASIS_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(2.0f).requiresTool()) {
  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    if (!world.isClient) {
      ItemStack heldItem = player.getStackInHand(hand);
      if (heldItem.getItem() == Items.LEAD) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof StasisBlockEntity) {
          StasisBlockEntity stasisBlockEntity = (StasisBlockEntity) blockEntity;
          if (stasisBlockEntity.getEnderman() == null) {
            stasisBlockEntity.setEnderman(player);
            heldItem.decrement(1);
            return ActionResult.SUCCESS;
          }
        }
      }
    }
    return ActionResult.PASS;
  }
};

  @Override
  public void onInitialize() {
    Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "stasis_block"), STASIS_BLOCK);
    Registry.register(Registries.ITEM, new Identifier(MOD_ID, "stasis_block"), new BlockItem(STASIS_BLOCK, new Item.Settings()));
  }
}