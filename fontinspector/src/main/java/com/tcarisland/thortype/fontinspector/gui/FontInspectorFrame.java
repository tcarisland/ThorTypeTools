package com.tcarisland.thortype.fontinspector.gui;

import com.tcarisland.thortype.fontinspector.FontInspectorProperties;

import javax.swing.*;

public class FontInspectorFrame extends JFrame {

    private final FontInspectorProperties properties;

    public FontInspectorFrame(FontInspectorProperties properties) {
        super();
        this.properties = properties;
        setSize(properties.getWidth(), properties.getHeight());
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(properties.getWidth(), properties.getHeight());
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
