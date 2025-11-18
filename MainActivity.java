package com.example.drawingtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    
    private DrawingView drawingView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        
        // Botões de cores
        LinearLayout colorButtons = new LinearLayout(this);
        colorButtons.setOrientation(LinearLayout.HORIZONTAL);
        
        Button blackBtn = new Button(this);
        blackBtn.setText("⚫");
        blackBtn.setOnClickListener(v -> drawingView.setColor(Color.BLACK));
        
        Button redBtn = new Button(this);
        redBtn.setText("🔴");
        redBtn.setOnClickListener(v -> drawingView.setColor(Color.RED));
        
        Button blueBtn = new Button(this);
        blueBtn.setText("🔵");
        blueBtn.setOnClickListener(v -> drawingView.setColor(Color.BLUE));
        
        Button clearBtn = new Button(this);
        clearBtn.setText("🗑️ LIMPAR");
        clearBtn.setOnClickListener(v -> drawingView.clearCanvas());
        
        colorButtons.addView(blackBtn);
        colorButtons.addView(redBtn);
        colorButtons.addView(blueBtn);
        colorButtons.addView(clearBtn);
        
        // Área de desenho
        drawingView = new DrawingView(this);
        
        layout.addView(colorButtons);
        layout.addView(drawingView);
        
        setContentView(layout);
    }
    
    class DrawingView extends View {
        private Paint paint;
        private Path path;
        
        public DrawingView(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            path = new Path();
        }
        
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            canvas.drawPath(path, paint);
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(x, y);
                    invalidate();
                    break;
            }
            return true;
        }
        
        public void setColor(int color) {
            paint.setColor(color);
        }
        
        public void clearCanvas() {
            path.reset();
            invalidate();
        }
    }
}