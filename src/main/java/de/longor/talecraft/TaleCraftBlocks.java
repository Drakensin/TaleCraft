package de.longor.talecraft;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;

import de.longor.talecraft.blocks.deco.BlankBlock;
import de.longor.talecraft.blocks.util.BlockUpdateDetector;
import de.longor.talecraft.blocks.util.ClockBlock;
import de.longor.talecraft.blocks.util.CollisionTriggerBlock;
import de.longor.talecraft.blocks.util.DelayBlock;
import de.longor.talecraft.blocks.util.EmitterBlock;
import de.longor.talecraft.blocks.util.HiddenBlock;
import de.longor.talecraft.blocks.util.ImageHologramBlock;
import de.longor.talecraft.blocks.util.InverterBlock;
import de.longor.talecraft.blocks.util.KillBlock;
import de.longor.talecraft.blocks.util.LightBlock;
import de.longor.talecraft.blocks.util.MemoryBlock;
import de.longor.talecraft.blocks.util.MessageBlock;
import de.longor.talecraft.blocks.util.RedstoneActivatorBlock;
import de.longor.talecraft.blocks.util.RedstoneTriggerBlock;
import de.longor.talecraft.blocks.util.RelayBlock;
import de.longor.talecraft.blocks.util.ScriptBlock;
import de.longor.talecraft.blocks.util.StorageBlock;
import de.longor.talecraft.blocks.util.SummonBlock;
import de.longor.talecraft.blocks.util.TriggerFilterBlock;
import de.longor.talecraft.blocks.util.URLBlock;
import de.longor.talecraft.blocks.util.tileentity.BlockUpdateDetectorTileEntity;
import de.longor.talecraft.blocks.util.tileentity.ClockBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.CollisionTriggerBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.DelayBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.EmitterBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.ImageHologramBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.InverterBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.LightBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.MemoryBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.MessageBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.RedstoneTriggerBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.RelayBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.ScriptBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.StorageBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.SummonBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.TriggerFilterBlockTileEntity;
import de.longor.talecraft.blocks.util.tileentity.URLBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tiffit.talecraft.blocks.MusicBlock;
import tiffit.talecraft.blocks.world.LockedDoorBlock;
import tiffit.talecraft.blocks.world.SpikeBlock;
import tiffit.talecraft.tileentity.LockedDoorTileEntity;
import tiffit.talecraft.tileentity.MusicBlockTileEntity;

public class TaleCraftBlocks {
	public static HashMap<String, Block> blocksMap = Maps.newHashMap();
	public static List<Block> blocks = Lists.newArrayList();

	// UTILITY
	public static KillBlock killBlock;
	public static ClockBlock clockBlock;
	public static RedstoneTriggerBlock redstoneTrigger;
	public static RedstoneActivatorBlock redstoneActivator;
	public static RelayBlock relayBlock;
	public static ScriptBlock scriptBlock;
	public static BlockUpdateDetector updateDetectorBlock;
	public static StorageBlock storageBlock;
	public static EmitterBlock emitterBlock;
	public static ImageHologramBlock imageHologramBlock;
	//	public static BarrierEXTBlock barrierEXTBlock;
	public static CollisionTriggerBlock collisionTriggerBlock;
	public static LightBlock lightBlock;
	public static HiddenBlock hiddenBlock;
	public static MessageBlock messageBlock;
	public static InverterBlock inverterBlock;
	public static MemoryBlock memoryBlock;
	public static TriggerFilterBlock triggerFilterBlock;
	public static DelayBlock delayBlock;
	public static URLBlock urlBlock;
	public static SummonBlock summonBlock;
	public static MusicBlock musicBlock;
	
	//WORLD
	public static LockedDoorBlock lockedDoorBlock;
	public static SpikeBlock spikeBlock;
	
	// DECORATION
	public static BlankBlock blankBlock;
	public static BlankBlock deco_stone_a;
	public static BlankBlock deco_stone_b;
	public static BlankBlock deco_stone_c;
	public static BlankBlock deco_stone_d;
	public static BlankBlock deco_stone_e;
	public static BlankBlock deco_stone_f;
	public static BlankBlock deco_wood_a;
	public static BlankBlock deco_glass_a;
	public static BlankBlock deco_cage_a;

