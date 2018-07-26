package com.tefsalkw.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.tefsalkw.R;

import static com.tefsalkw.utils.Contents.baseVideoURL;

public class CustomVideoPlayerNewActivity extends AppCompatActivity implements Player.EventListener    {

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;

    private Timeline.Window window;
    private DataSource.Factory mediaDataSourceFactory;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private BandwidthMeter bandwidthMeter;


    private String videoLink = baseVideoURL + "Tefsal_Intro_1.mp4";
    private String videoLink1 = baseVideoURL + "Tefsal_Neck_1.mp4";
    private String videoLink2 = baseVideoURL + "Tefsal_Shoulders_1.mp4";
    private String videoLink3 = baseVideoURL + "Tefsal_Chest_1.mp4";
    private String videoLink4 = baseVideoURL + "Tefsal_Waist_1.mp4";
    private String videoLink5 = baseVideoURL + "Tefsal_Arm_1.mp4";
    private String videoLink6 = baseVideoURL + "Tefsal_Wrist_1.mp4";
    private String videoLink7 = baseVideoURL + "Tefsal_FrontH_1.mp4";
    private String videoLink8 = baseVideoURL + "Tefsal_BackH_1.mp4";

    String[] literals = {videoLink, videoLink1, videoLink2, videoLink3, videoLink4, videoLink5, videoLink6, videoLink7, videoLink8};

    int currentVideoIs = 0;

    boolean isFirstTime = false;
    DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
    MediaSource mediaSource = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_video_player_new);
        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);
        window = new Timeline.Window();



    }


    private void initializePlayer() {

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);

        simpleExoPlayerView.requestFocus();

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);


        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        player.addListener(this);

        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(shouldAutoPlay);

        Intent intent = getIntent();
        Integer positionIs = intent.getIntExtra("position", 0);

        if (positionIs == 0) {
            isFirstTime = true;

        }

        String playerUrl = "";

        if (positionIs < 9) {
            playerUrl = literals[positionIs];

            Log.d("playerUrl", playerUrl);

        }



         mediaSource = new ExtractorMediaSource(Uri.parse(playerUrl),
                mediaDataSourceFactory, extractorsFactory, null, null);

         player.prepare(mediaSource);


    }


    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if (playbackState == ExoPlayer.STATE_ENDED){
            currentVideoIs++;


            Log.d("onCompletion", "" + currentVideoIs);


            int maxVideosToPlay = isFirstTime ? 1 : 8;
            if (currentVideoIs <= maxVideosToPlay) {


                mediaSource = new ExtractorMediaSource(Uri.parse(literals[currentVideoIs]),
                        mediaDataSourceFactory, extractorsFactory, null, null);

                player.prepare(mediaSource);

            }

        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
