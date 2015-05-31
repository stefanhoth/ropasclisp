package com.stefanhoth.ropasclisp;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.stefanhoth.ropasclisp.achievements.AchievementObserver;
import com.stefanhoth.ropasclisp.games_sdk.PlayGamesClient;
import com.stefanhoth.ropasclisp.achievements.DAILY_STATE;

public class GooglePlayServicesActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_RESOLUTION = 1;
    private static final int CODE_REQUEST_ACHIEVEMENTS = 42;

    private PlayGamesClient playGamesClient;
    private AchievementObserver achievementObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (playGamesClient == null) {
            playGamesClient = new PlayGamesClient(this);
        }

        playGamesClient.restoreInstanceState(savedInstanceState);

        achievementObserver = new AchievementObserver(playGamesClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        playGamesClient.connect();
    }

    @Override
    protected void onStop() {
        playGamesClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        playGamesClient.onSaveInstanceState(outState);
    }

    public void showAchievements() {
        startActivityForResult(playGamesClient.getDisplayAchievementsIntent(), CODE_REQUEST_ACHIEVEMENTS);
    }

    public void startPlayGamesErrorResolution(ConnectionResult result) throws IntentSender.SendIntentException {
        result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_RESOLUTION:
                playGamesClient.retryConnecting();
                break;
        }
    }

    public void onGameReady() {
        achievementObserver.onGameStart();
        DAILY_STATE.with(this).countAppStart();
    }

    public void onGameWin() {
        achievementObserver.onGameWin();
    }

}
