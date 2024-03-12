package com.tcarisland.thortype.fontinspector.gui;

import lombok.Data;
import org.apache.fontbox.ttf.TTFTable;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;


@Data
public class FontTableList {
    private JPanel tableListPanel;
    private JTree tableTree;

    public void createTable(Map<String, TTFTable> tableMap) {
        Hashtable<String, TTFTable> hashtable = (Hashtable<String, TTFTable>) Collections.synchronizedMap(tableMap);

    }
}
