package com.tcarisland.thortype.fontinspector;

import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class FontInspectorEventHandler {

    private final FontInspectorEventListener eventListener;

    public FontInspectorEventHandler(@Autowired FontInspectorEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void openFile(File file) {
        try {
            log.info("opening file {}", file.toPath());
            OTFParser otfParser = new OTFParser();
            OpenTypeFont font = otfParser.parse(file);
            eventListener.handleOpenedFont(font);
        } catch (IOException e) {
            log.error("could not open font", e);
            throw new RuntimeException(e);
        }
    }

}
