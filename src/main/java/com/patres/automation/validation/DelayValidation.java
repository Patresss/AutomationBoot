package com.patres.automation.validation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DelayValidation extends AbstractValidation {

    private final static String invalidMessage = "Only integer";

    public DelayValidation(Label validationLabel, TextField validationField) {
        super(validationLabel, validationField);
        validationLabel.setText(invalidMessage);
    }

    public boolean isConditionFulfilled() {
        try {
            Integer.parseInt(getValidationTextField().getText());
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
