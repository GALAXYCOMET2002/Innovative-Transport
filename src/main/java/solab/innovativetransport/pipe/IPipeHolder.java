package solab.innovativetransport.pipe;

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
