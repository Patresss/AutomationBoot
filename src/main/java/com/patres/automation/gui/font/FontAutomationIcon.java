package com.patres.automation.gui.font;

import de.jensd.fx.glyphs.GlyphIcons;


/*
 * Java class, because Kotlin does not allow override name function
 */
public enum FontAutomationIcon implements GlyphIcons {

    LEFT_MOUSE_BUTTON("\ue900"),
    LEFT_MOUSE_BUTTON_ALT("\ue901"),
    LEFT_MOUSE_BUTTON_EDGE("\ue902"),
    LEFT_MOUSE_BUTTON_EDGE_ALT("\ue903"),
    MIDDLE_MOUSE_BUTTON("\ue904"),
    MIDDLE_MOUSE_BUTTON_ALT("\ue905"),
    MIDDLE_MOUSE_BUTTON_ALT_SMALL("\ue906"),
    MIDDLE_MOUSE_BUTTON_EDGE("\ue907"),
    MIDDLE_MOUSE_BUTTON_EDGE_SMALL("\ue908"),
    MIDDLE_MOUSE_BUTTON_SMALL("\ue909"),
    MOVE_MOUSE("\ue90a"),
    MOVE_MOUSE_ALT("\ue90b"),
    RIGHT_MOUSE_BUTTON("\ue90c"),
    RIGHT_MOUSE_BUTTON_ALT("\ue90d"),
    RIGHT_MOUSE_BUTTON_EDGE("\ue90e"),
    RIGHT_MOUSE_BUTTON_EDGE_ALT("\ue90f");

    private final String unicode;

    FontAutomationIcon(String unicode) {
        this.unicode = unicode;
    }

    @Override
    public String unicode() {
        return unicode;
    }

    @Override
    public String fontFamily() {
        return "FontAutomation";
    }
}
