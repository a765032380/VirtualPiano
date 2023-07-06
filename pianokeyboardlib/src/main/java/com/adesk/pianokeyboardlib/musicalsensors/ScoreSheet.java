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
//    public void setTrack(String song){
//        String[] notesStr = song.split(" ");
//        track = new ArrayList<>();
//        for(String noteIn : notesStr){
//            int length = Integer.parseInt(noteIn.substring(0,1));
//            String note;
//            double freq;
//            int octave;
//            if(noteIn.length() == 4) {
//                note = noteIn.substring(1,3);
//                octave = Integer.parseInt(noteIn.substring(3));
//            } else {
//                note = noteIn.substring(1,2);
//                octave = Integer.parseInt(noteIn.substring(2));
//            }
//            track.add(new Note(length,note,octave));
//        }
//    }

    public void addTrack(String song){
        Log.e("LLLLLL","song="+song);
        track.add(new Note(song));
    }

    public void drawStave(Canvas canvas, int x, int y){
        int spacing = 21;
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
        int drawable = 0;
        if (note.getNote().equals("A")){drawable = R.drawable.icon_semibreve_27;
        }else if (note.getNote().equals("B")){drawable = R.drawable.icon_semibreve_26a;
        }else if (note.getNote().equals("C")){ drawable = R.drawable.icon_semibreve_25;
        }else if (note.getNote().equals("D")){ drawable = R.drawable.icon_semibreve_24;
        }else if (note.getNote().equals("E")){ drawable = R.drawable.icon_semibreve_23a;
        }else if (note.getNote().equals("F")){drawable = R.drawable.icon_semibreve_22;
        }else if (note.getNote().equals("G")){drawable = R.drawable.icon_semibreve_21a;
        }
        Bitmap symbol = BitmapFactory.decodeResource(getResources(),drawable);

        canvas.drawBitmap(symbol, null, new Rect(x,0,x+symbol.getWidth(),symbol.getHeight()), null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        int MAX_WIDTH = canvas.getWidth();
        int x = 0;
        int y = 0;
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_semibreve_treble_clef),
//                                null, new Rect(30,y,30+(21*3),y+(59*3)),null);
//        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(5);
//
//        y += 11*3;
//        canvas.drawLine(0,y+21,0,y+(5+21*5),paint);
//        canvas.drawLine(MAX_WIDTH,y+21,MAX_WIDTH,y+(5+21*5),paint);

        Bitmap line = BitmapFactory.decodeResource(getResources(),R.drawable.icon_semibreve_line);

        canvas.drawBitmap(line,
                null, new Rect(0,0,MAX_WIDTH,line.getHeight()),null);
        paint.setColor(Color.parseColor("#D8D8D8"));
        paint.setStrokeWidth(3);

//        drawStave(canvas, x+3,y);
//        drawStave(canvas, x+90,y);
//        drawStave(canvas, x+120,y);
        x+=20;
        for(Note note : track){
            drawSemibreve(note, x, y);
            if(x > MAX_WIDTH-150){
                x = MAX_WIDTH/6 - 30;
            } else x+=MAX_WIDTH/6 - 30;

        }
    }
}
