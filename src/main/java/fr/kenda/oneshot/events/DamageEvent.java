package fr.kenda.oneshot.events;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.gamestatus.GameStatus;
import fr.kenda.oneshot.gamestatus.GameStatusManager;
import fr.kenda.oneshot.managers.GameManager;
import fr.kenda.oneshot.utils.MessageUtils;
import fr.kenda.oneshot.utils.Settings;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageEvent implements Listener {

    private final GameManager GAME_MANAGER = Oneshot.GAME_MANAGER;

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!GameStatusManager.isStatus(GameStatus.GAME)
                || !(e.getEntity() instanceof Player victim)) {
            e.setCancelled(true);
            return;
        }

        Player damager = null;

        if (e.getDamager() instanceof Arrow arrow) {
            ProjectileSource shooter = arrow.getShooter();
            if (shooter instanceof Player player)
                damager = player;
        } else {
            damager = (Player) e.getDamager();
            if (damager.getInventory().getItemInMainHand().getType() != Settings.getMaterial("sword")) {
                damager.sendMessage(MessageUtils.getMessage("cant_kill_with_hand", true));
                e.setCancelled(true);
                return;
            }
        }
        if (damager == victim) {
            e.setCancelled(true);
            return;
        }
        if (damager != null) {
            GameManager.getStatsOfPlayer(damager).addGameKill();
            GameManager.getStatsOfPlayer(victim).addGameDeath();
            GAME_MANAGER.reloadScoreboard();
            victim.sendMessage(MessageUtils.getMessage("killed_by", true, "%player%", damager.getName()));
            victim.setGameMode(GameMode.SPECTATOR);
            damager.playSound(damager.getLocation(), Settings.getKilledSound(), 1, 1);

            e.setCancelled(true);
            new BukkitRunnable() {
                private int timer = Settings.getTimerRespawn();

                @Override
                public void run() {
                    if (timer == 0) {
                        victim.setGameMode(GameMode.ADVENTURE);
                        GameManager.giveItem(victim);
                        victim.teleport(GAME_MANAGER.getRandomLocation());
                        victim.setHealth(20);
                        victim.setFoodLevel(20);
                        victim.sendTitle(MessageUtils.getMessage("title.respawn.title_back_area", false),
                                MessageUtils.getMessage("title.respawn.subtitle_back_area", false),
                                0, 40, 20);
                        cancel();
                    }
                    victim.sendTitle(MessageUtils.getMessage("title.respawn.title", false, "%seconds%", String.valueOf(timer)),
                            MessageUtils.getMessage("title.respawn.subtitle", false, "%seconds%", String.valueOf(timer)),
                            0, 20, 20);
                    timer--;
                }
            }.runTaskTimer(Oneshot.getInstance(), 20, 20);
        }
    }
}