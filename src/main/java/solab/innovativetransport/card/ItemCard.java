package solab.innovativetransport.card;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import solab.innovativetransport.InnovativeTransport;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.pipe.BlockPipe;
import solab.innovativetransport.pipe.TilePipeHolder;

import java.util.List;

public class ItemCard extends Item {

    CardBase card;

    public ItemCard() {
        super();
        setRegistryName(InnovativeTransport.MODID,"itemcard");
        setUnlocalizedName(InnovativeTransport.MODID + "_itemcard");
        addPropertyOverride(new ResourceLocation("cardtype"), (stack, worldIn, entityIn) -> {
            if (stack.getTagCompound() != null) {
                return stack.getTagCompound().getInteger("cardtype");
            }
            return 0;
        });
        setCreativeTab(InnovativeTransport.tab);
    }
    public static final ItemCard INSTANCE = new ItemCard();

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (int i=0;i<EnumCards.maxLength();i++) {
            ItemStack stack = new ItemStack(itemIn);
            NBTTagCompound compound1 = new NBTTagCompound();
            compound1.setInteger("cardtype",i);
            stack.setTagCompound(compound1);
            subItems.add(stack);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
        return super.getUnlocalizedName(stack) + stack.getTagCompound().getInteger("cardtype");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (stack.getTagCompound() != null) {
            tooltip.add(stack.getTagCompound().getInteger("cardtype") + "");
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        card = CardBase.getCardFromType(EnumCards.values()[stack.getTagCompound().getInteger("cardtype")]);
        if (worldIn.getBlockState(pos).getBlock() == BlockPipe.INSTANCE) {
            TilePipeHolder holder = (TilePipeHolder) worldIn.getTileEntity(pos);
            if (holder != null && holder.getPipe().getCardSlot(facing) != null) {
                holder.getPipe().getCardSlot(facing).insert(card);
                stack.stackSize--;
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

}