	public static void init()
	{
		blocksMap = Maps.newHashMap();
		blocks = Lists.newArrayList();
		///////////////////////////////////

		init_utility();
		init_decoration();
		init_world();
	}

	private static void init_world(){
		lockedDoorBlock = register("lockeddoorblock", new LockedDoorBlock());
		GameRegistry.registerTileEntity(LockedDoorTileEntity.class, "tc_lockeddoorblock");
	}
	
	private static void init_utility() {
		killBlock = register("killblock", new KillBlock());

		clockBlock = register("clockblock", new ClockBlock());
		GameRegistry.registerTileEntity(ClockBlockTileEntity.class, "tc_clockblock");

		redstoneTrigger = register("redstone_trigger", new RedstoneTriggerBlock());
		GameRegistry.registerTileEntity(RedstoneTriggerBlockTileEntity.class, "tc_redstonetrigger");

		redstoneActivator = register("redstone_activator", new RedstoneActivatorBlock());

		relayBlock = register("relayblock", new RelayBlock());
		GameRegistry.registerTileEntity(RelayBlockTileEntity.class, "tc_relayblock");

		scriptBlock = register("scriptblock", new ScriptBlock());
		GameRegistry.registerTileEntity(ScriptBlockTileEntity.class, "tc_scriptblock");

		updateDetectorBlock = register("updatedetectorblock", new BlockUpdateDetector());
		GameRegistry.registerTileEntity(BlockUpdateDetectorTileEntity.class, "tc_updatedetectorblock");

		storageBlock = register("storageblock", new StorageBlock());
		GameRegistry.registerTileEntity(StorageBlockTileEntity.class, "tc_storageblock");

		emitterBlock = register("emitterblock", new EmitterBlock());
		GameRegistry.registerTileEntity(EmitterBlockTileEntity.class, "tc_emitterblock");

		imageHologramBlock = register("imagehologramblock", new ImageHologramBlock());
		GameRegistry.registerTileEntity(ImageHologramBlockTileEntity.class, "tc_imagehologramblock");

		collisionTriggerBlock = register("collisiontriggerblock", new CollisionTriggerBlock());
		GameRegistry.registerTileEntity(CollisionTriggerBlockTileEntity.class, "tc_collisiontriggerblock");

		lightBlock = register("lightblock", new LightBlock());
		GameRegistry.registerTileEntity(LightBlockTileEntity.class, "tc_lightblock");

		hiddenBlock = register("hiddenblock", new HiddenBlock());

		messageBlock = register("messageblock", new MessageBlock());
		GameRegistry.registerTileEntity(MessageBlockTileEntity.class, "tc_messageblock");

		inverterBlock = register("inverterblock", new InverterBlock());
		GameRegistry.registerTileEntity(InverterBlockTileEntity.class, "tc_inverterblock");

		memoryBlock = register("memoryblock", new MemoryBlock());
		GameRegistry.registerTileEntity(MemoryBlockTileEntity.class, "tc_memoryblock");

		triggerFilterBlock = register("triggerfilterblock", new TriggerFilterBlock());
		GameRegistry.registerTileEntity(TriggerFilterBlockTileEntity.class, "tc_triggerfilterblock");

		delayBlock = register("delayblock", new DelayBlock());
		GameRegistry.registerTileEntity(DelayBlockTileEntity.class, "tc_delayblock");

		urlBlock = register("urlblock", new URLBlock());
		GameRegistry.registerTileEntity(URLBlockTileEntity.class, "tc_urlblock");

		summonBlock = register("summonblock", new SummonBlock());
		GameRegistry.registerTileEntity(SummonBlockTileEntity.class, "tc_summonblock");
		
		musicBlock = register("musicblock", new MusicBlock());
		GameRegistry.registerTileEntity(MusicBlockTileEntity.class, "tc_musicblock");
		
		spikeBlock = register("spikeblock", new SpikeBlock());
		
		//spikeBlock = register("spikeblock", new SpikeBlock());
		//GameRegistry.registerTileEntity(SpikeBlockTileEntity.class, "tc_spikeblock");

		// Can't implement because custom renderers are currently only possible with tileentities
		//		barrierEXTBlock = register("barrierextblock", new BarrierEXTBlock(), new BlockRegisterFunc() {
		//			@Override public void call(Block block, String name) {
		//				GameRegistry.registerBlock(block, ItemBarrierEXTBlock.class, name);
		//			}
		//		});
	}

