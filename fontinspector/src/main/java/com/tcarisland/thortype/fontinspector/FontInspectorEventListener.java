package com.tcarisland.thortype.fontinspector;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@Slf4j
@Data
public class FontInspectorEventListener {

    private JFrame fontInspectorFrame;

    public void handleOpenedFont(OpenTypeFont openTypeFont) {
        log.info("font opened {}", openTypeFont);
        log.info("frame initialized {} {}", fontInspectorFrame != null, fontInspectorFrame);
    }

}
