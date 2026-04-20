package me.mattyhd0.koth.scoreboard.hook.sternalboard;

import me.mattyhd0.koth.playeable.CurrentKoth;
import me.mattyhd0.koth.scoreboard.hook.ScoreboardHook;
import me.mattyhd0.koth.scoreboard.hook.plugin.KoTHScoreboardHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SternalBoardHook extends ScoreboardHook {

    private final KoTHScoreboardHook koTHScoreboardHook;

    public SternalBoardHook(){
        koTHScoreboardHook = new KoTHScoreboardHook();
    }

    @Override
    public String getHookName() {
        return "SternalBoard (Beta)";
    }

    @Override
    public void onKothStart(CurrentKoth currentKoth) {

        if (!hasSternalBoard()) {
            koTHScoreboardHook.onKothStart(currentKoth);
            return;
        }

        try {
            Object manager = getSternalManager();
            if (manager != null) {
                for(Player player: Bukkit.getServer().getOnlinePlayers()){
                    manager.getClass().getMethod("removeScoreboard", Player.class).invoke(manager, player);
                }
            }
        } catch (Exception ignored) {
            // Fallback to default scoreboard handling
        }

        koTHScoreboardHook.onKothStart(currentKoth);

    }

    @Override
    public void onKothEnd(CurrentKoth currentKoth) {

        if (hasSternalBoard()) {
            try {
                Object manager = getSternalManager();
                if (manager != null) {
                    for(Player player: Bukkit.getServer().getOnlinePlayers()){
                        manager.getClass().getMethod("setScoreboard", Player.class).invoke(manager, player);
                    }
                }
            } catch (Exception ignored) {
                // Fallback below
            }
        }

        koTHScoreboardHook.onKothEnd(currentKoth);
    }

    @Override
    public void update(CurrentKoth currentKoth) {
        koTHScoreboardHook.update(currentKoth);
    }

    public void removeBoard(Player player){
        if (!hasSternalBoard()) return;
        try {
            Object manager = getSternalManager();
            if (manager != null) {
                manager.getClass().getMethod("removeScoreboard", Player.class).invoke(manager, player);
            }
        } catch (Exception ignored) {
            // Ignore if reflection fails
        }
    }

    private boolean hasSternalBoard(){
        return Bukkit.getPluginManager().getPlugin("SternalBoard") != null;
    }

    private Object getSternalManager() throws Exception {
        Class<?> sternalClass = Class.forName("com.xism4.sternalboard.SternalBoard");
        Object instance = sternalClass.getMethod("getInstance").invoke(null);
        return instance.getClass().getMethod("getScoreboardManager").invoke(instance);
    }

}
