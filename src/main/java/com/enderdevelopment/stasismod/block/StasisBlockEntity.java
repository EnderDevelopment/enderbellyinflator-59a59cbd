package com.enderdevelopment.stasismod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StasisBlockEntity extends BlockEntity {
  private EndermanEntity enderman;

  public StasisBlockEntity(BlockPos pos, BlockState state) {
    super(StasisMod.STASIS_BLOCK_ENTITY, pos, state);
  }

  public void setEnderman(PlayerEntity player) {
    if (enderman == null) {
      enderman = new EndermanEntity(EntityType.ENDERMAN, player.getWorld());
      enderman.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0);
      player.getWorld().spawnEntity(enderman);
      markDirty();
    }
  }

  public EndermanEntity getEnderman() {
    return enderman;
  }

  @Override
  protected void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
    if (enderman != null) {
      NbtCompound endermanNbt = new NbtCompound();
      enderman.writeNbt(endermanNbt);
      nbt.put("Enderman", endermanNbt);
    }
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    if (nbt.contains("Enderman")) {
      NbtCompound endermanNbt = nbt.getCompound("Enderman");
      enderman = new EndermanEntity(EntityType.ENDERMAN, world);
      enderman.readNbt(endermanNbt);
    }
  }

  @Override
  public Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt() {
    return createNbt();
  }
}