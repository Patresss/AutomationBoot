# AutomationBoot
Application for process automation. Controls mouse and keyboard, records actions, executes scripts, etc. AutomationBoot gives you the ability to control actions using the button, keyboard shortcuts and via the website.

![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/My%20work.png)
 
## Download
* Installer exe (windows) - [download exe](https://github.com/Patresss/AutomationBoot/releases/download/1.0.0/Automation.Boot-1.0.0.exe)
* Executable jar (bin/Automation Boot) - [download zip](https://github.com/Patresss/AutomationBoot/releases/download/1.0.0/Automation.Boot-1.0.0.zip)

*If the program does not respond, please run it as administrator*
## Capabilities

* Actions:
    * Delay
    * Move mouse
    * Left, middle, right mouse button
        * Click
        * Double click
        * Press
        * Release
    * Scroll
        * Up
        * Down
    * Press a keyboard key
    * Paste a text
    * Paste a test from the file
    * Type a text
    * Type a text from the file
    * Run an existing AutomationBoot schema
    * Open file
    * Open directory
    * Run a bat script
    * Run a bat script and wait
* Mouse control:
    * Points
    * Vector
    * Based on the selected picture (opencv)
* Action recording
* Groups
* Iterations
* Multiple schemas (tabs)
* Control via:
    * Button
    * Keyboard shortcuts
    * Website:
        * Remote control (e.g. by smartphone)
        * Possibility of password protection
        * Possibility of changing port
* Supported languages: English, Polish
* Delay control between actions



## Examples
* Actions

| Move mouse [abJson](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Move%20mouse.ab) | Keyboard and paste [abJson](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Keyboard%20and%20paste.ab) |
:-------------------------:|:-------------------------:
![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Move%20mouse.png) | ![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Keyboard%20and%20paste.png)

| Scripts [abJson](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Scripts.ab) | Shut down computer [abJson](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Shut%20down%20computer.ab) |
:-------------------------:|:-------------------------:
![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Scripts.png) | ![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Shut%20down%20computer.png)

* Settings

| Global | Local | Active shemas |
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Global%20settings.png) | ![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Local%20settings.png) | ![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Active%20schemas.png)

* Web

| Web view |
:-------------------------:
![](https://github.com/Patresss/AutomationBoot/blob/master/Examples/Web.png) 


## Running
To run use VM options

        --add-opens=java.base/java.lang=opencv
        --add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix
        --add-exports=javafx.controls/com.sun.javafx.scene.control=com.jfoenix
        --add-opens=javafx.controls/javafx.scene.control=automationBoot
        --add-opens=javafx.graphics/javafx.scene=automationBoot
        
## Known issues
* Program not responding
    
    *Please run it as administrator*

*  https://github.com/kotest/kotest/issues/1495
         
        Could not write standard input to Gradle Test Executor 26.
        java.io.IOException: The pipe is being closed
   *Do not use gradlew to build the project*
   
*  https://github.com/beryx/badass-jlink-plugin/issues/116
         
        Execution failed for task ':compileJava'.
        > The value for task ':compileJava' property 'destinationDirectory' is final and cannot be changed any further.
   *Use version of gradle: 6.0.1*       

## Built With

* [JFoenix](https://github.com/jfoenixadmin/JFoenix)
* [Kotest](https://github.com/kotest/kotest)
* [commons-lang](https://github.com/apache/commons-lang)
* [jnativehook](https://github.com/kwhat/jnativehook)
* [log4j](https://logging.apache.org/log4j/2.x/)
* [slf4j](http://www.slf4j.org/)
* [FontAwesomeFx](https://www.jensd.de/wordpress/?tag=fontawesomefx)
* [Commons IO](http://commons.apache.org/proper/commons-io/)
* [OpenCV](https://github.com/openpnp/opencv)
* [Jackson](https://github.com/FasterXML/jackson-module-kotlin)

## Donate me

* [Buy me a coffee](https://www.buymeacoffee.com/Patres)
* [Paypal](https://www.paypal.me/Patresssss)

## License

This project is licensed under the Apache License 2.0 
