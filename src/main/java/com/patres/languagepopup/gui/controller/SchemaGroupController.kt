package com.patres.languagepopup.gui.controller

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.patres.languagepopup.gui.ActionBlock
import com.patres.languagepopup.gui.SchemaGroup
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox


class SchemaGroupController : ActionBlockController() {

    @FXML
    lateinit var outsideBox: StackPane

    @FXML
    lateinit var innerBox: VBox

    @FXML
    lateinit var buttonsGridPane: GridPane

    @FXML
    lateinit var groupNameTextField: JFXTextField

    @FXML
    lateinit var iterationsTextField: JFXTextField


    @FXML
    lateinit var moveButton: JFXButton


    fun initialize() {
    }

    @FXML
    fun downActionBlock() {
        actionBlock?.let { currentActionBlock ->
            val bottomNode = findNodeOnTheBottom()
            if (bottomNode is SchemaGroup) {
                if (currentActionBlock.hasTheSameParent(bottomNode)) {
                    bottomNode.addActionBlockToTop(currentActionBlock)
                } else {
                    bottomNode.moveActionBlockUnder(currentActionBlock)
                }
            }
        }
    }

    @FXML
    fun upActionBlock() {
        actionBlock?.let { currentActionBlock ->
            val topNode = findNodeOnTheTop()
            if (topNode is SchemaGroup) {
                if (currentActionBlock.hasTheSameParent(topNode)) {
                    topNode.addActionBlockToBottom(currentActionBlock)
                } else {
                    topNode.moveActionBlockAbove(currentActionBlock)
                }
            }
        }
    }

    fun findNodeOnTheBottom(): ActionBlock? {
        actionBlock?.let { currentActionBlock ->
            val endPositionY = currentActionBlock.endPositionY
            return currentActionBlock.rootParent.allChildrenActionBlocks
                    .filter { it !== actionBlock }
                    .filter { endPositionY <= it.startPositionY || (currentActionBlock.parent == it && currentActionBlock.isLast()) }
                    .minBy { it.startPositionY }
        }
    }

    fun findNodeOnTheTop(): ActionBlock? {
        actionBlock?.let { currentActionBlock ->
            val startPositionY = currentActionBlock.startPositionY
            return currentActionBlock.rootParent.allChildrenActionBlocks
                    .filter { it !== actionBlock }
                    .filter { startPositionY >= it.endPositionY || (currentActionBlock.parent == it && currentActionBlock.isFirst()) }
                    .maxBy { it.endPositionY }
        }
    }

    override fun getMainOutsideNode(): Node = outsideBox

    override fun getMainInsideNode(): Pane = innerBox

}