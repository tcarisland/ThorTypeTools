package com.tcarisland.thortype.fontinspector.gui.lookandfeel;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.tcarisland.thortype.fontinspector.FontInspectorProperties;
import com.tcarisland.thortype.fontinspector.gui.FontInspectorFrame;
import com.tcarisland.thortype.fontinspector.gui.FontInspectorGUI;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class LookAndFeelHelper {
    public static FontInspectorFrame initFrameWithMacLookAndFeel(FontInspectorProperties properties) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        FlatDarculaLaf.setup();
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "FontInspector");
        UIManager.setLookAndFeel( new FlatDarculaLaf());
        FontInspectorFrame frame = new FontInspectorFrame(properties);
        SwingUtilities.updateComponentTreeUI(frame);
        return frame;
    }

    public static FontInspectorFrame initFrameWithWindowsLookAndFeel(FontInspectorProperties properties) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        FontInspectorFrame frame = new FontInspectorFrame(properties);
        SwingUtilities.updateComponentTreeUI(frame);
        return frame;
    }

    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        boolean isWindows = osName.toLowerCase().contains("win");
        log.info("os.name {}", osName);
        log.info("is windows {}", isWindows);
        return isWindows;
    }

    public static boolean isMac() {
        String osName = System.getProperty(("os.name"));
        boolean isMac = osName.toLowerCase().contains("mac os x");
        log.info("os.name {}", osName);
        log.info("is mac {}", isMac);
        return isMac;
    }
}
