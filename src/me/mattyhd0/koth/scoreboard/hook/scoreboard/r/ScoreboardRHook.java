package me.mattyhd0.koth.scoreboard.hook.scoreboard.r;

import me.mattyhd0.koth.playeable.CurrentKoth;
import me.mattyhd0.koth.scoreboard.hook.ScoreboardHook;
import me.mattyhd0.koth.scoreboard.hook.plugin.KoTHScoreboardHook;

public class ScoreboardRHook extends ScoreboardHook {

    private final KoTHScoreboardHook kothScoreboard = new KoTHScoreboardHook();

    @Override
    public String getHookName() {
        return "Scoreboard-revision";
    }

    @Override
    public void onKothStart(CurrentKoth currentKoth) {
        // Fallback to internal scoreboard implementation when the external API is unavailable at compile-time.
        kothScoreboard.onKothStart(currentKoth);
    }

    @Override
    public void onKothEnd(CurrentKoth currentKoth) {
        kothScoreboard.onKothEnd(currentKoth);
    }

    @Override
    public void update(CurrentKoth currentKoth) {
        kothScoreboard.update(currentKoth);
    }

}
