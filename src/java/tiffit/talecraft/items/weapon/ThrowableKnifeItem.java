package tiffit.talecraft.items.weapon;

import javax.annotation.Nullable;

import de.longor.talecraft.TaleCraftItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tiffit.talecraft.entity.projectile.EntityBoomerang;

public class ThrowableKnifeItem extends TCWeaponItem {
	
	public ThrowableKnifeItem() {
		super();
		this.addPropertyOverride(new ResourceLocation("thrown"), new IItemPropertyGetter(){
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)	{
            	return isThrown(stack) ? 1.0F : 0.0F;
            }
        });
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		return !isThrown(item);
	}
	
	@SubscribeEvent
	public void onItemDrop(ItemTossEvent event){
		if(!event.getPlayer().worldObj.isRemote){
			if(isThrown(event.getEntityItem().getEntityItem())){
				event.getEntityItem().getEntityItem().getTagCompound().setBoolean("thrown", false);
			}
		}
	}
	
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.AMBIENT, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isRemote) {
			if(!isThrown(stack)){
				if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setBoolean("thrown", true);
				EntityBoomerang boomerang = new EntityBoomerang(world, player);
				boomerang.setItemStack(stack);
				boomerang.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntityInWorld(boomerang);
			}
		}

		return new ActionResult(EnumActionResult.SUCCESS, stack);
	}
	
	public static boolean isThrown(ItemStack stack){
		if(stack == null || stack.getItem() != TaleCraftItems.boomerang) return false;
		if(!stack.hasTagCompound()) return false;
		NBTTagCompound tag = stack.getTagCompound();
		return tag.getBoolean("thrown");
	}
	
}
