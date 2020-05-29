# AutomationBoot
Application for process automation. Controls mouse and keyboard, records actions, executes scripts, etc. AutomationBoot gives you the ability to control actions using the button, keyboard shortcuts and via the website.

## Download
The newest version - [download windows installer](https://github.com/Patresss/AutomationBoot/raw/master/release/1.0.0/Automation Boot-1.0.0.exe)   

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
 

# Build
To build AutomationBoot, execute the following command:

    gradlew build

To run the simulation, execute the following command:

    gradlew run
    
To run tests, execute the following command:

    gradlew test