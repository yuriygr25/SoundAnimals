package com.example.yura.soundanimals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCowSound, mDogSound, mDuckSound, mSheepSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            // Для устройств до Android 5
//            createOldSoundPool();
//        } else {
//            // Для новых устройств
//            createNewSoundPool();
//        }

//        mAssetManager = getAssets();
//
//        // получим идентификаторы
//        mCatSound = loadSound("cat.ogg");
//        mChickenSound = loadSound("chicken.ogg");
//        mCowSound = loadSound("cow.ogg");
//        mDogSound = loadSound("dog.ogg");
//        mDuckSound = loadSound("duck.ogg");
//        mSheepSound = loadSound("sheep.ogg");

        ImageButton cowImageButton = (ImageButton) findViewById(R.id.imageButtonCow);
        //cowImageButton.setOnClickListener(onClickListener);

        ImageButton chickenImageButton = (ImageButton) findViewById(R.id.imageButtonChicken);
        chickenImageButton.setOnClickListener(onClickListener);

        ImageButton catImageButton = (ImageButton) findViewById(R.id.imageButtonCat);
        catImageButton.setOnClickListener(onClickListener);

        ImageButton duckImageButton = (ImageButton) findViewById(R.id.imageButtonDuck);
        duckImageButton.setOnClickListener(onClickListener);

        ImageButton sheepImageButton = (ImageButton) findViewById(R.id.imageButtonSheep);
        sheepImageButton.setOnClickListener(onClickListener);

        ImageButton dogImageButton = (ImageButton) findViewById(R.id.imageButtonDog);
        dogImageButton.setOnClickListener(onClickListener);


        cowImageButton.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int eventAction = event.getAction();
                if (eventAction == MotionEvent.ACTION_UP) {
                    // Отпускаем палец
                    if (mStreamID > 0)
                        mSoundPool.stop(mStreamID);
                }
                if (eventAction == MotionEvent.ACTION_DOWN) {
                    // Нажимаем на кнопку
                    mStreamID = playSound(mCowSound);
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    mSoundPool.stop(mStreamID);
                }
                return true;
            }
        }
        );
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButtonCow:
                    playSound(mCowSound);
                    break;
                case R.id.imageButtonChicken:
                    playSound(mChickenSound);
                    break;
                case R.id.imageButtonCat:
                    playSound(mCatSound);
                    break;
                case R.id.imageButtonDuck:
                    playSound(mDuckSound);
                    break;
                case R.id.imageButtonSheep:
                    playSound(mSheepSound);
                    break;
                case R.id.imageButtonDog:
                    playSound(mDogSound);
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mCatSound = loadSound("cat.mp3");
        mChickenSound = loadSound("chicken.mp3");
        mCowSound = loadSound("cow.mp3");
        mDogSound = loadSound("dog.mp3");
        mDuckSound = loadSound("duck.mp3");
        mSheepSound = loadSound("sheep.mp3");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}

