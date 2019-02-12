package com.patres.languagepopup.gui.controller.model

import com.patres.languagepopup.model.AutomationModel
import com.patres.languagepopup.model.SchemaGroupModel


abstract class AutomationController {

    abstract fun getModel(): AutomationModel<out AutomationController>

    fun downActionBlock() {
        getModel()?.let { currentActionBlock ->
            val bottomNode = findNodeOnTheBottom()
            if (bottomNode is SchemaGroupModel) {
                if (currentActionBlock.hasTheSameParent(bottomNode)) {
                    bottomNode.addActionBlockToTop(currentActionBlock)
                } else {
                    bottomNode.moveActionBlockUnder(currentActionBlock)
                }
            }
        }
    }

    fun upActionBlock() {
        getModel()?.let { currentActionBlock ->
            val topNode = findNodeOnTheTop()
            if (topNode is SchemaGroupModel) {
                if (currentActionBlock.hasTheSameParent(topNode)) {
                    topNode.addActionBlockToBottom(currentActionBlock)
                } else {
                    topNode.moveActionBlockAbove(currentActionBlock)
                }
            }
        }
    }

    fun findNodeOnTheBottom(): AutomationModel<out AutomationController>? {
        getModel().let { currentActionBlock ->
            val endPositionY = currentActionBlock.endPositionY
            return currentActionBlock.root.allChildrenActionBlocks
                    .filter { it !==  getModel() }
                    .filter { endPositionY <= it.startPositionY || (currentActionBlock.parent == it && currentActionBlock.isLast()) }
                    .minBy { it.startPositionY }
        }
    }

    fun findNodeOnTheTop(): AutomationModel<out AutomationController>? {
        getModel().let { currentActionBlock ->
            val startPositionY = currentActionBlock.startPositionY
            return currentActionBlock.root.allChildrenActionBlocks
                    .filter { it !==  getModel() }
                    .filter { startPositionY >= it.endPositionY || (currentActionBlock.parent == it && currentActionBlock.isFirst()) }
                    .maxBy { it.endPositionY }
        }
    }

}