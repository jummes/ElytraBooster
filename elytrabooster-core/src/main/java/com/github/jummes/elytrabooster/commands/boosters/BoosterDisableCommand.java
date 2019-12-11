package com.github.jummes.elytrabooster.commands.boosters;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import com.github.jummes.elytrabooster.boosters.Booster;
import com.github.jummes.elytrabooster.core.ElytraBooster;
import com.github.jummes.elytrabooster.managers.boosters.BoosterManager;
import com.github.jummes.elytrabooster.utils.MessageUtils;

public class BoosterDisableCommand extends BoosterCommand {

	public BoosterDisableCommand(ElytraBooster plugin, CommandSender sender, String subCommand, String[] arguments,
			boolean isSenderPlayer, String boosterString) {
		super(plugin, sender, subCommand, arguments, isSenderPlayer, boosterString);
	}

	@Override
	protected void execute() {
		BoosterManager<?> boosterManager = BoosterManager.getBoosterManager(boosterString);
		if (arguments.length < 1) {
			incorrectUsage();
			return;
		}
		String id = arguments[0];
		Booster booster;
		if (boosterManager.getBoostersMap().containsKey(id)) {
			booster = boosterManager.getBooster(id);
		} else {
			invalidBooster();
			return;
		}
		booster.stopBoosterTask();
		sender.sendMessage(MessageUtils.color("&aPortal disabled, &6ID: &a" + id));
	}

	@Override
	protected boolean isOnlyPlayer() {
		return false;
	}
	
	@Override
	protected Permission getPermission() {
		return new Permission("eb.admin.disable");
	}

}