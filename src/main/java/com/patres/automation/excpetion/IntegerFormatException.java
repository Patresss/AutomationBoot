package com.patres.automation.excpetion;

public class IntegerFormatException extends ApplicationException {

    public IntegerFormatException(String delay) {
        super(String.format("Please enter the delay \"%s\" as an integer", delay));
    }

}
