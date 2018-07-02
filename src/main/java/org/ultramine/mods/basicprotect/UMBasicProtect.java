package org.ultramine.mods.basicprotect;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "UM-BasicProtect", name = "UM-BasicProtect", version = "@version@", acceptableRemoteVersions = "*")
public class UMBasicProtect
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new PlayerAbilitiesEventHandler());
	}
}
