package com.xq.jltx.horizontal3dlistview;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;


/**
 * @author jltx
 *         Created by junlintianxia on 2015年11月27日.
 */
public class H3DListView extends HListView {
    private float angle = 10;
    private float maxZIndex = 20;
    private Camera mCamera;
    private Matrix mMatrix;
    public int centerOffset = 0;
    public H3DListView(Context context) {
        super(context);
        initView();
    }

    public H3DListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public H3DListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView(){
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    @Override
    public void dispatchCustomDraw(Canvas canvas) {
        final long drawingTime = getDrawingTime();
        DrawFilter drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(drawFilter);
        final int childCount = getChildCount();
        final int headCount = getHeaderViewsCount();
        final int footCount = getFooterViewsCount();
        final int firstVIndex = getFirstVisiblePosition();
        final int AllCount = getCount();
//        invalidateParentViewCaches();
        for (int i = 0; i < childCount; i++) {
            if((firstVIndex + i) > (headCount - 1) && (firstVIndex + i) < AllCount - footCount){
                draw3dChildView(canvas, i, drawingTime);
            }else{
                View childView = getChildAt(i);
                drawChild(canvas, childView, drawingTime);
            }

//            draw3dChildView1(canvas, i, drawingTime);
        }

        ViewParent viewParent = getParent();
        if (viewParent != null) {
            View view = (View) viewParent;
            view.invalidate();
        }

    }

    public void invalidateParentViewCaches(){
        Method invalidateParentCachesMethod = null;
        for(Class<?> clazz = this.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()){
            try {
                invalidateParentCachesMethod = clazz.getDeclaredMethod("invalidateParentCaches",new Class[]{});
                break;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        if(invalidateParentCachesMethod != null){
            invalidateParentCachesMethod.setAccessible(true);
            try {
                invalidateParentCachesMethod.invoke(new Class[]{});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置中心偏移误差值
     * @param centerOffset
     */
    public void setCenterOffset(int centerOffset) {
        this.centerOffset = Math.abs(centerOffset);
    }

    @Override
    public void resetInCenter() {
        final int childCount = getChildCount();
        final int AllCount = getCount();
        final int headCount = getHeaderViewsCount();
        final int footCount = getFooterViewsCount();
        if(AllCount - headCount - footCount > 0){
            View childView = null;
            List<Integer> distanceList = new ArrayList<Integer>();
            List<Integer> distanceListAbs = new ArrayList<Integer>();
            for (int i = 0; i < childCount; i++) {
                childView = getChildAt(i);
                final int scollX = getScrollX();
                int centerX = scollX + getWidth() / 2;
                int centerY = getHeight() / 2;

                int left = childView.getLeft();
                int top = childView.getTop();
                int width = childView.getWidth();
                int height = childView.getHeight();

                int centerItemX = left + width / 2;
                int centerItemY = top + height / 2;
                int deltaX = centerItemX - centerX;
                distanceList.add(deltaX);
                distanceListAbs.add(Math.abs(deltaX));
            }
            List<Integer> subListAbs = new ArrayList<Integer>(distanceListAbs);
            Collections.sort(subListAbs, new Comparator<Integer>() {
                @Override
                public int compare(Integer lhs, Integer rhs) {
                    if (rhs > lhs) {
                        return -1;
                    } else if (rhs < lhs) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });

            int index = distanceListAbs.indexOf(subListAbs.get(0));
            int lastMinDelta = distanceList.get(index);
            if (Math.abs(lastMinDelta) > centerOffset) {
                smoothScrollBy(lastMinDelta, 250);
            }
        }

    }

    public void draw3dChildView(Canvas canvas, int childIndex, long drawingTime) {
        View childView = getChildAt(childIndex);
        final int scollX = getScrollX();
        int centerX = scollX + getWidth() / 2;
        int centerY = getHeight() / 2;
        int left = childView.getLeft();
        int top = childView.getTop();
        int width = childView.getWidth();
        int height = childView.getHeight();

        int centerItemX = left + width / 2;
        int centerItemY = top + height / 2;
        int deltaX = centerItemX - centerX;
        float ratio = (float) deltaX / (float) (width + getDividerWidth());
        final Camera camera = mCamera;
        final Matrix matrix = mMatrix;

        int saveCount = canvas.save();
        camera.save();
        camera.translate(0.0f, 0.0f, Math.abs(ratio) * maxZIndex);
        camera.rotateY(-ratio * angle);
        camera.rotateX(Math.abs(ratio) * (angle / 3.0f));
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerItemX, -centerItemY);
        matrix.postTranslate(centerItemX, centerItemY);
        canvas.concat(matrix);
        childView.setAlpha(1.0f - Math.abs(ratio / 2.0f));
        drawChild(canvas, childView, drawingTime);
        canvas.restoreToCount(saveCount);
    }


    public void draw3dChildView1(Canvas canvas, int childIndex, long drawingTime) {
        View childView = getChildAt(childIndex);
        final int scollX = getScrollX();
        int centerX = scollX + getWidth() / 2;
        int centerY = getHeight() / 2;
        int left = childView.getLeft();
        int top = childView.getTop();
        int width = childView.getWidth();
        int height = childView.getHeight();

        int centerItemX = left + width / 2;
        int centerItemY = top + height / 2;
        int deltaX = centerItemX - centerX;
        float ratio = (float) deltaX / (float) (width + getDividerWidth());
        final Camera camera = mCamera;
        final Matrix matrix = mMatrix;

        int saveCount = canvas.save();
        camera.save();
        camera.translate(-ratio * width / 2, 0.0f, Math.abs(ratio) * width / 2);
        camera.rotateY(ratio * 90);
//        camera.rotateX(Math.abs(ratio) * (angle / 3.0f));
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerItemX, -centerItemY);
        matrix.postTranslate(centerItemX, centerItemY);
        canvas.concat(matrix);
        childView.setAlpha(1.0f - Math.abs(ratio / 2.0f));
        drawChild(canvas, childView, drawingTime);
        canvas.restoreToCount(saveCount);
    }
}
