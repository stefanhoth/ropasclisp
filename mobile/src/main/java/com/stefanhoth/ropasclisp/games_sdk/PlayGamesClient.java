package com.stefanhoth.ropasclisp.games_sdk;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.novoda.notils.logger.simple.Log;
import com.stefanhoth.ropasclisp.GooglePlayServicesActivity;
import com.stefanhoth.ropasclisp.achievements.ACHIEVEMENT;

public class PlayGamesClient implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String KEY_IN_RESOLUTION = "is_in_resolution";

    private final GooglePlayServicesActivity activity;

    private GoogleApiClient gamesApiClient;

    /**
     * Determines if the client is in a resolution state, and
     * waiting for resolution intent to return.
     */
    private boolean currentlyResolvingErrorState;

    public PlayGamesClient(final GooglePlayServicesActivity activity) {
        this.activity = activity;
    }

    public void connect() {
        if (gamesApiClient == null) {
            gamesApiClient = new GoogleApiClient.Builder(activity)
                    .addApi(Games.API)
                    .addScope(Games.SCOPE_GAMES)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        gamesApiClient.connect();
    }

    public void retryConnecting() {
        currentlyResolvingErrorState = false;
        if (!gamesApiClient.isConnecting()) {
            gamesApiClient.connect();
        }
    }

    public void disconnect() {
        if (gamesApiClient != null) {
            gamesApiClient.disconnect();
        }
    }

    /**
     * Called when {@code gamesApiClient} is connected.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i("GoogleApiClient connected");
        activity.onGameReady();
    }

    /**
     * Called when {@code gamesApiClient} connection is suspended.
     */
    @Override
    public void onConnectionSuspended(int cause) {
        Log.i("GoogleApiClient connection suspended");
        retryConnecting();
    }

    /**
     * Called when {@code gamesApiClient} is trying to connect but failed.
     * Handle {@code result.getResolution()} if there is a resolution
     * available.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("GoogleApiClient connection failed: ", result.toString());
        if (!result.hasResolution()) {
            // Show a localized error dialog.
            GooglePlayServicesUtil.getErrorDialog(
                    result.getErrorCode(), null, 0, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            retryConnecting();
                        }
                    }).show();
            return;
        }
        // If there is an existing resolution error being displayed or a resolution
        // activity has started before, do nothing and wait for resolution
        // progress to be completed.
        if (currentlyResolvingErrorState) {
            return;
        }
        currentlyResolvingErrorState = true;
        try {
            activity.startPlayGamesErrorResolution(result);
        } catch (IntentSender.SendIntentException e) {
            Log.e(e, "Exception while starting resolution activity");
            retryConnecting();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_IN_RESOLUTION, currentlyResolvingErrorState);
    }

    public void restoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            return;
        }

        currentlyResolvingErrorState = savedInstanceState.getBoolean(KEY_IN_RESOLUTION, false);
    }

    public void unlockAchievement(ACHIEVEMENT achievement) {
        if(gamesApiClient == null || ! gamesApiClient.isConnected()){
            Log.w("Could not unlock achievement - gamesApiClient in invalid state");
            return;
        }

        if(achievement.isIncremental()){
            Games.Achievements.increment(gamesApiClient, achievement.getAchievementId(activity), 1);
        } else {
            Games.Achievements.unlock(gamesApiClient, achievement.getAchievementId(activity));
        }
    }

    public Intent getDisplayAchievementsIntent() {
        return Games.Achievements.getAchievementsIntent(gamesApiClient);
    }

}
