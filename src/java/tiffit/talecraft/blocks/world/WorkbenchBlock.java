package tiffit.talecraft.blocks.world;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.longor.talecraft.TaleCraft;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tiffit.talecraft.util.WorkbenchManager;

public class WorkbenchBlock extends TCWorldBlock{

	public static WorkbenchManager recipes = new WorkbenchManager();
	
	public WorkbenchBlock() {
		super();
		setSoundType(SoundType.WOOD);
	}
	
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack helditem, EnumFacing side, float hitX, float hitY, float hitZ){
    	player.openGui(TaleCraft.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
    	return true;
    }
    

	public static ItemStack findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn){
        for(IRecipe irecipe : recipes){
            if (irecipe.matches(craftMatrix, worldIn)){
                return irecipe.getCraftingResult(craftMatrix);
            }
        }
        return null;
    }
	
    public static ItemStack[] getRemainingItems(InventoryCrafting craftMatrix, World worldIn){
        for (IRecipe irecipe : recipes){
            if (irecipe.matches(craftMatrix, worldIn)){
                return irecipe.getRemainingItems(craftMatrix);
            }
        }
        ItemStack[] aitemstack = new ItemStack[craftMatrix.getSizeInventory()];
        for (int i = 0; i < aitemstack.length; ++i){
            aitemstack[i] = craftMatrix.getStackInSlot(i);
        }
        return aitemstack;
    }

}
