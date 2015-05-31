package com.stefanhoth.ropasclisp.achievements;

import android.content.Context;
import android.support.annotation.StringRes;

import com.stefanhoth.ropasclisp.R;

public enum ACHIEVEMENT {

    HELLO_THERE(R.string.achievement_hellothere, TYPE.STATIC, 1),
    GOTCHA(R.string.achievement_gotcha, TYPE.STATIC, 1),
    ON_A_ROLL(R.string.achievement_onaroll, TYPE.INCREMENTAL),
    POKER_FACE(R.string.achievement_pokerface, TYPE.INCREMENTAL),
    CONNOISSEUR(R.string.achievement_connoisseur, TYPE.STATIC, 5),
    ADDICT(R.string.achievement_addict, TYPE.STATIC, 25);

    public static final int NO_GOAL_AMOUNT = 0;
    private final int achievementId;
    private final TYPE achievementType;
    private final int goalAmount;

    private enum TYPE{
        STATIC, INCREMENTAL;
    }

    ACHIEVEMENT(@StringRes int achievementId, TYPE achievementType) {
        this(achievementId, achievementType, NO_GOAL_AMOUNT);
    }

    ACHIEVEMENT(@StringRes int achievementId, TYPE achievementType, int goalAmount) {
        this.achievementId = achievementId;
        this.achievementType = achievementType;
        this.goalAmount = goalAmount;
    }

    public String getAchievementId(final Context context) {
        return context.getString(achievementId);
    }

    public boolean isIncremental() {
        return achievementType == TYPE.INCREMENTAL;
    }

    public boolean isGoalReached(final int actual) {
        return actual >= goalAmount;
    }
}
