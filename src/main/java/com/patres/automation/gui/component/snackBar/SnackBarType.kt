package com.patres.automation.gui.component.snackBar

import javafx.css.PseudoClass

enum class SnackBarType(cssClass: String? = null) {

    INFO,
    WARNING("warning-toast");

    val pseudoClass = if (cssClass != null) {
        PseudoClass.getPseudoClass(cssClass)
    } else null

}