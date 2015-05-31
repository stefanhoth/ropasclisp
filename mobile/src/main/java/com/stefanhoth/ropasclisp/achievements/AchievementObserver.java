package com.stefanhoth.ropasclisp.achievements;

import com.stefanhoth.ropasclisp.games_sdk.PlayGamesClient;

public class AchievementObserver {

    private final PlayGamesClient playGamesClient;
    private final boolean firstSessionToday;

    public AchievementObserver(PlayGamesClient playGamesClient, boolean firstSessionToday) {
        this.playGamesClient = playGamesClient;
        this.firstSessionToday = firstSessionToday;
    }

    public void onGameStart() {
        // first game start ever
        playGamesClient.unlockAchievement(ACHIEVEMENT.HELLO_THERE);

        // on X game starts per day
        if(! firstSessionToday){
            playGamesClient.unlockAchievement(ACHIEVEMENT.CONNOISSEUR);
            playGamesClient.unlockAchievement(ACHIEVEMENT.ADDICT);
        }
    }

    public void onGameWin() {
        playGamesClient.unlockAchievement(ACHIEVEMENT.GOTCHA);
        playGamesClient.unlockAchievement(ACHIEVEMENT.ON_A_ROLL);
        playGamesClient.unlockAchievement(ACHIEVEMENT.POKER_FACE);
    }
}
