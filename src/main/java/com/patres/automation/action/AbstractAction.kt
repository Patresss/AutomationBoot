package com.patres.automation.action

import com.patres.automation.type.ActionBootable
import java.awt.Robot

abstract class AbstractAction(
        var actionBoot: ActionBootable
) {

    companion object {
        val robot: Robot = Robot()
    }

    abstract fun runAction()

    open fun validate() {
    }

    abstract fun toStringLog(): String

}
