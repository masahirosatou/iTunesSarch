package com.example.itunesmusicsearch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);




        TextView trackTextView = findViewById(R.id.track_text_view);
        String trackName = getIntent().getExtras().getString("track_name");
        trackTextView.setText(trackName);


        TextView artistTextView = findViewById(R.id.artist_text_view);
        String artistName = getIntent().getExtras().getString("artist_Name");
        artistTextView.setText(artistName);

        String previewUrl = getIntent().getExtras().getString("preview_url");
        if (!TextUtils.isEmpty(previewUrl)) {
            VideoView videoView = findViewById(R.id.video_view);
            videoView.setMediaController(new MediaController(this)); // 再生ボタンとかをつける
            videoView.setVideoURI(Uri.parse(previewUrl)); // URLを設定する
            videoView.start(); // 再生する
        }
        //https://qiita.com/PP_/items/94c704522ac6a6111797
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        try {
//            mediaPlayer.setDataSource(previewUrl);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.start();


        //参考URL→　http://kiwamunet.hateblo.jp/entry/2014/09/24/112005
        String artworkUrl100 = getIntent().getExtras().getString("artwork_Url100");
        ImageView imageView =  findViewById(R.id.image_view);
        ImageGetTask task = new ImageGetTask(imageView);
        task.execute(artworkUrl100);
    }




    class ImageGetTask extends AsyncTask<String,Void,Bitmap> {
        private ImageView image;

        public ImageGetTask(ImageView _image) {
            image = _image;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap image;
            try {
                URL imageUrl = new URL(params[0]);
                InputStream imageIs;
                imageIs = imageUrl.openStream();
                image = BitmapFactory.decodeStream(imageIs);
                return image;
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            // 取得した画像をImageViewに設定します。
            image.setImageBitmap(result);
        }
    }




}
