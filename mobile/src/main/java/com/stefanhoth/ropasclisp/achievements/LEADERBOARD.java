package com.stefanhoth.ropasclisp.achievements;

import android.content.Context;
import android.support.annotation.StringRes;

import com.stefanhoth.ropasclisp.R;

public enum LEADERBOARD {

    PLAYED_GAMES(R.string.leaderboard_playedgames),
    WON_IN_A_ROW(R.string.leaderboard_wongamesinarow);

    private final int leaderboardId;

    LEADERBOARD(@StringRes int leaderboardId) {
        this.leaderboardId = leaderboardId;
    }

    public String getLeaderboardId(final Context context) {
        return context.getString(leaderboardId);
    }

}
