package com.cowry.assetmanage.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
 * @Description: scrollview中内嵌listview的简单实现
 * 当设置了android:dividerHeight="1dp"时，计算的高度没有包含这1dp，所以会挤压最后一个item
 * 解决方法:在item的布局中做出间隔效果
 * 
 * @File: ScrollViewWithListView.java
 * 
 * @Paceage com.meiya.ui
 * 
 * 
 * @Date 下午03:02:38
 * 
 * @Version
 */
public class ScrollListView extends ListView {

	public ScrollListView(Context context) {
		super(context);
	}

	public ScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollListView(Context context, AttributeSet attrs,
						  int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}