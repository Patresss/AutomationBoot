package com.patres.automation

import com.jfoenix.controls.JFXDecorator
import com.patres.automation.gui.component.snackBar.SnackBarType
import com.patres.automation.gui.component.snackBar.addMessage
import com.patres.automation.gui.component.snackBar.addMessageLanguage
import com.patres.automation.gui.controller.MainController
import com.patres.automation.server.ServerBoot
import com.patres.automation.settings.GlobalSettingsLoader
import com.patres.automation.settings.LanguageManager
import com.patres.automation.system.ApplicationInfo
import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import javafx.util.Duration
import nu.pattern.OpenCV
import org.slf4j.LoggerFactory
import java.awt.*
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.system.exitProcess


class ApplicationLauncher : Application() {

    companion object {
        val logger = LoggerFactory.getLogger(ApplicationLauncher::class.java)!!
        const val sceneWidth = 515
        const val sceneHeight = 750
        const val sceneBarHeight = 35.0 + 4.0
        const val sceneBarWeight = 4.0 + 4.0
        var globalSettings = GlobalSettingsLoader.load().also { LanguageManager.setLanguage(it.language) }
        lateinit var mainStage: Stage
        var mainController: MainController? = null
        var mainPane: StackPane = StackPane()
        var tmpDirector: File = File(System.getProperty("user.dir"), "tmp")

        @JvmStatic
        fun main(args: Array<String>) {
            logger.info("Application is starting...")
            checkVersion()
            OpenCV.loadLocally()
            if (globalSettings.enableRest) {
                ServerBoot.run(globalSettings.port)
            }
            launch(ApplicationLauncher::class.java)
        }

        private fun checkVersion() {
            if (globalSettings.applicationVersion != ApplicationInfo.version) {
                logger.warn("Versions differ from each other - application version ${ApplicationInfo.version} | setting version: ${globalSettings.applicationVersion}")
            }
        }

        fun getStylesheet(): String = ApplicationLauncher::class.java.getResource("/css/style_day.css").toExternalForm()

        fun setStyle(scene: Scene) {
            scene.stylesheets.add(getStylesheet())
        }

    }

    override fun start(primaryStage: Stage) {
        try {
            mainStage = primaryStage
            mainStage.titleProperty().bind(LanguageManager.createStringBinding("application.name"))
            mainStage.icons.add(Image(ApplicationLauncher::class.java.getResourceAsStream("/icon/desktop/main-icon.png")))
            mainStage.scene = createScene(loadMainPane())
            mainStage.minWidth = sceneWidth.toDouble()
            mainStage.minHeight = sceneHeight.toDouble()
            showStage()

            if (!SystemTray.isSupported()) {
                logger.error("SystemTray is not supported")
            } else {
                createTrayIcon()
            }

            showServerErrorMessageIfExist()
        } catch (e: IOException) {
            logger.error("Error in start method - I/O Exception", e)
        }

    }

    private fun showServerErrorMessageIfExist() {
        ServerBoot.errorMessage?.let {message ->
            mainController?.snackBar?.addMessage(SnackBarType.ERROR, message, Duration.seconds(5.0))
        }
    }

    private fun createTrayIcon() {
        Platform.setImplicitExit(false)
        val popup = PopupMenu()
        val url = ApplicationLauncher::class.java.getResource("/icon/desktop/small-icon.png")
        val image = ImageIO.read(url)

        val trayIcon = TrayIcon(image)
        trayIcon.addActionListener { Platform.runLater { this.showStage() } }

        val tray = SystemTray.getSystemTray()

        val openItem = MenuItem(LanguageManager.createStringBinding("button.open").get())
        val exitItem = MenuItem(LanguageManager.createStringBinding("button.exit").get())

        openItem.addActionListener { Platform.runLater { this.showStage() } }
        exitItem.addActionListener {
            Platform.exit()
            tray.remove(trayIcon)
            exitProcess(0)
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
        loader.location = ApplicationLauncher::class.java.getResource("/fxml/Main.fxml")
        loader.resources = LanguageManager.getBundle()

        mainPane = loader.load()
        mainController = loader.getController()

        return mainPane
    }

    private fun createScene(mainPane: Pane): Scene {
        val decorator = JFXDecorator(mainStage, mainPane, false, true, true)
        val scene = Scene(decorator, sceneWidth.toDouble(), sceneHeight.toDouble())
        setStyle(scene)
        return scene
    }

}
