package solab.innovativetransport.card;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.pipe.BlockPipe;
import solab.innovativetransport.pipe.TilePipeHolder;

public class ItemCardSlot extends Item {
    private ItemCardSlot() {
        super();
        setCreativeTab(InnovativeTransport.tab);
        setRegistryName("itemcardslot");
        setUnlocalizedName("itemcardslot");
    }
    public static final ItemCardSlot INSTANCE = new ItemCardSlot();

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState blockState = worldIn.getBlockState(pos);
        if (blockState.getBlock() == BlockPipe.INSTANCE) {
            TilePipeHolder holder = (TilePipeHolder)worldIn.getTileEntity(pos);
            if (holder != null) {
                if (holder.attachCardSlot(facing)) {
                    stack.stackSize--;
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }
}
