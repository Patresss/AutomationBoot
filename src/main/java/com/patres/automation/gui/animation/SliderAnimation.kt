package com.patres.automation.gui.animation

import com.patres.automation.ApplicationLauncher

import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.scene.Node
import javafx.scene.layout.Pane
import javafx.util.Duration

object SliderAnimation {

    fun goToTheWindow(previousNode: Node, newNode: Node, container: Pane) {
        moveToTheWindow(previousNode, newNode, container, true)
    }

    fun backToTheWindow(previousNode: Node, newNode: Node, container: Pane) {
        moveToTheWindow(previousNode, newNode, container, false)
    }

    private fun moveToTheWindow(previousNode: Node, newNode: Node, container: Pane, forward: Boolean) {
        val direction = if(forward) 1 else -1
        previousNode.translateXProperty().set(direction *  ApplicationLauncher.mainStage.scene.width)
        container.children.add(previousNode)

        val timeline = Timeline()
        val kv = KeyValue(previousNode.translateXProperty(), 0, Interpolator.EASE_IN)
        val kf = KeyFrame(Duration.seconds(0.1), kv)
        timeline.keyFrames.add(kf)
        timeline.setOnFinished { container.children.remove(newNode) }
        timeline.play()
    }
}