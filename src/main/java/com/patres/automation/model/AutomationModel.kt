package com.patres.automation.model

abstract class AutomationModel() {

    abstract fun runAction()

    open fun checkValidations() {
    }

}