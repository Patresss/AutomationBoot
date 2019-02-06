//package com.patres.languagepopup.gui.controller
//
//import com.jfoenix.controls.JFXButton
//import com.jfoenix.controls.JFXTextField
//import com.patres.languagepopup.model.ActionBlock
//import com.patres.languagepopup.model.SchemaGroup
//import javafx.fxml.FXML
//import javafx.scene.Cursor
//import javafx.scene.Node
//import javafx.scene.layout.GridPane
//import javafx.scene.layout.Pane
//import javafx.scene.layout.StackPane
//import javafx.scene.layout.VBox
//import java.awt.Point
//
//
//class SchemaGroupController : ActionBlockController() {
//
//    @FXML
//    lateinit var outsideBox: StackPane
//
//    @FXML
//    lateinit var innerBox: VBox
//
//    @FXML
//    lateinit var buttonsGridPane: GridPane
//
//    @FXML
//    lateinit var groupNameTextField: JFXTextField
//
//    @FXML
//    lateinit var iterationsTextField: JFXTextField
//
//
//    @FXML
//    lateinit var moveButton: JFXButton
//
//
//    fun initialize() {
//        makeDraggable()
//    }
//
//    @FXML
//    fun remove() {
//        actionBlock?.let { currentActionBlock ->
//            val bottomNode = findNodeOnTheBottom()
//            if (bottomNode is SchemaGroup) {
//                when {
//                    currentActionBlock.willBeParent(bottomNode) -> bottomNode.addActionBlockToTop(currentActionBlock)
//                    currentActionBlock.willBeTriedOutOfGroup(bottomNode) -> bottomNode.moveToCommonGroup(currentActionBlock)
//                }
//            }
//        }
//    }
//
//    fun findNodeOnTheBottom(): ActionBlock? {
//        actionBlock?.let { currentActionBlock ->
//            val endPositionY = currentActionBlock.endPositionY
//            val startPositionY = currentActionBlock.startPositionY
//            return currentActionBlock.rootParent.allChildrenActionBlocks
//                    .filter { it -> it !== actionBlock }
//                    .filter { it -> endPositionY <= it.startPositionY || (currentActionBlock.parent == it && currentActionBlock.isLast()) }
//                    .minBy { it -> it.startPositionY }
//        }
//    }
//
//    fun makeDraggable() {
//        // var topNode = findNodeOnTheTop()
//        var bottomNodeStartPosition =0.0
//        var bottomNode: ActionBlock? = null
//
//        val mousePressedPoint = Point()
//        moveButton.setOnMousePressed { mouseEvent ->
//            mousePressedPoint.x = mouseEvent.screenX.toInt() - outsideBox.translateX.toInt()
//            mousePressedPoint.y = mouseEvent.screenY.toInt() - outsideBox.translateY.toInt()
//            moveButton.cursor = Cursor.MOVE
//
//            bottomNode = findNodeOnTheBottom()
//            val bottomNodeBoundsInScene = bottomNode?.getMainInsideNode()?.localToScene(bottomNode?.getMainInsideNode()?.boundsInLocal)
//            bottomNodeStartPosition = bottomNodeBoundsInScene?.minY?: 0.0
//
//        }
//
//        moveButton.setOnMouseReleased {
//            moveButton.cursor = Cursor.HAND
//            outsideBox.translateX = 0.0
//            outsideBox.translateY = 0.0
//        }
//
//        moveButton.setOnMouseDragged { mouseEvent ->
//            val newX = mouseEvent.screenX - mousePressedPoint.x
//            val newY = mouseEvent.screenY - mousePressedPoint.y
//            outsideBox.translateX = newX
//            outsideBox.translateY = newY
//
//            val boundsInScene = getMainInsideNode().localToScene(getMainInsideNode().boundsInLocal)
//            val currentEndPosition = boundsInScene.maxY
//
//            println("CURRENT: ${currentEndPosition}")
//            println("NEXT: ${bottomNodeStartPosition}")
//
//            if (currentEndPosition > bottomNodeStartPosition) {
//                outsideBox.translateX = 0.0
//                outsideBox.translateY = 0.0
//
//                actionBlock?.let { currentActionBlock ->
//                    val node = bottomNode
//                    if (node is SchemaGroup) {
//                        when {
//                            currentActionBlock.willBeParent(node) -> node.addActionBlockToTop(currentActionBlock)
//                            currentActionBlock.willBeTriedOutOfGroup(node) -> node.moveToCommonGroup(currentActionBlock)
//                        }
//                    }
//                }
//            }
////
////            node.parent.parent
////            val touchedGroup = foundHoveredGroup(actionBlocks, mouseEvent)
////            //  .firstOrNull()
////            println(touchedGroup?.groupNameTextField?.text ?: "Nie znaleziono")
////            println(touchedGroup?.numberOfParents ?: "Brak parentow")
//        }
//
//        moveButton.setOnMouseEntered { mouseEvent ->
//            if (!mouseEvent.isPrimaryButtonDown) {
//                moveButton.cursor = Cursor.HAND
//            }
//        }
//
//        moveButton.setOnMouseExited { mouseEvent ->
//            if (!mouseEvent.isPrimaryButtonDown) {
//                moveButton.cursor = Cursor.DEFAULT
//            }
//        }
//    }
//
//    override fun getMainOutsideNode(): Node = outsideBox
//
//    override fun getMainInsideNode(): Pane = innerBox
//
//}