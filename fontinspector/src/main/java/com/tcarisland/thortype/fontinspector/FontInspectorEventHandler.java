package com.tcarisland.thortype.fontinspector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FontInspectorEventHandler {

    public void openFile(File file) {
        log.info("opening file {}", file.toPath());
    }
}
