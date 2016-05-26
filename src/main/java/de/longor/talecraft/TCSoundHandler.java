package de.longor.talecraft;

import java.lang.reflect.Field;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class TCSoundHandler {

	public static SoundEvent SONG1, SONG2, SONG3, SONG4, SONG5, SONG6, SONG7, SONG8;
	public static SoundEvent EFFECT1, EFFECT2, EFFECT3, EFFECT4, EFFECT5, EFFECT6, EFFECT7, EFFECT8;
	public static SoundEvent EXTRA1, EXTRA2, EXTRA3, EXTRA4;
	
	private static int index = 0;
	
	public static void init() {
		index = SoundEvent.soundEventRegistry.getKeys().size();
		
		SONG1 = register("SONG1");
		SONG2 = register("SONG2");
		SONG3 = register("SONG3");
		SONG4 = register("SONG4");
		SONG5 = register("SONG5");
		SONG6 = register("SONG6");
		SONG7 = register("SONG7");
		SONG8 = register("SONG8");
		
		EFFECT1 = register("EFFECT1");
		EFFECT2 = register("EFFECT2");
		EFFECT3 = register("EFFECT3");
		EFFECT4 = register("EFFECT4");
		EFFECT5 = register("EFFECT5");
		EFFECT6 = register("EFFECT6");
		EFFECT7 = register("EFFECT7");
		EFFECT8 = register("EFFECT8");
		
		EXTRA1 = register("EXTRA1");
		EXTRA2 = register("EXTRA2");
		EXTRA3 = register("EXTRA3");
		EXTRA4 = register("EXTRA4");
		
	}
	
	private static SoundEvent register(String name) {
		ResourceLocation loc = new ResourceLocation(Reference.MOD_ID + ":" + name);
		SoundEvent e = new SoundEvent(loc);
		SoundEvent.soundEventRegistry.register(index, loc, e);
		index++;
		return e;
	}
	
	public static enum SoundEnum{
		SONG1, SONG2, SONG3, SONG4, SONG5, SONG6, SONG7, SONG8,
		EFFECT1, EFFECT2, EFFECT3, EFFECT4, EFFECT5, EFFECT6, EFFECT7, EFFECT8,
		EXTRA1, EXTRA2, EXTRA3, EXTRA4;
		
		public SoundEvent getSoundEvent(){
			Class<TCSoundHandler> tcsh = TCSoundHandler.class;
			try {
				Field field = tcsh.getField(name());
				return (SoundEvent) field.get(null);
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
	}
	
}