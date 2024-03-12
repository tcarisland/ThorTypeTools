package com.tcarisland.thortype.fontinspector;

import com.tcarisland.thortype.fontinspector.gui.FontInspectorGUI;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFTable;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Map;

@Component
@Slf4j
@Data
public class FontInspectorEventListener {

    private FontInspectorGUI fontInspectorGUI;

    public void handleOpenTypeFont(OpenTypeFont openTypeFont) {
        log.info("font opened {}", openTypeFont);
        Map<String, TTFTable> map = openTypeFont.getTableMap();
        this.fontInspectorGUI.updateContentPane(openTypeFont, map);
    }

    public void handleTrueTypeFont(TrueTypeFont trueTypeFont) {
        log.info("font opened {}", trueTypeFont);
        Map<String, TTFTable> map = trueTypeFont.getTableMap();
        this.fontInspectorGUI.updateContentPane(trueTypeFont, map);
    }
}
