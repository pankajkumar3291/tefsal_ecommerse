package com.tefsalkw.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.tefsalkw.R;

import static com.tefsalkw.utils.Contents.baseVideoURL;

public class CustomVideoPlayerActivity extends AppCompatActivity implements EasyVideoCallback {
    private EasyVideoPlayer player;
    //  Tefsal_Arm.mp4  Tefsal_BackH.mp4  Tefsal_Chest.mp4  Tefsal_FrontH.mp4  Tefsal_Intro.mp4  Tefsal_Neck.mp4  Tefsal_Shoulders.mp4  Tefsal_Waist.mp4  Tefsal_Wrist.mp4

    private String videoLink = baseVideoURL + "Tefsal_Intro.mp4";
    private String videoLink1 = baseVideoURL + "Tefsal_Neck.mp4";
    private String videoLink2 = baseVideoURL + "Tefsal_Shoulders.mp4";
    private String videoLink3 = baseVideoURL + "Tefsal_Chest.mp4";
    private String videoLink4 = baseVideoURL + "Tefsal_Waist.mp4";
    private String videoLink5 = baseVideoURL + "Tefsal_Arm.mp4";
    private String videoLink6 = baseVideoURL + "Tefsal_Wrist.mp4";
    private String videoLink7 = baseVideoURL + "Tefsal_FrontH.mp4";
    private String videoLink8 = baseVideoURL + "Tefsal_BackH.mp4";

    String[] literals = {videoLink, videoLink1, videoLink2, videoLink3, videoLink4, videoLink5, videoLink6, videoLink7, videoLink8};

    int currentVideoIs = 0;

    boolean isFirstTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_video_player);


        // Grabs a reference to the player view
        player = (EasyVideoPlayer) findViewById(R.id.player);

        // Sets the callback to this Activity, since it inherits EasyVideoCallback
        player.setCallback(this);

        Intent intent = getIntent();
        Integer positionIs = intent.getIntExtra("position", 0);

        if (positionIs == 0) {
            isFirstTime = true;

        }

        if (positionIs < 9) {
            String playerUrl = literals[positionIs];

            Log.d("playerUrl", playerUrl);

            // Sets the source to the HTTP URL held in the TEST_URL variable.
            // To play files, you can use Uri.fromFile(new File("..."))
            player.setSource(Uri.parse(playerUrl));
        }


    }

    @Override
    public void onStarted(EasyVideoPlayer easyVideoPlayer) {

    }

    @Override
    public void onPaused(EasyVideoPlayer easyVideoPlayer) {

    }

    @Override
    public void onPreparing(EasyVideoPlayer easyVideoPlayer) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer easyVideoPlayer) {

    }

    @Override
    public void onBuffering(int i) {

    }

    @Override
    public void onError(EasyVideoPlayer easyVideoPlayer, Exception e) {

    }

    @Override
    public void onCompletion(EasyVideoPlayer easyVideoPlayer) {

        currentVideoIs++;


        Log.d("onCompletion", "" + currentVideoIs);


        int maxVideosToPlay = isFirstTime ? 1 : 8;
        if (currentVideoIs <= maxVideosToPlay) {


            player.setSource(Uri.parse(literals[currentVideoIs]));
            // Starts or resumes playback.
            //  player.start();


        }

    }

    @Override
    public void onRetry(EasyVideoPlayer easyVideoPlayer, Uri uri) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer easyVideoPlayer, Uri uri) {

        player.reset();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (player != null)
            player.reset();

    }
}
