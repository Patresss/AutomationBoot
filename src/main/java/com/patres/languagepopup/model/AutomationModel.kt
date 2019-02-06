package com.patres.languagepopup.model

import com.patres.languagepopup.gui.controller.model.AutomationController
import com.patres.languagepopup.gui.controller.model.SchemaGroupController
import com.sun.javafx.geometry.BoundsUtils
import javafx.geometry.Bounds
import javafx.geometry.Point2D
import javafx.scene.Node

abstract class AutomationModel< ControllerType : AutomationController>(
        val controller: ControllerType,
        val root: RootSchemaGroupModel,
        var parent: SchemaGroupModel?
) {

//    var rootParentModel: SchemaGroupModel? = if (parent == null) parent else parent?.rootParentModel
//
//    var rootParentController: SchemaGroupController? = rootParentModel?.controller

    private var boundsInScene: Bounds = BoundsUtils.createBoundingBox(Point2D(0.0, 0.0), Point2D(0.0, 0.0), Point2D(0.0, 0.0), Point2D(0.0, 0.0))
        get() = getMainNode().localToScene(getMainNode().boundsInLocal)

    var startPositionY = 0.0
        get() = boundsInScene.minY

    var endPositionY = 0.0
        get() = boundsInScene.maxY

    abstract fun getMainNode(): Node

    abstract fun getMainInsideNode(): Node

    fun hasTheSameParent(actionBlock: AutomationModel<AutomationController>) = parent == actionBlock.parent

    fun willBeParent(actionBlock: AutomationModel<AutomationController>) = parent == actionBlock.parent

    fun willBeTriedOutOfGroup(actionBlock: AutomationModel<AutomationController>) = endPositionY <= actionBlock.endPositionY

    fun isLast() = parent?.actionBlocks?.maxBy { it.endPositionY } == this

    fun isFirst() = parent?.actionBlocks?.minBy { it.startPositionY } == this


}