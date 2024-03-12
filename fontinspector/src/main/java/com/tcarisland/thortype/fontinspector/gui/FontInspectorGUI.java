package com.tcarisland.thortype.fontinspector.gui;

import com.tcarisland.thortype.fontinspector.FontInspectorEventListener;
import com.tcarisland.thortype.fontinspector.FontInspectorProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFTable;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@Slf4j
public class FontInspectorGUI {

    private final FontInspectorProperties properties;
    private JFrame fontInspectorFrame;
    private JPanel mainContentPane;
    private FontTableList tableListPane;

    public FontInspectorGUI(@Autowired FontInspectorProperties properties,
                            @Autowired FontInspectorMenuFactory menuFactory,
                            @Autowired FontInspectorEventListener eventListener) {
        this.properties = properties;
        EventQueue.invokeLater(() -> {
            FontInspectorFrame frame = new FontInspectorFrame(properties);
            frame.setJMenuBar(menuFactory.initMenu(frame.getRootPane()));
            eventListener.setFontInspectorGUI(this);
            this.mainContentPane = initContentPane();
            this.tableListPane = initTableListPane();
            this.mainContentPane.add(this.tableListPane.getTableListPanel(), BorderLayout.WEST);
            frame.setContentPane(this.mainContentPane);
            this.fontInspectorFrame = frame;
            frame.repaint();
            frame.setVisible(true);
        });
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
        log.info("update content pane called {}", String.join(" ", map.keySet()));
        this.tableListPane.getTableTree().removeAll();
        try {
            this.tableListPane.getTableTree().setModel(createJTreeFromHashMap(font.getName(), map).getModel());
        } catch (IOException e) {
            log.error("couldn't read font", e);
            throw new RuntimeException(e);
        }
        this.fontInspectorFrame.revalidate();
        this.fontInspectorFrame.repaint();
    }

    private JTree createJTreeFromHashMap(String name, Map<String, TTFTable> hashMap) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(name);
        for (Map.Entry<String, TTFTable> entry : hashMap.entrySet()) {
            String parent = entry.getKey();
            TTFTable child = entry.getValue();
            DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parent);
            rootNode.add(parentNode);
            parentNode.add(new DefaultMutableTreeNode(child.getTag()));
        }
        return new JTree(rootNode);
    }

}
