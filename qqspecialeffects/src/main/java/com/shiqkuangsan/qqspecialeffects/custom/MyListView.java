package com.shiqkuangsan.qqspecialeffects.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by shiqkuangsan on 2016/6/16.
 */

/**
 * 实现头条目下拉放大效果,参考QQ空间,不过QQ空间是橡皮筋拉伸
 */
public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ImageView iv_header;
    private int maxHeight;  // 可以拖动的最大高度
    private int srcHeight;  // 图片的原始高度
    private int ivHeight;   // 盛放图片的ImageView的高度

    // 不要忘记该控件需要设置头布局的图片
    public void setmImageView(final ImageView iv_header) {
        this.iv_header = iv_header;

        // 获取ImageView高度,该方法可能获取不到高度.解决办法是添加观察者,或者在布局中使用dimens
//        int ivHeight = iv_header.getHeight();
        iv_header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                iv_header.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                // 获取ImageView高度
                ivHeight = iv_header.getHeight();
                // 获取图片原始高度
                srcHeight = iv_header.getDrawable().getIntrinsicHeight();
                // 如果ImageView高度(布局中写的200dp)比原图大,那就设置最大值为2倍(400dp),不然就以图片最大高度
                maxHeight = ivHeight > srcHeight ? ivHeight * 2 : srcHeight;
            }
        });

    }

    /**
     * 该方法在ListView滑动到上头或者下头的时候调用该方法
     *
     * @param deltaX         继续滑动的时候x方向的变化量
     * @param deltaY         继续滑动的时候y方向的变化量,正值表示滑动到底部,负值是滑动到头部
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX x方向可以滚动的最大距离
     * @param maxOverScrollY y方向可以滚动的最大距离
     * @param isTouchEvent   true-当前是手指滑动,false-当前是惯性滑动
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        // 如果是手指滑动到顶部了,继续滑动的时候,给头iv_header设置高度,由于iv_header是centerCrop的,增加高度就会自动裁剪
        if (iv_header != null && deltaY < 0 && isTouchEvent) {
            int newHeight = iv_header.getHeight() + (-deltaY / 3);    // △Y/3 这样更好看
            // 定义个最大高度,不能让你一直增大
            if (newHeight > maxHeight)
                newHeight = maxHeight;
            iv_header.getLayoutParams().height = newHeight;
            iv_header.requestLayout();
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            // 恢复布局中写的ImageView高度,执行一个属性动画,从当前高度到布局中原来ImageView的高度
            final ValueAnimator animator = ValueAnimator.ofInt(iv_header.getHeight(), ivHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (Integer) animator.getAnimatedValue();
                    // 逐渐恢复到原始高度
                    iv_header.getLayoutParams().height = value;
                    iv_header.requestLayout();
                }
            });
            // 加一个回弹插补器
            animator.setInterpolator(new OvershootInterpolator(1f));
            animator.setDuration(500);
            animator.start();

        }

        return super.onTouchEvent(ev);
    }
}
