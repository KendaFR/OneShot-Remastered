package fr.kenda.oneshot.schedulers;

import fr.kenda.oneshot.gamestatus.GameStatus;
import fr.kenda.oneshot.gamestatus.GameStatusManager;
import fr.kenda.oneshot.utils.ItemBuilder;
import fr.kenda.oneshot.utils.MessageUtils;
import fr.kenda.oneshot.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerArrow extends BukkitRunnable {

    private int timer = Settings.getTimerGivenArrow();

    @Override
    public void run() {
        if (!GameStatusManager.isStatus(GameStatus.GAME))
            cancel();
        if (timer == 0) {
            ItemStack arrow = new ItemBuilder(Settings.getMaterial("arrow")).setName(Settings.getName("arrow")).toItemStack();
            for (Player player : Bukkit.getOnlinePlayers()) {
                int size = player.getInventory().getSize();
                for (int i = 0; i < size; i++) {
                    Inventory inv = player.getInventory();
                    ItemStack item = inv.getItem(i);
                    if (item != null) {
                        if (item.getType() == Settings.getMaterial("arrow")) {
                            int amount = item.getAmount();
                            if (amount < Settings.getMaxArrow()) {
                                player.getInventory().addItem(arrow);
                                player.sendMessage(MessageUtils.getMessage("received_arrow", true));
                            } else
                                MessageUtils.getMessage("already_max_arrow", true);
                            break;
                        }
                    }
                }
            }
            timer = Settings.getTimerGivenArrow();
        } else {
            Bukkit.getOnlinePlayers().forEach(p -> p.setLevel(timer));
            timer--;
        }
    }
}
