package com.tefal.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.tefal.R;

public class CustomVideoPlayerActivity extends AppCompatActivity implements EasyVideoCallback {
    private EasyVideoPlayer player;

    private String videoLink = "http://tefsalkw.com/public/videos/app.3gp";
    private String videoLink1 = "http://tefsalkw.com/public/videos/app_video_neck.3gp";
    private String videoLink2 = "http://tefsalkw.com/public/videos/app_video_custom.3gp";
    private String videoLink3 = "http://tefsalkw.com/public/videos/app_video_chest.3gp";
    private String videoLink4 = "http://tefsalkw.com/public/videos/app_video_waist.3gp";
    private String videoLink5 = "http://tefsalkw.com/public/videos/app_video_arm.3gp";
    private String videoLink6 = "http://tefsalkw.com/public/videos/app_video_wrist.3gp";
    private String videoLink7 = "http://tefsalkw.com/public/videos/app_video_f_height.3gp";
    private String videoLink8 = "http://tefsalkw.com/public/videos/app_video_b_height.3gp";

    String[] literals = {videoLink, videoLink1,videoLink2, videoLink3,  videoLink4, videoLink5, videoLink6, videoLink7, videoLink8};

    int currentVideoIs = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_video_player);


        // Grabs a reference to the player view
        player = (EasyVideoPlayer) findViewById(R.id.player);

        // Sets the callback to this Activity, since it inherits EasyVideoCallback
        player.setCallback(this);

        Intent intent = getIntent();
        Integer positionIs = intent.getIntExtra("position",0);

        String playerUrl = literals[positionIs];

        Log.d("playerUrl",playerUrl);

        // Sets the source to the HTTP URL held in the TEST_URL variable.
        // To play files, you can use Uri.fromFile(new File("..."))
        player.setSource(Uri.parse(playerUrl));

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



        Log.d("onCompletion", ""+currentVideoIs);



        if (currentVideoIs <= 8) {



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

    }
}
