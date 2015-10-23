package com.stefanhoth.ropasclisp.playing;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.stefanhoth.ropasclisp.R;

public enum HandSign {

    LIZARD(R.drawable.vector_lizard, R.string.sign_lizard),
    PAPER(R.drawable.vector_paper, R.string.sign_paper),
    ROCK(R.drawable.vector_rock, R.string.sign_rock),
    SCISSORS(R.drawable.vector_scissors, R.string.sign_scissors),
    SPOCK(R.drawable.vector_spock, R.string.sign_spock);

    @DrawableRes
    private int vectorResId;
    @StringRes
    private int nameResId;

    HandSign(@DrawableRes int vector, @StringRes int name) {
        vectorResId = vector;
        nameResId = name;
    }

    public int getVectorResId() {
        return vectorResId;
    }

    public int getNameResId() {
        return nameResId;
    }

}
