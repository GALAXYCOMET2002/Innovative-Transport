/*
 * Copyright (c) 2019. Made by SoRA_X7. CC BY-NC-SA 4.0
 */

package warpdrive.innovativetransport.pipe.base;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public interface IPipeHolder extends ICapabilityProvider {
    World getWorld();
    BlockPos getPos();
    IPipe getPipe();

    @Nullable
    TileEntity getNeighborTile(EnumFacing facing);
}
