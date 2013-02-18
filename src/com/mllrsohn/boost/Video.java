package com.mllrsohn.boost;


import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.net.Uri;

public class Video extends Activity {

    /**
     * TODO: Set the path variable to a streaming video URL or a local media
     * file path.
     */
    private String path = "";
    private VideoView mVideoView;

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

            /*
             * Alternatively,for streaming media you can use
             * mVideoView.setVideoURI(Uri.parse(URLstring));
             */
            mVideoView.setVideoPath(path);
            mVideoView.requestFocus();
            mVideoView.start();

        }
    }
}
