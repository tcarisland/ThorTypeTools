package com.tcarisland.thortype.fontinspector.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.fontbox.ttf.TTFTable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Data
@Slf4j
public class TableTreeService {

    public JTree createDefaultTree() {
        return new JTree(new DefaultMutableTreeNode("no font selected"));
    }

    public JTree createJTreeFromHashMap(String name, Map<String, TTFTable> hashMap) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(name);
        for (Map.Entry<String, TTFTable> entry : hashMap.entrySet()) {
            String parent = entry.getKey();
            TTFTable child = entry.getValue();
            DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parent);
            rootNode.add(parentNode);
            parentNode.add(createDataFromTable(child));
        }
        return new JTree(rootNode);
    }

    public DefaultMutableTreeNode createDataFromTable(TTFTable table) {
        DefaultMutableTreeNode tableRootNode = new DefaultMutableTreeNode(table.getTag());
        for(Field field : table.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                String parent = field.getName();
                DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parent);
                tableRootNode.add(parentNode);
                Object child = field.get(table);
                log.info("child class {} {} {}", field.getName(), field.getType(), child != null ? child.getClass() : null);
                log.info("child is primitive {} {}", field.getType().isPrimitive(), field.getClass());
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("" + child);
                parentNode.add(childNode);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return tableRootNode;
    }
}
