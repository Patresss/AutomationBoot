package com.patres.automation.validation;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class PointValidation extends AbstractValidation {

    private final static String invalidMessage = "Point format: (x;y) ";
    private final static String pattern = "\\((\\d+);(\\d+)\\)"; // (12;34)

    public PointValidation(Label validationLabel, TextInputControl validationField) {
        super(validationLabel, validationField);
        validationLabel.setText(invalidMessage);
    }

    public boolean isConditionFulfilled() {
        return getValidationTextField().getText().matches(pattern);
    }
}
