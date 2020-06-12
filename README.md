# AutomationBoot
Application for process automation. Controls mouse and keyboard, records actions, executes scripts, etc. AutomationBoot gives you the ability to control actions using the button, keyboard shortcuts and via the website.

 
## Download
* Installer exe (windows) - [download exe](https://github.com/Patresss/AutmationBoot/raw/master/release/Automation%20Boot-1.0.0.exe)
* Executable jar (bin/Automation Boot) - [download zip](https://github.com/Patresss/AutmationBoot/raw/master/release/Automation%20Boot-1.0.0.zip)

## Possibilities

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

| Move mouse [abJson](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Move%20mouse.ab) | Keyboard and paste [abJson](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Kayboard%20and%20paste.ab) | Shut down computer [abJson](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Shut%20down%20computer.ab) |
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Move%20mouse.png) | ![](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Kayboard%20and%20paste.png) | ![](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Shut%20down%20computer.png)

* Settings

| Global | Local | Active shemas |
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Global%20settings.png) | ![](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Local%20settings.png) | ![](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Active%20schemas.png)

* Web

| Web view |
:-------------------------:
![](https://github.com/Patresss/AutmationBoot/blob/master/Examples/Web.png) 


## Running
To run use VM options

        --add-opens=java.base/java.lang=opencv
        --add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix
        --add-exports=javafx.controls/com.sun.javafx.scene.control=com.jfoenix
        --add-opens=javafx.controls/javafx.scene.control=automationBoot
        --add-opens=javafx.graphics/javafx.scene=automationBoot
        
## Known issues
*  https://github.com/kotest/kotest/issues/1495
         
        Could not write standard input to Gradle Test Executor 26.
        java.io.IOException: The pipe is being closed
   *Do not use gradlew to build the pojrect*
   
*  https://github.com/beryx/badass-jlink-plugin/issues/116
         
        Execution failed for task ':compileJava'.
        > The value for task ':compileJava' property 'destinationDirectory' is final and cannot be changed any further.
   *Use version of gradle: 6.0.1*       

