package com.tcarisland.thortype.fontinspector.services;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.fontbox.ttf.TTFTable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;
import java.util.stream.Stream;

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
                DefaultMutableTreeNode childNode;
                if(field.getType().isPrimitive()) {
                    childNode = createPrimitiveDatatypeTreeNode(table, field);
                } else if(child != null && isSearchableType(field)) {
                    childNode = createObjectTypeTreeNode(child, field);
                } else {
                    childNode = new DefaultMutableTreeNode(field.getName());
                }
                parentNode.add(childNode);
            } catch (IllegalAccessException e) {
                log.error("could not create table", e);
            }
        }
        return tableRootNode;
    }

    public boolean isSearchableType(Field f) {
        boolean isString = StringUtils.equalsIgnoreCase(f.getType().getName(), "string");
        boolean isFont = StringUtils.equalsIgnoreCase(f.getType().getName(), "font");
        return !isString && !isFont;
    }

    public DefaultMutableTreeNode createObjectTypeTreeNode(Object o, Field f) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(f.getName());
        for(Field field : f.getType().getDeclaredFields()) {
            try {
                if(isSearchableType(field)) {
                    field.setAccessible(true);
                    Object child = field.get(o);
                    if(field.getType().isPrimitive()) {
                        DefaultMutableTreeNode childNode = createPrimitiveDatatypeTreeNode(o, field);
                        treeNode.add(childNode);
                    } else if(child != null && Map.class.isAssignableFrom(field.getType())) {
                        DefaultMutableTreeNode childNode = createMapTypeTreeNode(child, field);
                        treeNode.add(childNode);
                    } else if(child != null && List.class.isAssignableFrom(field.getType())) {
                        DefaultMutableTreeNode childNode = createListTypeTreeNode(child, field);
                        treeNode.add(childNode);
                    } else {
                        DefaultMutableTreeNode childNode = createObjectTypeTreeNode(child, field);
                        treeNode.add(childNode);
                    }
                }
            } catch (IllegalAccessException | InaccessibleObjectException e) {
                log.info("could not make the field {} accessible", field.getName());
            }
        }
        return treeNode;
    }

    public boolean isMap(Object o, Field f) {
        boolean assignableMap = Map.class.isAssignableFrom(f.getType());
        boolean isCastableMap = true;
        try {
            Map<?,?> map = (Map<?,?>) o;
        } catch (Exception e) {
            log.info("could not cast {} {}", o, f);
        }
        return assignableMap && isCastableMap;
    }
    public boolean isList(Object o, Field f) {
        boolean assignableList = List.class.isAssignableFrom(f.getType());
        boolean isCastableList = true;
        try {
            List<?> map = (List<?>) o;
        } catch (Exception e) {
            log.info("could not cast {} {}", o, f);
        }
        return assignableList && isCastableList;
    }

    private DefaultMutableTreeNode createListTypeTreeNode(Object o, Field field) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(field.getName());
        List<?> list = (List<?>) o;
        for (Object object : list) {
            treeNode.add(new DefaultMutableTreeNode(object));
        }
        return treeNode;
    }

    private DefaultMutableTreeNode createMapTypeTreeNode(Object o, Field field) {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(field.getName());
        Map<?,?> map = (Map<?,?>) o;
        map.keySet().forEach(u -> {
            DefaultMutableTreeNode keyNode = new DefaultMutableTreeNode(u);
            DefaultMutableTreeNode valueNode = new DefaultMutableTreeNode(map.get(u));
            keyNode.add(valueNode);
            treeNode.add(keyNode);
        });
        return treeNode;
    }

    private DefaultMutableTreeNode createPrimitiveDatatypeTreeNode(Object table, Field field) throws IllegalAccessException {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(field.getName());
        DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(field.getType().toString());
        DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode("" + field.get(table));
        treeNode.add(typeNode);
        treeNode.add(dataNode);
        return treeNode;
    }
}
