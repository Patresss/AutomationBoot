package com.patres.languagepopup.gui.controller.model

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
import com.patres.languagepopup.model.AutomationModel
import com.patres.languagepopup.model.SchemaGroupModel
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.*


class SchemaGroupController : AutomationController() {

    @FXML
    lateinit var mainSchemaBox: BorderPane

    @FXML
    lateinit var outsideBox: StackPane

    @FXML
    lateinit var innerBox: VBox

    @FXML
    lateinit var groupNameTextField: JFXTextField

    @FXML
    lateinit var iterationsTextField: JFXTextField

    @FXML
    lateinit var selectActionButton: JFXButton

    lateinit var model: SchemaGroupModel

    fun initialize() {
    }

    @FXML
    fun selectAction() {
        model.root.unselectAllButton()
        selectActionButton.styleClass.add("select-action-button-selected")
    }

    fun unselectAction() {
        selectActionButton.styleClass.remove("select-action-button-selected")
    }

//    @FXML
//    fun downActionBlock() {
//        model?.let { currentActionBlock ->
//            val bottomNode = findNodeOnTheBottom()
//            if (bottomNode is SchemaGroupModel) {
//                if (currentActionBlock.hasTheSameParent(bottomNode)) {
//                    bottomNode.addActionBlockToTop(currentActionBlock)
//                } else {
//                    bottomNode.moveActionBlockUnder(currentActionBlock)
//                }
//            }
//        }
//    }
//
//    @FXML
//    fun upActionBlock() {
//        model?.let { currentActionBlock ->
//            val topNode = findNodeOnTheTop()
//            if (topNode is SchemaGroupModel) {
//                if (currentActionBlock.hasTheSameParent(topNode)) {
//                    topNode.addActionBlockToBottom(currentActionBlock)
//                } else {
//                    topNode.moveActionBlockAbove(currentActionBlock)
//                }
//            }
//        }
//    }

    fun findNodeOnTheBottom(): AutomationModel<out AutomationController>? {
        model.let { currentActionBlock ->
            val endPositionY = currentActionBlock.endPositionY
            return currentActionBlock.root.allChildrenActionBlocks
                    .filter { it !== model }
                    .filter { endPositionY <= it.startPositionY || (currentActionBlock.parent == it && currentActionBlock.isLast()) }
                    .minBy { it.startPositionY }
        }
    }

    fun findNodeOnTheTop(): AutomationModel<out AutomationController>? {
        model.let { currentActionBlock ->
            val startPositionY = currentActionBlock.startPositionY
            return currentActionBlock.root.allChildrenActionBlocks
                    .filter { it !== model }
                    .filter { startPositionY >= it.endPositionY || (currentActionBlock.parent == it && currentActionBlock.isFirst()) }
                    .maxBy { it.endPositionY }
        }
    }

    fun getMainOutsideNode(): Node = mainSchemaBox

    fun getMainInsideNode(): Pane = innerBox

}