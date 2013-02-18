package com.mllrsohn.boost;


import android.app.Activity;
import android.os.Bundle;

import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnCompletionListener;

import android.net.Uri;

import android.view.View;
import android.view.MotionEvent;

public class Video extends Activity {

    /**
     * TODO: Set the path variable to a streaming video URL or a local media
     * file path.
     */
    private String path = "";
    private VideoView mVideoView;
    private MediaController ctlr;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.video);
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        path = getIntent().getStringExtra("videoURL");

        System.out.println("----------------videoURL----------------");
        System.out.println(path);
        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(
                    Video.this,
                    "Please edit VideoViewDemo Activity, and set path"
                            + " variable to your media file URL/path",
                    Toast.LENGTH_LONG).show();
        } else {

            mVideoView.setVideoPath(path);
            ctlr = new MediaController(this);
            ctlr.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(ctlr);
            ctlr.setVisibility(View.GONE);
            mVideoView.requestFocus();
            mVideoView.start();
/*
            // Looping
            mVideoView.setOnPreparedListener (new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
*/
            // Close on End
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finish();
                }
            });

            // Close on touch
            mVideoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    finish();
                    return true;
                }
            });
        }
    }
}
