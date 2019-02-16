package com.patres.languagepopup.util

import com.sun.javafx.util.Utils
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.TextField
import kotlin.reflect.KClass


fun TextField.setIntegerFilter() {
    textProperty().addListener { _, _, newValue ->
        if (!newValue.matches(Regex("\\d*"))) {
            text = newValue.replace(Regex("[^\\d]"), "")
        }
    }
}

fun Parent.moveTo(node: Node, newPlace: Int) {
    // Workaround for private members
    val childrenFromField = getObjectFromField(Parent::class, this,"children" ) as ObservableList<Node>
    val childSetFromField = getObjectFromField(Parent::class, this,"childSet" ) as Set<Node>
    val childrenTriggerPermutationField = Parent::class.java.getDeclaredField("childrenTriggerPermutation")
    childrenTriggerPermutationField.isAccessible = true


    if (Utils.assertionEnabled()) {
        if (!childSetFromField.contains(node)) {
            throw java.lang.AssertionError(
                    "specified node is not in the list of children")
        }
    }

    if (childrenFromField[0] !== node) {
        childrenTriggerPermutationField.setBoolean(this, true)
        try {
            childrenFromField.remove(node)
            childrenFromField.add(newPlace, node)
        } finally {
            childrenTriggerPermutationField.setBoolean(this, false)
        }
    }
}

fun Parent.swap(node: Node, nodeToSwap: Node) {
    // Workaround for private members
    val childrenFromField = getObjectFromField(Parent::class, this,"children" ) as ObservableList<Node>
    val childSetFromField = getObjectFromField(Parent::class, this,"childSet" ) as Set<Node>
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
