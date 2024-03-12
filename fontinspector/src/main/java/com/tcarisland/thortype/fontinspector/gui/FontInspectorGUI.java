package com.tcarisland.thortype.fontinspector.gui;

import com.tcarisland.thortype.fontinspector.FontInspectorEventListener;
import com.tcarisland.thortype.fontinspector.FontInspectorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class FontInspectorGUI {

    public FontInspectorGUI(@Autowired FontInspectorProperties properties,
                            @Autowired FontInspectorMenuFactory menuFactory,
                            @Autowired FontInspectorEventListener eventListener) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FontInspectorFrame frame = new FontInspectorFrame(properties);
                frame.setVisible(true);
                frame.setJMenuBar(menuFactory.initMenu(frame.getRootPane()));
                eventListener.setFontInspectorFrame(frame);
            }
        });
    }
}
