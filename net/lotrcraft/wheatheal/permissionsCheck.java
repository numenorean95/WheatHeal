package net.lotrcraft.wheatheal;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class permissionsCheck {

	public static boolean check(CommandSender sender, String permission) {

		if (sender instanceof ConsoleCommandSender) return true;
		else if (Config.useBukkitPerms) { // If using PermissionsBukkit
			if (sender.hasPermission(permission)) { // Its good
				return true;
			}
			else if (sender.isOp()) { // If PermissionsBukkit is disabled check if sender is an op
				return true;
			}
		}
		else { // If using Nijikokun's Permissions or t3hk0d3's PermissionsEx
			if (WHMain.nijikoPermissions != null) { // Check that Permissions is enabled
				if (WHMain.nijikoPermissions.has((Player)sender, permission) || sender.isOp()) { // Again you can change this node if you want
					return true;
				}
			}
			else if (WHMain.permissionsEx != null) { // Check that Permissions is enabled
				if (WHMain.permissionsEx.has((Player)sender, permission) || sender.isOp()) { // Again you can change this node if you want
					return true;
				}
			}
			else if (sender.isOp()) { // If player doesn't have the permission but is an op
				return true;
			}
		}

		return false;
	}

}
