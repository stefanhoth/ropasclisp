package com.stefanhoth.ropasclisp;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.stefanhoth.ropasclisp.games_sdk.PlayGamesClient;

public class GooglePlayServicesActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_RESOLUTION = 1;

    private PlayGamesClient playGamesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (playGamesClient == null) {
            playGamesClient = new PlayGamesClient(this);
        }

        playGamesClient.restoreInstanceState(savedInstanceState);
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
}
