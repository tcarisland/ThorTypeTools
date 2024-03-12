package com.tcarisland.thortype.fontinspector.services;

import lombok.Data;
import org.apache.fontbox.ttf.TTFTable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Map;

@Service
@Data
public class TableTreeService {
    public JTree createJTreeFromHashMap(String name, Map<String, TTFTable> hashMap) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(name);
        for (Map.Entry<String, TTFTable> entry : hashMap.entrySet()) {
            String parent = entry.getKey();
            TTFTable child = entry.getValue();
            DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parent);
            rootNode.add(parentNode);
            parentNode.add(new DefaultMutableTreeNode(child.getTag()));
        }
        return new JTree(rootNode);
    }
}
