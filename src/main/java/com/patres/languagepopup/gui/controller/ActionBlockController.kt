package com.patres.languagepopup.gui.controller

import com.patres.languagepopup.gui.ActionBlock
import com.patres.languagepopup.util.swap
import com.sun.javafx.geometry.BoundsUtils
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import java.awt.Point

abstract class ActionBlockController() {

    var actionBlock: ActionBlock? = null

    var boundsInScene: Bounds = BoundsUtils.createBoundingBox(Point2D(0.0, 0.0), Point2D(0.0, 0.0), Point2D(0.0, 0.0), Point2D(0.0, 0.0))
        get() = getMainInsideNode().localToScene(getMainInsideNode().boundsInLocal)


     fun foundHoveredGroup(actionBlocks: List<ActionBlockController>, mouseEvent: MouseEvent): SchemaGroupController? {
        return actionBlocks
                .filter { it -> it !== this }
                .filterIsInstance<SchemaGroupController>()
                .map { it -> (it to it.boundsInScene) }
                .filter { (_, bounds) -> bounds.minY < mouseEvent.sceneY && mouseEvent.sceneY < bounds.maxY }
                .map { (node, _) -> node }
                .maxBy { it -> it.numberOfParents }
    }

    var numberOfParents: Int = 0
        get() {
            var currentNumberOfParent = 0
            var currentParent = getMainInsideNode().parent
            while (currentParent != null) {
                currentNumberOfParent++
                currentParent = currentParent.parent
            }
            return currentNumberOfParent
        }

    abstract fun getMainOutsideNode(): Node

    abstract fun getMainInsideNode(): Pane

}
