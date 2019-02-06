package com.patres.languagepopup.model

import com.sun.javafx.geometry.BoundsUtils
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Node

abstract class AutomationModel(var rootParent: RootSchemaGroup, var parent: SchemaGroup?) {

    private var boundsInScene: Bounds = BoundsUtils.createBoundingBox(Point2D(0.0, 0.0), Point2D(0.0, 0.0), Point2D(0.0, 0.0), Point2D(0.0, 0.0))
        get() = getMainNode().localToScene(getMainNode().boundsInLocal)

    var startPositionY = 0.0
        get() = boundsInScene.minY

    var endPositionY = 0.0
        get() = boundsInScene.maxY

    abstract fun getMainNode(): Node

    abstract fun getMainInsideNode(): Node

    fun hasTheSameParent(actionBlock: AutomationModel) = parent == actionBlock.parent

    fun willBeParent(actionBlock: AutomationModel) = parent == actionBlock.parent

    fun willBeTriedOutOfGroup(actionBlock: AutomationModel) = endPositionY <= actionBlock.endPositionY

    fun isLast() = parent?.actionBlocks?.maxBy { it.endPositionY } == this

    fun isFirst() = parent?.actionBlocks?.minBy { it.startPositionY } == this


}