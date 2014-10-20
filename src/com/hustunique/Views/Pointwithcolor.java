package com.hustunique.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class Pointwithcolor  extends View{
	
	private int width=200;
	private int height=200;
	private Paint paint;
	private int color=Color.GREEN;
	public Pointwithcolor(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint=new Paint();
		paint.setStyle(Style.FILL);
		// TODO Auto-generated constructor stub
	}
	
	
	public Pointwithcolor(Context context){
		super(context);
		paint=new Paint();
		paint.setStyle(Style.FILL);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		this.height=this.getHeight();
		this.width=this.getWidth();
		paint.setColor(this.color);
		paint.setAntiAlias(true);
		canvas.drawCircle(width/2 ,height/2, height/2, paint);
	}

	public void setColor(int color){
		this.color=color;
		postInvalidate();
	}
	
	
}
