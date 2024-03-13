package com.tcarisland.thortype.fontinspector.gui;

import com.tcarisland.thortype.fontinspector.FontInspectorEventListener;
import com.tcarisland.thortype.fontinspector.FontInspectorProperties;
import com.tcarisland.thortype.fontinspector.gui.components.FontInspectorPanel;
import com.tcarisland.thortype.fontinspector.gui.components.FontTableList;
import com.tcarisland.thortype.fontinspector.gui.lookandfeel.LookAndFeelHelper;
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
    private FontInspectorPanel mainContentPane;

    public FontInspectorGUI(@Autowired FontInspectorProperties properties,
                            @Autowired FontInspectorMenuFactory menuFactory,
                            @Autowired FontInspectorEventListener eventListener,
                            @Autowired TableTreeService tableTreeService) {
        this.tableTreeService = tableTreeService;
        this.properties = properties;
        EventQueue.invokeLater(() -> {
            try {
                FontInspectorFrame frame;
                if(LookAndFeelHelper.isWindows()) {
                    frame = LookAndFeelHelper.initFrameWithWindowsLookAndFeel(properties);
                } else if(LookAndFeelHelper.isMac()) {
                    frame = LookAndFeelHelper.initFrameWithMacLookAndFeel(properties);
                } else {
                    frame = new FontInspectorFrame(properties);
                }
                frame.setJMenuBar(menuFactory.initMenu(frame.getRootPane()));
                eventListener.setFontInspectorGUI(this);
                this.mainContentPane = new FontInspectorPanel();
                this.mainContentPane.getTableListPanel().getTableTree().setModel(tableTreeService.createDefaultTree().getModel());
                frame.setContentPane(this.mainContentPane.getFontInspectorPanel());
                this.fontInspectorFrame = frame;
                frame.repaint();
                frame.setVisible(true);
            } catch (Exception e) {
                log.error("could not set look and feel", e);
            }
        });
    }

    public void updateContentPane(TrueTypeFont font, Map<String, TTFTable> map) {
        try {
            log.info("update content pane called {}", String.join(" ", map.keySet()));
            this.mainContentPane.getTableListPanel().getTableTree().removeAll();
            this.mainContentPane.getTableListPanel().getTableTree().setModel(tableTreeService.createJTreeFromHashMap(font.getName(), map).getModel());
            this.fontInspectorFrame.revalidate();
            this.fontInspectorFrame.repaint();
        } catch (IOException e) {
            log.error("couldn't read font", e);
            throw new RuntimeException(e);
        }
    }

}
