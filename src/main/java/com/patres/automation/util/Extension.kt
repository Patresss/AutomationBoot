package com.patres.automation.util

import com.patres.automation.Main
import com.patres.automation.excpetion.IntegerFormatException
import com.patres.automation.font.FontAutomationIcon
import com.patres.automation.font.FontAutomationIconView
import com.sun.javafx.util.Utils
import de.jensd.fx.glyphs.GlyphIcons
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.TextInputControl
import javafx.scene.control.Tooltip
import javafx.util.Duration
import kotlin.reflect.KClass


fun TextInputControl.setIntegerFilter() {
    textProperty().addListener { _, _, newValue ->
        if (!newValue.matches(Regex("\\d*"))) {
            text = newValue.replace(Regex("[^\\d]"), "")
        }
    }
}

fun Parent.swap(node: Node, nodeToSwap: Node) {
    // Workaround for private members
    val childrenFromField = getObjectFromField(Parent::class, this, "children") as ObservableList<Node>
    val childSetFromField = getObjectFromField(Parent::class, this, "childSet") as Set<Node>
    val childrenTriggerPermutationField = Parent::class.java.getDeclaredField("childrenTriggerPermutation")
    childrenTriggerPermutationField.isAccessible = true


    if (Utils.assertionEnabled()) {
        if (!childSetFromField.contains(node)) {
            throw java.lang.AssertionError(
                    "specified node is not in the list of children")
        }
    }

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

fun <TYPE> MutableList<TYPE>.swap(elementA: TYPE, elementB: TYPE) {
    val indexOfActionBlock = indexOf(elementA)
    val indexOfActionBlockToSwap = indexOf(elementB)
    this[indexOfActionBlock] = set(indexOfActionBlockToSwap, this[indexOfActionBlock])
}

fun getObjectFromField(clazz: KClass<out Any>, instance: Any, nameOfField: String): Any {
    val field = clazz.java.getDeclaredField(nameOfField)
    field.isAccessible = true
    return field.get(instance)
}

fun String.getInteger(): Int {
    try {
        return Integer.parseInt(this)
    } catch (e: Exception) {
        throw IntegerFormatException(this)
    }
}

fun fromBundle(key: String): String = Main.getLanguageString(key)

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


fun Tooltip.getHideTimer(): Timeline {
    val fieldBehavior = this.javaClass.getDeclaredField("BEHAVIOR")
    fieldBehavior.isAccessible = true
    val objBehavior = fieldBehavior.get(this)

    val fieldTimer = objBehavior.javaClass.getDeclaredField("hideTimer")
    fieldTimer.isAccessible = true
    return fieldTimer.get(objBehavior) as Timeline
}

fun GlyphIcons.getIcon(): Node? {
    val graphic: Node? = when (this) {
        is FontAwesomeIcon -> FontAwesomeIconView(this)
        is FontAutomationIcon -> FontAutomationIconView(this)
        else -> null
    }
    graphic?.styleClass?.add("sub-icon")
    return graphic
}