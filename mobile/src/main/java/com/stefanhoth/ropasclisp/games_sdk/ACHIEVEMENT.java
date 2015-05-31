package com.stefanhoth.ropasclisp.games_sdk;

import android.content.Context;
import android.support.annotation.StringRes;

import com.stefanhoth.ropasclisp.R;

public enum ACHIEVEMENT {

    HELLO_THERE(R.string.achievement_hellothere, TYPE.STATIC),
    GOTCHA(R.string.achievement_gotcha, TYPE.STATIC),
    ON_A_ROLL(R.string.achievement_onaroll, TYPE.INCREMENTAL),
    POKER_FACE(R.string.achievement_pokerface, TYPE.INCREMENTAL),
    CONNOISSEUR(R.string.achievement_connoisseur, TYPE.INCREMENTAL),
    ADDICT(R.string.achievement_addict, TYPE.INCREMENTAL);

    private final int achievementId;
    private final TYPE achievementType;

    private enum TYPE{
        STATIC, INCREMENTAL;
    }

    ACHIEVEMENT(@StringRes int achievementId, TYPE achievementType) {
        this.achievementId = achievementId;
        this.achievementType = achievementType;
    }

    public String getAchievementId(final Context context) {
        return context.getString(achievementId);
    }

    public boolean isIncremental() {
        return achievementType == TYPE.INCREMENTAL;
    }
}
