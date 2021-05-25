package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;

public class PaintView extends View implements View.OnTouchListener {
    //aktualna farba
    Paint current_paint;
    //lista scieżek do rysowania
    List<Path> path_list = null;
    //lista farb dla ścieżek
    List<Paint> paint_list = null;
    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public PaintView(Context context) {
        super(context);
        init();
    }

    private void init() {
        //aktualna farba
        current_paint = new Paint();
        current_paint.setAntiAlias(true);
        current_paint.setColor(Color.BLUE);
        current_paint.setStyle(Paint.Style.STROKE);
        current_paint.setStrokeWidth(50);
        paint_list = new ArrayList<Paint>();
        path_list = new ArrayList<Path>();
        setOnTouchListener(this);

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x, y;
        int i;
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // utworzenie nowej ścieżki i nowej farby
                Path p = new Path();
                Paint paint = new Paint(current_paint);
                //start sieżki
                p.moveTo(x, y);
                paint_list.add(paint);
                path_list.add(p);
                break;
            case MotionEvent.ACTION_MOVE:
                i = path_list.size();
                //koniec ścieżki
                path_list.get(i - 1).lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }
    @Override
    protected void onDraw(Canvas c) {
        int i;
        if (path_list.size() > 0) {
            for (i = 0; i < path_list.size(); i++)
                c.drawPath(path_list.get(i), paint_list.get(i));
        }
    }
}
