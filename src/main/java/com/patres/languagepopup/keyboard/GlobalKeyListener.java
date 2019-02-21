package com.patres.languagepopup.keyboard;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalKeyListener implements NativeKeyListener {

    private static boolean stop = false;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GlobalKeyListener.class);

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            stop = true;
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public static void activeListener() {
        try {
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.WARNING);
            logger.setUseParentHandlers(false);
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            LOGGER.error("There was a problem registering the native hook.", ex);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }

    public static boolean isStop() {
        return stop;
    }

    public static void setStop(boolean stop) {
        GlobalKeyListener.stop = stop;
    }
}