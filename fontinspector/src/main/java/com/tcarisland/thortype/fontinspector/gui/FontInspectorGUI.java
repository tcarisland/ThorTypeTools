package com.tcarisland.thortype.fontinspector.gui;

import com.tcarisland.thortype.fontinspector.FontInspectorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class FontInspectorGUI {

    public FontInspectorGUI(@Autowired FontInspectorProperties properties, @Autowired FontInspectorMenuFactory menuFactory) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FontInspectorFrame frame = new FontInspectorFrame(properties);
                frame.setVisible(true);
                frame.setJMenuBar(menuFactory.initMenu(frame.getRootPane()));
            }
        });
    }
}
