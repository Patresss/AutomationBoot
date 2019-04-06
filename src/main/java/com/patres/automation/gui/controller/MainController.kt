package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent
import com.jfoenix.controls.JFXTabPane
import com.patres.automation.Main
import com.patres.automation.gui.dialog.ExceptionHandlerDialog
import com.patres.automation.model.RootSchemaGroupModel
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.util.RootSchemaLoader
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane
import javafx.util.Duration
import org.slf4j.LoggerFactory
import java.io.File
import java.text.MessageFormat


class MainController {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MainController::class.java)
        val FILE_IS_SAVED: String = Main.bundle.getString("message.snackbar.fileIsSaved")
        const val MESSAGE_SNACKBAR_TIMEOUT: Long = 5000
    }

    @FXML
    lateinit var root: StackPane

    @FXML
    lateinit var tabPane: JFXTabPane

    @FXML
    lateinit var centerStackPane: StackPane

    private lateinit var snackBar: JFXSnackbar

    val tabContainers = FXCollections.observableList(ArrayList<TabContainer>())

    private val globalSettingsController = GlobalSettingsController(this)

    fun initialize() {
        snackBar = JFXSnackbar(root)
        tabPane.tabClosingPolicy = TabPane.TabClosingPolicy.ALL_TABS

        val previousOpenModels = getPreviousOpenModels()
        if (previousOpenModels.isEmpty()) {
            createNewRootSchema()
        } else {
            previousOpenModels.forEach { loadModelFromFile(it) }
        }

        listenTabContainers()
    }

    private fun listenTabContainers() {
        tabContainers.addListener { _: ListChangeListener.Change<out TabContainer>? ->
            GlobalSettingsLoader.save(Main.globalSettings)
        }
    }

    private fun getPreviousOpenModels(): List<File> {
        return Main.globalSettings.previousPathFiles
                .map { File(it) }
                .filter { it.exists() }
    }

    @FXML
    fun createNewRootSchema() {
        val tabContainer = RootSchemaLoader.createNewRootSchema(tabPane)
        tabContainers.add(tabContainer)
    }

    @FXML
    fun openRootSchema() {
        try {
            val tabContainer = RootSchemaLoader.openRootSchema(tabPane)
            if (tabContainer != null) {
                tabContainers.add(tabContainer)
            }
        } catch (e: Exception) {
            LOGGER.error("Exception: {}", e)
            val dialog = ExceptionHandlerDialog(e)
            dialog.show()
        }
    }

    private fun loadModelFromFile(fileToLoad: File) {
        try {
            val tabContainer = RootSchemaLoader.openRootSchema(tabPane, fileToLoad)
            tabContainers.add(tabContainer)
        } catch (e: Exception) {
            LOGGER.error("Cannot load file ${fileToLoad.absolutePath} Exception: {}", e.message)
        }
    }

    @FXML
    fun saveExistingRootSchema() {
        getSelectedTabContainer()?.let {
            val saved = RootSchemaLoader.saveExistingRootSchema(it)
            if (saved) {
                setMessageToSnackBar(MessageFormat.format(FILE_IS_SAVED, it.rootSchema.file?.name))
            }
        }
    }

    @FXML
    fun saveAsRootSchema() {
        getSelectedTabContainer()?.let {
            val saved = RootSchemaLoader.saveAsRootSchema(it)
            if (saved) {
                setMessageToSnackBar(MessageFormat.format(FILE_IS_SAVED, it.rootSchema.file?.name))
            }
        }
    }

    @FXML
    fun openGlobalSettings() {
        if (!centerStackPane.children.contains(globalSettingsController)) {
            globalSettingsController.translateXProperty().set(Main.mainStage.scene.width)
            centerStackPane.children.add(globalSettingsController)

            val timeline = Timeline()
            val kv = KeyValue(globalSettingsController.translateXProperty(), 0, Interpolator.EASE_IN)
            val kf = KeyFrame(Duration.seconds(0.1), kv)
            timeline.keyFrames.add(kf)
            timeline.setOnFinished { centerStackPane.children.remove(tabPane) }
            timeline.play()
        }
    }

    private fun getSelectedTabContainer(): TabContainer? = tabContainers.find { it.tab == tabPane.selectionModel?.selectedItem }

    fun changeDetect(rootSchemaGroupModel: RootSchemaGroupModel) {
        val tabContainer = tabContainers.find { it.rootSchema == rootSchemaGroupModel }
        tabContainer?.tab?.graphic = FontAwesomeIconView(FontAwesomeIcon.SAVE)
    }

    private fun setMessageToSnackBar(message: String) {
        snackBar.fireEvent(
                SnackbarEvent(message, "X",
                        MESSAGE_SNACKBAR_TIMEOUT,
                        false,
                        EventHandler { snackBar.close() }))
    }

    fun removeTab(tabContainer: TabContainer) {
        tabContainers.remove(tabContainer)
        tabPane.tabs.remove(tabContainer.tab)
    }

}
