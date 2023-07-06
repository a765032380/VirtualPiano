package com.adesk.pianokeyboardlib.musicalsensors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.adesk.pianokeyboardlib.R;

import java.util.ArrayList;

public class ScoreSheet extends View {

    private Canvas canvas;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ArrayList<Note> track = new ArrayList<>();

    public ScoreSheet(Context context) {
        super(context);
    }

    public ScoreSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScoreSheet(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addTrack(String song){
            int length = Integer.parseInt(song.substring(0,1));
            String note;
            double freq;
            int octave;
            if(song.length() == 4) {
                note = song.substring(1,3);
                octave = Integer.parseInt(song.substring(3));
            } else {
                note = song.substring(1,2);
                octave = Integer.parseInt(song.substring(2));
            }
            track.add(new Note(length,note,octave));
    }


    public void drawStave(Canvas canvas, int x, int y){
        int spacing = 50;
        y+=spacing;
        int width = canvas.getWidth();
        canvas.drawLine(x,y,width,y,paint);
        y+=spacing;
        canvas.drawLine(x,y,width,y,paint);
        y+=spacing;
        canvas.drawLine(x,y,width,y,paint);
        y+=spacing;
        canvas.drawLine(x,y,width,y,paint);
        y+=spacing;
        canvas.drawLine(x,y,width,y,paint);
    }

    public void drawSemibreve(Note note, int x, int y){
        drawStave(canvas, x, y);
        Bitmap symbol = BitmapFactory.decodeResource(getResources(),R.drawable.semibreve);
        Positioner pos = getNotePosition(note,x,y);
        y = pos.getPos();
        if(!pos.isDown()){
            y+=137;
        }
        switch (pos.accidentals) {
            case 1:
                drawSharp(x, y, true);
                break;
            case 2:
                drawFlat(x, y, true);
                break;
        }

        canvas.drawBitmap(symbol, null, new Rect(x,y-2,x+77,y+46), null);
    }

    private void drawSharp(int x, int y, boolean down) {
        y-=10;
        x-=40;
        if(!down){
            y+=130;
        }
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sharp),
                                null, new Rect(x,y,x+23,y+80), null);
    }

    private void drawFlat(int x, int y, boolean down) {
        y-=25;
        x-=32;
        if(!down){
            y+=130;
        }
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.flat),
                null, new Rect(x,y,x+21,y+70), null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        int MAX_WIDTH = canvas.getWidth();
        int x = 40;
        int y = 0;
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.treble_clef),
                                null, new Rect(x,y,x+150,y+375),null);

        canvas.drawLine(x+165,y+90,x+165,y+290,paint);
        y += 40;
        drawStave(canvas, x,y);
        drawStave(canvas, x+90,y);
        drawStave(canvas, x+120,y);
        x+=220;
        for(Note note : track){
            drawSemibreve(note, x, y);
            if(x > MAX_WIDTH-150){
                y += 350;
                x = 40;
            } else x+=120;

        }
    }

    public Positioner getNotePosition(Note note, int x, int y){
        y+=2;
        int downDist = 25;
        boolean drawDown = false;
        String theNote;
        int accidentals = 0;
        if(note.getNote().length()==2){
            theNote = note.getNote().substring(0,1);
            if(note.getNote().substring(1).equals("#")){
                accidentals = 1;
            } else if(note.getNote().substring(1).equals("b")){
                accidentals = 2;
            }
        } else {
            theNote = note.getNote();
        }
        if(theNote.equals("G") && note.getOctave() == 5){
            drawDown = true;
        } else if(theNote.equals("F") && note.getOctave() == 5){
            y += downDist;
            drawDown = true;
        } else if(theNote.equals("E") && note.getOctave() == 5){
            y += (downDist * 2);
            drawDown = true;
        } else if(theNote.equals("D") && note.getOctave() == 5){
            y += (downDist * 3);
            drawDown = true;
        } else if(theNote.equals("C") && note.getOctave() == 5){
            y += (downDist * 4);
            drawDown = true;
        } else if(theNote.equals("B") && note.getOctave() == 4){
            y += (downDist * 5);
            drawDown = true;
        } else if(theNote.equals("A") && note.getOctave() == 4){
            y += 13;
            drawDown = false;
        } else if(theNote.equals("G") && note.getOctave() == 4){
            y += 13 + downDist;
            drawDown = false;
        } else if(theNote.equals("F") && note.getOctave() == 4){
            y += 13 + (downDist * 2);
            drawDown = false;
        } else if(theNote.equals("E") && note.getOctave() == 4){
            y += 13 + (downDist * 3);
            drawDown = false;
        } else if(theNote.equals("D") && note.getOctave() == 4){
            y += 13 + (downDist * 4);
            drawDown = false;
        } else if(note.getOctave() <= 4){
            y += 20 + (downDist * 5);
            paint.setStrokeWidth(9);
            canvas.drawLine(x-10,y+133,x+70,y+133,paint);
            paint.setStrokeWidth(5);
            drawDown = false;
        } else {
            paint.setStrokeWidth(9);
            canvas.drawLine(x-10,y+10,x+70,y+10,paint);
            paint.setStrokeWidth(5);
            y-=40;
            drawDown = true;
        }
        return new Positioner(y, drawDown, accidentals);
    }

    public class Positioner{
        private int pos;
        private boolean drawDown;
        private int accidentals;

        public Positioner(int pos, boolean drawDown, int accidentals){
            this.pos = pos;
            this.drawDown = drawDown;
            this.accidentals = accidentals;
        }

        public int getPos() {
            return pos;
        }

        public boolean isDown() {
            return drawDown;
        }

        public int getAccidentals() {
            return accidentals;
        }
    }
}
