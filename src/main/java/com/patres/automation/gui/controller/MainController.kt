package com.patres.automation.gui.controller

import com.jfoenix.controls.JFXSnackbar
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent
import com.jfoenix.controls.JFXTabPane
import com.patres.automation.ApplicationLauncher
import com.patres.automation.Main
import com.patres.automation.action.RootSchemaGroupModel
import com.patres.automation.gui.animation.SliderAnimation
import com.patres.automation.gui.controller.settings.GlobalSettingsController
import com.patres.automation.gui.controller.settings.LocalSettingsController
import com.patres.automation.gui.dialog.ExceptionHandlerDialog
import com.patres.automation.gui.dialog.LogManager
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LanguageManager
import com.patres.automation.util.RootSchemaLoader
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane
import org.slf4j.LoggerFactory
import java.io.File


class MainController {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(MainController::class.java)
    }

    @FXML
    lateinit var root: StackPane

    @FXML
    lateinit var tabPane: JFXTabPane

    @FXML
    lateinit var centerStackPane: StackPane

    @FXML
    lateinit var fileMenu: Menu

    @FXML
    lateinit var newMenuItem: MenuItem

    @FXML
    lateinit var openMenuItem: MenuItem

    @FXML
    lateinit var saveMenuItem: MenuItem

    @FXML
    lateinit var saveAsMenuItem: MenuItem

    @FXML
    lateinit var closeTabMenuItem: MenuItem

    @FXML
    lateinit var settingsMenu: Menu

    @FXML
    lateinit var globalSettingsMenuItem: MenuItem

    @FXML
    lateinit var localSettingsMenuItem: MenuItem

    @FXML
    lateinit var helpMenu: Menu

    @FXML
    lateinit var aboutMenuItem: MenuItem

    private lateinit var snackBar: JFXSnackbar

    val tabContainers: ObservableList<TabContainer> = FXCollections.observableList(ArrayList<TabContainer>())

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
        initLanguage()
    }
    
    private fun initLanguage() {
        fileMenu.textProperty().bind(LanguageManager.createStringBinding("menu.file"))
        newMenuItem.textProperty().bind(LanguageManager.createStringBinding("menu.new"))
        openMenuItem.textProperty().bind(LanguageManager.createStringBinding("menu.open"))
        saveMenuItem.textProperty().bind(LanguageManager.createStringBinding("menu.save"))
        saveAsMenuItem.textProperty().bind(LanguageManager.createStringBinding("menu.saveAs"))
        closeTabMenuItem.textProperty().bind(LanguageManager.createStringBinding("menu.closeTab"))
        settingsMenu.textProperty().bind(LanguageManager.createStringBinding("menu.settings"))
        globalSettingsMenuItem.textProperty().bind(LanguageManager.createStringBinding("menu.settings.globalSettings"))
        localSettingsMenuItem.textProperty().bind(LanguageManager.createStringBinding("menu.settings.localSettings"))
        helpMenu.textProperty().bind(LanguageManager.createStringBinding("menu.help"))
        aboutMenuItem.textProperty().bind(LanguageManager.createStringBinding("menu.about"))
    }

    private fun listenTabContainers() {
        tabContainers.addListener { _: ListChangeListener.Change<out TabContainer>? ->
            GlobalSettingsLoader.save(ApplicationLauncher.globalSettings)
        }
    }

    private fun getPreviousOpenModels(): List<File> {
        return ApplicationLauncher.globalSettings.previousPathFiles
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
            LogManager.showAndLogException(e)
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
                createSaveFileSnackBar(it.rootSchema.file?.name)
            }
        }
    }

    @FXML
    fun saveAsRootSchema() {
        getSelectedTabContainer()?.let {
            val saved = RootSchemaLoader.saveAsRootSchema(it)
            if (saved) {
                createSaveFileSnackBar(it.rootSchema.file?.name)
            }
        }
    }

    @FXML
    fun openGlobalSettings() {
        if (!centerStackPane.children.contains(globalSettingsController)) {
            globalSettingsController.reloadSettingsValue()
            SliderAnimation.goToTheWindow(globalSettingsController, tabPane, centerStackPane)
        }
    }

    @FXML
    fun openLocalSettings() {
        if (centerStackPane.children.contains(globalSettingsController)) {
            globalSettingsController.closeSettings()
        }
        getSelectedTabContainer()?.rootSchema?.controller?.openLocalSettings()
    }

    @FXML
    fun closeCurrentTab() {
        getSelectedTabContainer()?.let {
            RootSchemaLoader.createOnCloseRequest(it).handle(null)
        }
    }

    private fun getSelectedTabContainer(): TabContainer? = tabContainers.find { it.tab == tabPane.selectionModel?.selectedItem }

    fun changeDetect(rootSchemaGroupModel: RootSchemaGroupModel) {
        val tabContainer = tabContainers.find { it.rootSchema == rootSchemaGroupModel }
        tabContainer?.tab?.graphic = FontAwesomeIconView(FontAwesomeIcon.SAVE)
    }

    private fun createSaveFileSnackBar(fileName: String?) {
        val formattedMessage = LanguageManager.getLanguageString("message.snackbar.fileIsSaved", fileName ?: "")
        snackBar.fireEvent(SnackbarEvent(Label(formattedMessage)))
    }

    fun removeTab(tabContainer: TabContainer) {
        tabContainers.remove(tabContainer)
        tabPane.tabs.remove(tabContainer.tab)
    }

    fun findActionByName(actionName: String): RootSchemaGroupModel? {
        return tabContainers
                .map { it.rootSchema }
                .find { it.localSettings.enableRest && it.getEndpointName() == actionName }
    }
}
