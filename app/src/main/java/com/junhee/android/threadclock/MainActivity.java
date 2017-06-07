package com.junhee.android.threadclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    //TODO 강사님 코드처럼 옮겨보기 

    int deviceHeight;
    int deviceWidth;

    int center_x, center_y;

    int LINE = 0;

    double angle_sec = 0;
    double angle_mill = 0;
    double angle_min = 0;
    double end_x, end_y;

    CustomView stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        // 화면 세로길이
        deviceHeight = metrics.heightPixels;
        // 화면 가로넓이
        deviceWidth = metrics.widthPixels;
        // 중심점 가로
        center_x = deviceWidth / 2;
        // 중심점 세로
        center_y = deviceHeight / 2;
        // 선의길이
        LINE = center_x - 50;

        stage = new CustomView(getBaseContext());
        setContentView(stage);

        // 화면을 그려주는 Thread를 동작시킨다.
        new DrawStage_sec().start();
        new DrawStage_mill().start();
        new DrawStage_min().start();
    }

    class DrawStage_mill extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                angle_mill = angle_mill + 1;
                stage.postInvalidate();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class DrawStage_min extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                angle_min = angle_min + 6;
                stage.postInvalidate();
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 뷰를 1초에 한번 갱신하는 객체
    class DrawStage_sec extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                angle_sec = angle_sec + 30;
                stage.postInvalidate();
                try {
                    Thread.sleep(1000); // 1초간 동작을 멈춘다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class CustomView extends View {
        Paint paint_sec = new Paint();
        Paint paint_mill = new Paint();
        Paint paint_min = new Paint();

        double angle_temp = 0;

        public CustomView(Context context) {

            super(context);
            paint_min.setColor(Color.BLUE);
            paint_min.setStrokeWidth(20f);

            paint_sec.setColor(Color.RED);
            paint_sec.setStrokeWidth(10f);

            paint_mill.setColor(Color.LTGRAY);
            paint_mill.setStrokeWidth(5f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // 화면의 중앙부터 12시방향으로 직선을 긋는다
            angle_temp = angle_sec - 90;
            end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x; // x좌표 구하는 식
            end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y; // y좌표 구하는 식
            canvas.drawLine(center_x, center_y, (float) end_x, (float) end_y, paint_sec);

            angle_temp = angle_mill - 90;
            end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x; // x좌표 구하는 식
            end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y; // y좌표 구하는 식
            canvas.drawLine(center_x, center_y, (float) end_x, (float) end_y, paint_mill);

            angle_temp = angle_min - 90;
            end_x = Math.cos(angle_temp * Math.PI / 180) * LINE + center_x; // x좌표 구하는 식
            end_y = Math.sin(angle_temp * Math.PI / 180) * LINE + center_y; // y좌표 구하는 식
            canvas.drawLine(center_x, center_y, (float) end_x, (float) end_y, paint_min);
        }
    }
}