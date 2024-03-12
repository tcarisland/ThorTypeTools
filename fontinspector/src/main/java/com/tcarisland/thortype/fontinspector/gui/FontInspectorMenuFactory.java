package com.tcarisland.thortype.fontinspector.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Arrays;

@Component
public class FontInspectorMenuFactory {

    private final FontInspectorListenerFactory listenerFactory;

    public FontInspectorMenuFactory(@Autowired FontInspectorListenerFactory listenerFactory) {
        this.listenerFactory = listenerFactory;
    }

    public JMenuBar initMenu(JComponent parent) {
        JMenuBar mainMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        openMenuItem.addActionListener(listenerFactory.openListener(parent));
        exitMenuItem.addActionListener(listenerFactory.exitListener());
        Arrays.asList(openMenuItem, exitMenuItem).forEach(fileMenu::add);
        mainMenuBar.add(fileMenu);
        return mainMenuBar;
    }

}
