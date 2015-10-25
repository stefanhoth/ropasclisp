package com.stefanhoth.ropasclisp;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

public class CircularFrameDrawable extends Drawable {

    private final Drawable content;
    private final RectF bounds;
    private final Paint backgroundPaint;
    private final Paint contentPaint;
    private final Paint outlinePaint;

    public CircularFrameDrawable(Drawable content, @ColorInt int backgroundColor, @ColorInt int outlineColor) {
        this.content = content;
        this.bounds = new RectF();
        this.backgroundPaint = getBackgroundPaint(backgroundColor);
        this.contentPaint = new Paint();
        this.outlinePaint = getOutlinePaint(outlineColor);
    }

    private Paint getBackgroundPaint(@ColorInt int backgroundColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    private Paint getOutlinePaint(@ColorInt int outlineColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(outlineColor);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        this.bounds.set(bounds);
        content.setBounds(bounds);
        outlinePaint.setStrokeWidth(bounds.width() * .05f);
        contentPaint.setShader(null);
    }

    @Override
    public int getIntrinsicWidth() {
        return content.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return content.getIntrinsicHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        if (contentPaint.getShader() == null) {
            updateContentPaint(content);
        }
        canvas.drawOval(bounds, backgroundPaint);
        canvas.drawOval(bounds, contentPaint);
        canvas.drawOval(bounds, outlinePaint);
    }

    private void updateContentPaint(Drawable content) {
        Rect bounds = getBounds();
        Bitmap bitmap = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ARGB_8888);
        content.draw(new Canvas(bitmap));
        contentPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    }

    @Override
    public void setAlpha(int alpha) {
        backgroundPaint.setAlpha(alpha);
        contentPaint.setAlpha(alpha);
        outlinePaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        backgroundPaint.setColorFilter(colorFilter);
        contentPaint.setColorFilter(colorFilter);
        outlinePaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}
