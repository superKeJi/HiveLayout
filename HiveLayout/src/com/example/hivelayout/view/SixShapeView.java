package com.example.hivelayout.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SixShapeView extends LinearLayout {
	private int shapeWith;
	private int shapeHeight;
	private int screenWith;
	private int screenHeight;
	private int devideDp = 20;
	private List<SixShape> sixShaps;
	private Context context;
	/**
	 * ·ä³²ÏÂL„Ó—lÁô°×¸ß¶È
	 */
	private int abstractButtonHeight = 700;
	public SixShapeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

		screenWith = wm.getDefaultDisplay().getWidth();


	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (sixShaps != null && sixShaps.size() != 0) {
			int item = (int) sixShaps.size() / 5;
			int itemLast = sixShaps.size() % 5;
			if (itemLast != 0) {
				item = item + 1;
			}
			int h = item * (shapeHeight * 3 / 2 + devideDp * 2) + 1 / 4 * shapeHeight + abstractButtonHeight;
			int measureWith = 0;
			int measureHeight = 0;
			if (screenWith > (shapeWith * 3 + devideDp * 4)) {
				measureWith = screenWith;
			} else {
				measureWith = shapeWith * 3 + devideDp * 4;
			}
			if (screenHeight > h) {
				measureHeight = screenHeight;
			} else {
				measureHeight = h;
			}
			setMeasuredDimension(measureWith, measureHeight);
		} else {
			setMeasuredDimension(screenWith, screenHeight);
		}
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			child.measure(shapeWith, shapeHeight);
		}
	}

	/**
	 * onLayout
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childShapView = getChildAt(i);
			int left = 0;
			int top = 0;

			int mitemLast = (i + 1) % 5;
			int mitem = (int) i / 5;
			switch (mitemLast) {
			case 1:
				left = screenWith / 2 - shapeWith - devideDp / 2;
				top = mitem * (shapeHeight + shapeHeight * 1 / 2 + devideDp + devideDp);
				childShapView.layout(left, top, left + shapeWith, top + shapeHeight);
				break;
			case 2:
				left = screenWith / 2 + devideDp / 2;
				top = mitem * (shapeHeight + shapeHeight * 1 / 2 + devideDp + devideDp);
				childShapView.layout(left, top, left + shapeWith, top + shapeHeight);
				break;
			case 3:
				left = screenWith / 2 - shapeWith / 2;
				top = mitem * (shapeHeight + shapeHeight * 1 / 2 + devideDp + devideDp) + shapeHeight * 3 / 4
						+ devideDp;
				childShapView.layout(left, top, left + shapeWith, top + shapeHeight);
				break;
			case 4:
				left = screenWith / 2 - shapeWith / 2 - shapeWith - devideDp;
				top = mitem * (shapeHeight + shapeHeight * 1 / 2 + devideDp + devideDp) + shapeHeight * 3 / 4
						+ devideDp;
				childShapView.layout(left, top, left + shapeWith, top + shapeHeight);
				break;
			case 0:
				left = screenWith / 2 - shapeWith / 2 + shapeWith + devideDp;
				top = mitem * (shapeHeight + shapeHeight * 1 / 2 + devideDp + devideDp) + shapeHeight * 3 / 4
						+ devideDp;
				childShapView.layout(left, top, left + shapeWith, top + shapeHeight);
				break;

			}
		}
	}

	public void initShapeView(List<SixShape> sixShaps, int devideDp, Activity activity, int h, final HiveItemOnClickListener hiveItemOnClickListener) {
		this.sixShaps = sixShaps;
		this.devideDp = devideDp;
		this.screenHeight = h;
		Bitmap bitmap = null;
		for (int i = 0; i < sixShaps.size(); i++) {
			bitmap = BitmapFactory.decodeResource(this.getResources(), sixShaps.get(i).getDrawableId());
			shapeHeight = bitmap.getHeight();
			shapeWith = bitmap.getWidth();
			ImageView imageShape = new ImageView(context);
			imageShape.setPadding(0, 0, 0, 0);
			imageShape.setImageBitmap(bitmap);
			imageShape.setTag(i);
			imageShape.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					hiveItemOnClickListener.hiveItemOnClick(Integer.valueOf(v.getTag().toString()));
				}
			});
			addView(imageShape);
		}
		bitmap = null;
		invalidate();
	}
	
	public interface HiveItemOnClickListener{
		public void hiveItemOnClick(int position);
	}
}
