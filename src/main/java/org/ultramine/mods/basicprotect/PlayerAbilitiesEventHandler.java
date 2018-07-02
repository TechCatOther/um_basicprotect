package org.ultramine.mods.basicprotect;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import org.ultramine.core.permissions.Permissions;
import org.ultramine.core.service.InjectService;

public class PlayerAbilitiesEventHandler
{
	@InjectService private static Permissions perms;

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void checkChatPermission(ServerChatEvent e)
	{
		if(e.player.playerNetServerHandler == null || e.player.getData() == null)
			return;
		if(!perms.has(e.player, "ability.player.chat"))
		{
			e.setCanceled(true);
			e.player.addChatMessage(new ChatComponentTranslation("ultramine.ability.chat").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onBreakEvent(BlockEvent.BreakEvent e)
	{
		if(!e.getPlayer().isEntityPlayerMP() || ((EntityPlayerMP)e.getPlayer()).playerNetServerHandler == null)
			return;
		if(!perms.has(e.getPlayer(), "ability.player.blockbreak"))
		{
			e.setCanceled(true);
			e.getPlayer().addChatMessage(new ChatComponentTranslation("ultramine.ability.blockbreak").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlaceEvent(BlockEvent.PlaceEvent e)
	{
		if(!e.player.isEntityPlayerMP() || ((EntityPlayerMP)e.player).playerNetServerHandler == null)
			return;
		if(!perms.has(e.player, "ability.player.blockplace"))
		{
			e.setCanceled(true);
			e.player.addChatMessage(new ChatComponentTranslation("ultramine.ability.blockplace").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerInteractEvent(PlayerInteractEvent e)
	{
		if(!e.entityPlayer.isEntityPlayerMP() || ((EntityPlayerMP)e.entityPlayer).playerNetServerHandler == null)
			return;
		if(!perms.has(e.entityPlayer, "ability.player.useitem"))
		{
			e.useItem = Event.Result.DENY;
			if(e.entityPlayer.inventory.getCurrentItem() != null)
				e.entityPlayer.addChatMessage(new ChatComponentTranslation("ultramine.ability.useitem").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
		if(!perms.has(e.entityPlayer, "ability.player.useblock"))
		{
			e.useBlock = Event.Result.DENY;
			e.entityPlayer.addChatMessage(new ChatComponentTranslation("ultramine.ability.useblock").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}

		if(e.useItem == Event.Result.DENY && e.useBlock == Event.Result.DENY)
			e.setCanceled(true);
	}

	@SideOnly(Side.SERVER)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onLivingAttackEvent(LivingAttackEvent e)
	{
		Entity attacker = e.source.getEntity();
		if(attacker != null && attacker.isEntityPlayerMP())
		{
			EntityPlayerMP player = (EntityPlayerMP)attacker;
			if(player.playerNetServerHandler == null)
				return;
			if(!perms.has(player, "ability.player.attack"))
			{
				e.setCanceled(true);
				player.addChatMessage(new ChatComponentTranslation("ultramine.ability.attack").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		}
	}
}
