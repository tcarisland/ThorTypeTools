package com.tcarisland.thortype.fontinspector.gui;

import com.tcarisland.thortype.fontinspector.FontInspectorEventHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FontInspectorListenerFactory {

    private final FontInspectorEventHandler eventHandler;

    public FontInspectorListenerFactory(@Autowired FontInspectorEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public ActionListener openListener(JComponent parent) {
        return l -> openFile(parent);
    }

    private void openFile(JComponent parent) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("fonts", FontFileType.extensions());
        fileChooser.setFileFilter(fileNameExtensionFilter);
        int response = fileChooser.showOpenDialog(parent);
        if(response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            eventHandler.openFile(file);
        }
        log.info("Open clicked");
    }

    public ActionListener exitListener() {
        return l -> {
            log.info("Exit clicked");
            System.exit(0);
        };
    }

}
