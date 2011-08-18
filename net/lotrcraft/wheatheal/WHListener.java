package net.lotrcraft.wheatheal;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;

public class WHListener extends EntityListener{

	// Create a new instance of our Healer class
	private Healer healer = WHMain.healer;
	private UseChecker checker = WHMain.checker;

	public void onEntityDamage(EntityDamageEvent event) {
			if (event instanceof EntityDamageByEntityEvent){
				EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;

				if (e.getEntity() instanceof Player && e.getDamager() instanceof Player){
					Player puncher = (Player)e.getDamager();
					Player punchee = (Player)e.getEntity();
					int itemID = puncher.getItemInHand().getTypeId();


					// Only perform the code if one of the itemID's is detected in the puncher's hand and is set to be used
					if (checker.useChecker(itemID)) {
						// DEBUG LINE BELOW - REMOVE ONCE TESTING IS COMPLETE
						//WHMain.log.info("Healer - ItemID:" + itemID + " Punchee:" + punchee.getEntityId()+ " Puncher:" + puncher.getName());

						// Cancel the event to prevent any damage being caused to the player being punched
						event.setCancelled(true);

						if (punchee.getHealth() == 20) return;  //If punchee health is 20 you cant heal them

						if (!permissionsCheck.check(puncher, "wheatheal.heal")){
							// DEBUG LINES. COMMENT OUT IF NOT WANTED IN MAIN RELEASES
							//puncher.sendMessage(ChatColor.GREEN + "[WheatHeal] " + ChatColor.RED + "You don't have permission to heal players!");
							//punchee.sendMessage(ChatColor.GREEN + "[WheatHeal] " + ChatColor.AQUA + puncher.getName() +
							//		ChatColor.RED + " tried to heal you but doesn't have permission!");
							return;
						}

						if (itemID == 282) { 					// If block for punching with mushroom stew in hand

							// Remove 1 mushroom stew and add an empty bowl to inventory if more than 1 mushroom stew is in hand
							if (puncher.getItemInHand().getAmount() > 1) {
								puncher.getItemInHand().setAmount(puncher.getItemInHand().getAmount() - 1);
								puncher.getInventory().addItem(new ItemStack(281, 1));
							} // Remove mushroom stew and add an empty bowl to inventory if only 1 mushroom stew is in hand
							else if (puncher.getItemInHand().getAmount() == 1) {
								puncher.getItemInHand().setAmount(puncher.getItemInHand().getAmount() - 1);
								puncher.getInventory().addItem(new ItemStack(281, 1));
							}
						}
						else {
							// Decrease itemInHand amount by 1 or remove if the player only had 1 of the item
							if (puncher.getItemInHand().getAmount() > 1) {
								puncher.getItemInHand().setAmount(puncher.getItemInHand().getAmount() - 1);
							}
							else {
								puncher.setItemInHand(null);
							}
						}

						// Finally call healPlayer in our healer class and pass in the punchee and the itemID
						healer.healPlayer(punchee, itemID);

						//else {
						//	  puncher.sendMessage(ChatColor.RED + "You do not have permission to do this");
						//}
					}


				}

			}

	}
}
