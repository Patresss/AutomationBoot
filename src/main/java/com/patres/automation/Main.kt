package com.patres.automation

import com.jfoenix.controls.JFXDecorator
import com.patres.automation.gui.controller.MainController
import com.patres.automation.settings.GlobalSettingsLoader
import javafx.application.Application
import javafx.application.Platform
import javafx.beans.binding.Bindings
import javafx.beans.binding.StringBinding
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import java.awt.*
import java.io.File
import java.io.IOException
import java.text.MessageFormat
import java.util.*
import java.util.concurrent.Callable
import javax.imageio.ImageIO


class Main : Application() {

    companion object {
        val logger = LoggerFactory.getLogger(Main::class.java)!!
        const val sceneWidth = 515
        const val sceneHeight = 715
        const val sceneBarHeight = 35.0 + 4.0
        const val sceneBarWeight = 4.0 + 4.0
        var localeProperty = SimpleObjectProperty(Locale("pl"))
        var globalSettings = GlobalSettingsLoader.load()
        var mainStage: Stage = Stage()
        var mainPane: StackPane = StackPane()
        var tmpDirector: File = File("tmp")
        var mainController: MainController? = null

        @JvmStatic
        fun main(args: Array<String>) {
            nu.pattern.OpenCV.loadShared() //add this
//            ServerBoot.run()
            launch(Main::class.java)
        }

        fun getStylesheet(): String = Main::class.java.getResource("/css/style_day.css").toExternalForm()

        fun setStyle(scene: Scene) {
            scene.stylesheets.add(getStylesheet())
        }


        // TODO refactor to other class
        fun createStringBinding(key: String, vararg args: Any): StringBinding {
            logger.debug("Get string property for key: $key")
            return Bindings.createStringBinding(Callable { getLanguageString(key, args) }, localeProperty)
        }


        fun getLanguageString(key: String, vararg args: Any) = MessageFormat.format(getBundle().getString(key), *args)

        fun getBundle() = ResourceBundle.getBundle("language/Bundle", localeProperty.get())

    }

    override fun start(primaryStage: Stage) {
        try {
            mainStage = primaryStage
            mainStage.titleProperty().bind(createStringBinding("application.name"))
            mainStage.icons.add(Image("/image/icon.png"))
            mainStage.scene = createScene(loadMainPane())
            mainStage.minWidth = sceneWidth.toDouble()
            mainStage.minHeight = sceneHeight.toDouble()
            showStage()

            if (!SystemTray.isSupported()) {
                logger.error("SystemTray is not supported")
                Platform.exit()
            } else {
                createTryIcon()
            }
            //Platform.setImplicitExit(false)
        } catch (e: IOException) {
            logger.error("Error in start method - I/O Exception", e)
        }

    }

    private fun createTryIcon() {
        val popup = PopupMenu()
        val url = Main::class.java.getResource("/image/icon-small.png")
        val image = ImageIO.read(url)

        val trayIcon = TrayIcon(image)
        trayIcon.addActionListener { Platform.runLater { this.showStage() } }

        val tray = SystemTray.getSystemTray()

        val openItem = MenuItem(createStringBinding("button.open").get())
        val exitItem = MenuItem(createStringBinding("button.exit").get())

        openItem.addActionListener { Platform.runLater { this.showStage() } }
        exitItem.addActionListener {
            Platform.exit()
            tray.remove(trayIcon)
        }
        popup.add(openItem)
        popup.addSeparator()
        popup.add(exitItem)

        trayIcon.popupMenu = popup

        try {
            tray.add(trayIcon)
        } catch (e: AWTException) {
            logger.error("TrayIcon could not be added.")
        }
    }

    private fun showStage() {
        mainStage.show()
        mainStage.toFront()
    }

    private fun loadMainPane(): Pane {
        val loader = FXMLLoader()
        loader.location = Main::class.java.getResource("/fxml/Main.fxml")
        loader.resources = getBundle()

        mainPane = loader.load<StackPane>()
        mainController = loader.getController<MainController>()
        return mainPane
    }

    private fun createScene(mainPane: Pane): Scene {
        val decorator = JFXDecorator(mainStage, mainPane, false, true, true)
        val scene = Scene(decorator, sceneWidth.toDouble(), sceneHeight.toDouble())
        setStyle(scene)
        return scene
    }


}
