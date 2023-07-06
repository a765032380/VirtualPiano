package com.adesk.virtualpiano.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.adesk.mvpframe.base.BaseActivity;
import com.adesk.mvpframe.base.BasePresenter;
import com.adesk.pianokeyboardlib.keyboard.Key;
import com.adesk.pianokeyboardlib.keyboard.PianoKeyBoard;
import com.adesk.pianokeyboardlib.musicalsensors.ScoreSheet;
import com.adesk.pianokeyboardlib.sound.SoundPlayUtils;
import com.adesk.virtualpiano.R;
import com.adesk.virtualpiano.mvp.contract.PianoMainContract;
import com.adesk.virtualpiano.mvp.presenter.PianoMainPresenter;

/**
 * @author tony
 */
public class PianoMainActivity extends BaseActivity implements PianoMainContract.View {

    SeekBar mSeekBar;
    PianoKeyBoard mKeyboard;
    ScoreSheet scoresheet;

    protected void playTrack(String song) {
        scoresheet.addTrack(song);
        scoresheet.invalidate();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoundPlayUtils.init(getApplicationContext());
        mKeyboard = findViewById(R.id.keyboard);
        mSeekBar = findViewById(R.id.seek_bar);
        scoresheet = (ScoreSheet) findViewById(R.id.scoresheet_area);
        mKeyboard.setKeyListener(new PianoKeyBoard.KeyListener() {
            @Override
            public void onKeyPressed(Key key) {

            }

            @Override
            public void onKeyUp(Key key) {
//                playTrack("1G#5 2Fb5 3E#5 4Db5 4C#5 6Bb4 8A#4 1Gb4 2F#4 3Eb4 4D#4 6Cb4");
//                key.getKeyCode();
                playTrack(key.getTextToDraw());
            }

            @Override
            public void currentFirstKeyPosition(int position) {

            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mKeyboard.moveToPosition(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setMax(mKeyboard.getMaxMovePosition());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public BasePresenter setPresenter() {
        return new PianoMainPresenter();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

}
