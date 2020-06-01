package com.patres.automation.util.extension

import com.patres.automation.gui.font.FontAutomationIcon
import com.patres.automation.gui.font.FontAutomationIconView
import de.jensd.fx.glyphs.GlyphIcon
import de.jensd.fx.glyphs.GlyphIcons
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Tooltip
import javafx.util.Duration
import java.util.*
import kotlin.reflect.KClass


@Suppress("UNCHECKED_CAST")
fun Parent.swap(node: Node, nodeToSwap: Node) {
    // Workaround for private members
    val childrenFromField = getObjectFromField(Parent::class, this, "children") as ObservableList<Node>
    val childrenTriggerPermutationField = Parent::class.java.getDeclaredField("childrenTriggerPermutation")
    childrenTriggerPermutationField.isAccessible = true

    if (nodeToSwap !== node) {
        childrenTriggerPermutationField.setBoolean(this, true)
        try {
            val positionOfNode = childrenFromField.indexOf(node)
            val positionOfNodeToSwap = childrenFromField.indexOf(nodeToSwap)

            childrenFromField.remove(node)
            childrenFromField.remove(nodeToSwap)
            if (positionOfNode > positionOfNodeToSwap) {
                childrenFromField.add(positionOfNodeToSwap, node)
                childrenFromField.add(positionOfNode, nodeToSwap)
            } else {
                childrenFromField.add(positionOfNode, nodeToSwap)
                childrenFromField.add(positionOfNodeToSwap, node)
            }
        } finally {
            childrenTriggerPermutationField.setBoolean(this, false)
        }
    }
}

fun Tooltip.startTiming(time: Double) {
    try {
        val fieldBehavior = this.javaClass.getDeclaredField("BEHAVIOR")
        fieldBehavior.isAccessible = true
        val objBehavior = fieldBehavior.get(this)

        val fieldTimer = objBehavior.javaClass.getDeclaredField("activationTimer")
        fieldTimer.isAccessible = true
        val objTimer = fieldTimer.get(objBehavior) as Timeline

        objTimer.keyFrames.clear()
        objTimer.keyFrames.add(KeyFrame(Duration(time)))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun GlyphIcons.getIcon(): GlyphIcon<*>? {
    val graphic: GlyphIcon<*>? = when (this) {
        is FontAwesomeIcon -> FontAwesomeIconView(this)
        is FontAutomationIcon -> FontAutomationIconView(this)
        else -> null
    }
    graphic?.getStyleClass()?.add("sub-icon")
    return graphic
}

fun GlyphIcons.getIcon(size: String): GlyphIcon<*>? {
    val graphic: GlyphIcon<*>? = when (this) {
        is FontAwesomeIcon -> FontAwesomeIconView(this, size)
        is FontAutomationIcon -> FontAutomationIconView(this, size)
        else -> null
    }
    graphic?.getStyleClass()?.add("sub-icon")
    return graphic
}

@Suppress("UNCHECKED_CAST")
fun <T : Node> Node.calculateTypedParent(type: KClass<T>): T? {
    val potentialParent = parent
    return when {
        type.isInstance(potentialParent) -> potentialParent as T
        potentialParent is Node -> potentialParent.calculateTypedParent(type)
        else -> null
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Node> Node.hasParent(type: KClass<T>): Boolean {
    val potentialParent = parent
    return when {
        type.isInstance(potentialParent) -> true
        potentialParent is Node -> potentialParent.hasParent(type)
        else -> false
    }
}

fun Parent.getAllNodes(): ArrayList<Node> {
    val nodes = ArrayList<Node>()
    fun recurseNodes(node: Node) {
        nodes.add(node)
        if (node is Parent)
            for (child in node.childrenUnmodifiable) {
                recurseNodes(child)
            }
    }
    recurseNodes(this)
    return nodes
}

