package com.tcarisland.thortype.fontinspector.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.tcarisland.thortype.fontinspector.FontInspectorEventListener;
import com.tcarisland.thortype.fontinspector.FontInspectorProperties;
import com.tcarisland.thortype.fontinspector.services.TableTreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.ttf.TTFTable;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class FontInspectorGUI {

    private final TableTreeService tableTreeService;
    private final FontInspectorProperties properties;
    private JFrame fontInspectorFrame;
    private JPanel mainContentPane;
    private FontTableList tableListPane;

    public FontInspectorGUI(@Autowired FontInspectorProperties properties,
                            @Autowired FontInspectorMenuFactory menuFactory,
                            @Autowired FontInspectorEventListener eventListener,
                            @Autowired TableTreeService tableTreeService) {
        this.tableTreeService = tableTreeService;
        this.properties = properties;
        EventQueue.invokeLater(() -> {
            try {
                FontInspectorFrame frame;
                if(isWindows()) {
                    frame = this.initFrameWithWindowsLookAndFeel();
                } else if(isMac()) {
                    frame = this.initFrameWithMacLookAndFeel();
                } else {
                    frame = new FontInspectorFrame(properties);
                }
                frame.setJMenuBar(menuFactory.initMenu(frame.getRootPane()));
                eventListener.setFontInspectorGUI(this);
                this.mainContentPane = initContentPane();
                this.tableListPane = initTableListPane();
                this.mainContentPane.add(this.tableListPane.getTableListPanel(), BorderLayout.WEST);
                frame.setContentPane(this.mainContentPane);
                this.fontInspectorFrame = frame;
                frame.repaint();
                frame.setVisible(true);
            } catch (Exception e) {
                log.error("could not set look and feel", e);
            }
        });
    }

    private FontInspectorFrame initFrameWithMacLookAndFeel() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        FlatDarculaLaf.setup();
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "FontInspector");
        UIManager.setLookAndFeel( new FlatDarculaLaf());
        FontInspectorFrame frame = new FontInspectorFrame(this.properties);
        SwingUtilities.updateComponentTreeUI(frame);
        return frame;
    }

    public FontInspectorFrame initFrameWithWindowsLookAndFeel() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        FontInspectorFrame frame = new FontInspectorFrame(this.properties);
        SwingUtilities.updateComponentTreeUI(frame);
        return frame;
    }

    public boolean isWindows() {
        String osName = System.getProperty("os.name");
        boolean isWindows = osName.toLowerCase().indexOf("win") >= 0;
        log.info("os.name {}", osName);
        log.info("is windows {}", isWindows);
        return isWindows;
    }

    public boolean isMac() {
        String osName = System.getProperty(("os.name"));
        boolean isMac = osName.toLowerCase().contains("mac os x");
        log.info("os.name {}", osName);
        log.info("is mac {}", isMac);
        return isMac;
    }

    public JPanel initContentPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        return panel;
    }

    public FontTableList initTableListPane() {
        JTree emptyTree = new JTree(new DefaultMutableTreeNode("no font selected"));
        FontTableList fontTableList = new FontTableList();
        fontTableList.getTableTree().setModel(emptyTree.getModel());
        return fontTableList;
    }

    public void updateContentPane(TrueTypeFont font, Map<String, TTFTable> map) {
        try {
            log.info("update content pane called {}", String.join(" ", map.keySet()));
            this.tableListPane.getTableTree().removeAll();
            this.tableListPane.getTableTree().setModel(tableTreeService.createJTreeFromHashMap(font.getName(), map).getModel());
            this.fontInspectorFrame.revalidate();
            this.fontInspectorFrame.repaint();
        } catch (IOException e) {
            log.error("couldn't read font", e);
            throw new RuntimeException(e);
        }
    }

}