	private static void init_decoration() {
		blankBlock = register("blankblock", new BlankBlock(SoundType.STONE), ItemBlockBlankBlock.class);
		deco_stone_a = register("deco_stone_a", new BlankBlock(SoundType.STONE), ItemBlockBlankBlock.class);
		deco_stone_b = register("deco_stone_b", new BlankBlock(SoundType.STONE), ItemBlockBlankBlock.class);
		deco_stone_c = register("deco_stone_c", new BlankBlock(SoundType.STONE), ItemBlockBlankBlock.class);
		deco_stone_d = register("deco_stone_d", new BlankBlock(SoundType.STONE), ItemBlockBlankBlock.class);
		deco_stone_e = register("deco_stone_e", new BlankBlock(SoundType.STONE), ItemBlockBlankBlock.class);
		deco_stone_f = register("deco_stone_f", new BlankBlock(SoundType.STONE), ItemBlockBlankBlock.class);

		deco_wood_a = register("deco_wood_a", new BlankBlock(SoundType.WOOD), ItemBlockBlankBlock.class);

		deco_glass_a = register("deco_glass_a", new BlankBlock(SoundType.GLASS), ItemBlockBlankBlock.class);
		deco_glass_a.blockLayer = 1; // CUTOUT layer
		deco_glass_a.ignoreSimilarity = false;
		deco_glass_a.setLightOpacity(0);

		deco_cage_a = register("deco_cage_a", new BlankBlock(SoundType.METAL), ItemBlockBlankBlock.class);
		deco_cage_a.blockLayer = 1; // CUTOUT layer
		deco_cage_a.ignoreSimilarity = true;
		deco_cage_a.setLightOpacity(0);

	}

	private static <T extends Block> T register(String name, T block) {
		block.setUnlocalizedName("talecraft:"+name);
		block.setRegistryName(Reference.MOD_ID, name);
		GameRegistry.register(block);
		TaleCraftItems.register(new ItemBlock(block), name);
		addToMaps(block, name);
		return block;
	}

	private static <T extends Block> T register(String name, T block, Class<? extends ItemBlock> itemblock) {
		block.setUnlocalizedName("talecraft:"+name);
		block.setRegistryName(Reference.MOD_ID, name);
		GameRegistry.register(block);
		addToMaps(block, name);
		ItemBlock i = null;
		try {
			i = itemblock.getConstructor(Block.class).newInstance(block);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i != null){
			TaleCraftItems.register(i, name);
		}else{
			System.out.println("ERROR: Failed to create ItemBlock FOR " + name);
		}
		return block;
	}
	
	private static void addToMaps(Block block, String name){
		blocksMap.put(name, block);
		blocks.add(block);
		Item item = Item.getItemFromBlock(block);
		if(item!=null)TaleCraftItems.ALL_TC_ITEMS.add(item);
	}

	public static class ItemBlockKillBlock extends ItemMultiTexture {
		public ItemBlockKillBlock(Block block) {
			super(block, block, new String[] {
					"all",
					"npc",
					"items",
					"living",
					"player",
					"monster",
					"xor_player"
			});
		}
	}

	public static class ItemBlockBlankBlock extends ItemMultiTexture {
		public ItemBlockBlankBlock(Block block) {
			super(block, block, new String[] {
					"0",
					"1",
					"2",
					"3",
					"4",
					"5",
					"6",
					"7",
					"8",
					"9",
					"10",
					"11",
					"12",
					"13",
					"14",
					"15"
			});
		}

		@Override
		public int getMetadata(int damage) {
			return damage;
		}
	}

	public static class ItemBarrierEXTBlock extends ItemMultiTexture {
		public ItemBarrierEXTBlock(Block block) {
			super(block, block, new String[] {
					"all", // 0
					"players", // 1
					"living", // 2
					"living (not players)", // 3
					"monsters", // 4
					"villagers", // 5
					"items" // 6
			});
		}
	}

	public static List<Block> getBlockList() {
		return blocks;
	}

}
