package tiffit.talecraft.entity.projectile;

import de.longor.talecraft.TaleCraftItems;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import tiffit.talecraft.client.render.RenderBoomerang;
import tiffit.talecraft.items.weapon.BoomerangItem;

public class EntityBoomerang extends EntityThrowable{
	
	private int rotation = 0;
	private ItemStack stack;
	private boolean isReturning = false;
	
    public EntityBoomerang(World world){
        super(world);
    }

    public EntityBoomerang(World world, EntityLivingBase thrower){
        super(world, thrower);
    }

    public EntityBoomerang(World world, double x, double y, double z){
        super(world, x, y, z);
    }
    
    public void setItemStack(ItemStack stack){
    	this.stack = stack;
    }
	
	@Override
    protected void onImpact(RayTraceResult result){
		if(worldObj.isRemote) return;
		if(result.typeOfHit == Type.ENTITY){
			Entity ent = result.entityHit;
			if(ent == getThrower()){
				setDead();
			}else{
				ent.attackEntityFrom(DamageSource.causeIndirectDamage(this, getThrower()), 4F);
			}
		}else{
			if(isReturning){
				setDead();
			}else{
				returnToThrower();
			}	
		}
    }
	
	@Override
	public void onUpdate(){
		rotation+= 30;
		if(rotation > 360){
			rotation = 0;
		}
		if(ticksExisted > 20){
			ticksExisted = 0;
			if(isReturning){
				setDead();
			}else{
				returnToThrower();
			}
		}
		super.onUpdate();
	}
	
	private void returnToThrower(){
		isReturning = true;
		this.motionX *= -1;
		this.motionY *= -1;
		this.motionZ *= -1;
	}
	
	@Override
	public void setDead() {
		super.setDead();
		if(getThrower() != null){
			stack.getTagCompound().setBoolean("thrown", false);
		}
	}
	
	public int getRotation() {
		return rotation;
	}
	
	@Override
	protected float getGravityVelocity() {
		return 0;
	}
	
	 public void writeEntityToNBT(NBTTagCompound tag){
	        super.writeEntityToNBT(tag);
	        
	    }

	    public void readEntityFromNBT(NBTTagCompound tag){
	       super.readEntityFromNBT(tag);
	       
	    }
	
	
	public static class EntityBoomerangFactory implements IRenderFactory{
		@Override
		public Render createRenderFor(RenderManager manager) {
			return new RenderBoomerang(manager);
		}
	}

}
