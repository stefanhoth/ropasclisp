package com.stefanhoth.ropasclisp.achievements;

import android.content.Context;

import com.stefanhoth.ropasclisp.games_sdk.PlayGamesClient;

public class AchievementObserver {

    private final PlayGamesClient playGamesClient;
    private final Context context;

    public AchievementObserver(PlayGamesClient playGamesClient, final Context context) {
        this.playGamesClient = playGamesClient;
        this.context = context;
    }

    public void onGameStart() {
        // first game start ever
        playGamesClient.unlockAchievement(ACHIEVEMENT.HELLO_THERE);

        DAILY_STATE dailyState = DAILY_STATE.with(context);

        // on X game starts per day
        if (!dailyState.isTodaysFirstStart()) {

            final int todaysStarts = dailyState.getTodaysStarts();
            if (ACHIEVEMENT.ADDICT.isGoalReached(todaysStarts)) {
                playGamesClient.unlockAchievement(ACHIEVEMENT.ADDICT);
            }
            if (ACHIEVEMENT.CONNOISSEUR.isGoalReached(todaysStarts)) {
                playGamesClient.unlockAchievement(ACHIEVEMENT.CONNOISSEUR);
            }

        }
    }

    public void onGameWin() {
        playGamesClient.unlockAchievement(ACHIEVEMENT.GOTCHA);
        playGamesClient.unlockAchievement(ACHIEVEMENT.ON_A_ROLL);
        playGamesClient.unlockAchievement(ACHIEVEMENT.POKER_FACE);
    }
}
