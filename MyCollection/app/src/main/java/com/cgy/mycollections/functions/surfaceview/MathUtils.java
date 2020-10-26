package com.cgy.mycollections.functions.surfaceview;

import android.graphics.Point;

import androidx.annotation.NonNull;

/**
 * Description :
 * Author :cgy
 * Date :2020/10/26
 */
class MathUtils {
    //获取两点间直线距离
    public static int getLength(float x1, float y1, float x2, float y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * 获取线段上某个点的坐标，长度为a.x - cutRadius
     *
     * @param fromPoint 出发点A
     * @param b         终点B
     * @param cutRadius 截断距离
     * @return 截断点
     */
    public static Point getBorderPoint(Point fromPoint, Point b, int cutRadius) {
        float radian = getRadian(fromPoint, b);
        return new Point(fromPoint.x + (int) (cutRadius * Math.cos(radian)), fromPoint.y + (int) (cutRadius * Math.sin(radian)));
    }

    /**
     * 获取线段上某个点的坐标，长度为a.x - cutRadius
     *
     * @param fromPoint 起始点
     * @param angle     角度
     * @param radius    半径
     * @return
     */
    public static Point getBorderPoint(@NonNull Point fromPoint, float angle, int radius) {
        if (angle == GameSurface.NO_ANGLE || radius <= 0)
            return new Point(fromPoint.x, fromPoint.y);
        return new Point(fromPoint.x + (int) (radius * Math.cos(angle)), fromPoint.y + (int) (radius * Math.sin(angle)));
    }

    /**
     * 获取两点之间水平线夹角弧度，如果是同一个点返回NO_ANGLE
     *
     * @param a
     * @param b
     * @return
     */
    public static float getRadian(Point a, Point b) {
        float lenA = b.x - a.x;
        float lenB = b.y - a.y;
        if (lenA == 0 && lenB == 0)
            return GameSurface.NO_ANGLE;
        float lenC = (float) Math.sqrt(lenA * lenA + lenB * lenB);
        float ang = (float) Math.acos(lenA / lenC);
        ang = ang * (b.y < a.y ? -1 : 1);
        return ang;
    }

}
