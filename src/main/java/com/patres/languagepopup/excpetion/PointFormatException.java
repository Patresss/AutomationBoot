package com.patres.languagepopup.excpetion;

public class PointFormatException extends ApplicationException {

    public PointFormatException(String point) {
        super(String.format("Please enter the point \"%s\" in (x;y) format", point));
    }

}
