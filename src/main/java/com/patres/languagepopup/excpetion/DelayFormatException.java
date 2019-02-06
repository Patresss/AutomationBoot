package com.patres.languagepopup.excpetion;

public class DelayFormatException extends ApplicationException {

    public DelayFormatException(String delay) {
        super(String.format("Please enter the delay \"%s\" as an integer", delay));
    }

}
