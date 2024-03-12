package com.tcarisland.thortype.fontinspector;

import com.tcarisland.thortype.fontinspector.types.FontFileType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
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
            FontFileType fileType = FontFileType.fromExtension(FilenameUtils.getExtension(file.getAbsolutePath()));
            switch (fileType) {
                case TRUETYPE:
                    openTrueType(file);
                    return;
                case WEBFONT:
                case OPENTYPE:
                    openOpenType(file);
                    return;
                default:
                    log.error("could not open file {}", file);
            }
        } catch (IOException e) {
            log.error("could not open font", e);
            throw new RuntimeException(e);
        }
    }

    public void openTrueType(File file) throws IOException {
        TTFParser ttfParser = new TTFParser();
        TrueTypeFont font = ttfParser.parse(file);
        eventListener.handleTrueTypeFont(font);
    }

    public void openOpenType(File file) throws IOException {
        OTFParser otfParser = new OTFParser();
        OpenTypeFont font = otfParser.parse(file);
        eventListener.handleTrueTypeFont(font);
    }

}
