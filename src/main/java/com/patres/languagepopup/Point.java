package com.patres.languagepopup;


import com.patres.languagepopup.excpetion.PointFormatException;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point stringToPoint(String pointString) throws PointFormatException {
        try {
            String pointArray[] = pointString.substring(1, pointString.length() - 1).split(";");
            int x = new Integer(pointArray[0]);
            int y = new Integer(pointArray[1]);
            return new Point(x, y);
        } catch (Exception e) {
            throw new PointFormatException(pointString);
        }
    }

    @Override
    public String toString() {
        return String.format("(%d;%d)", x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
