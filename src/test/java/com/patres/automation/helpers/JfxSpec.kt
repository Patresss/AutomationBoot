package com.patres.automation.helpers

import io.kotest.core.spec.style.FreeSpec
import javafx.embed.swing.JFXPanel
open class JfxSpec(body: FreeSpec.() -> Unit = {}) : FreeSpec(body) {

    companion object {
        val fxPanel = JFXPanel() // init JavaFx Thread
    }

}