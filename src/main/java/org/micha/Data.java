package org.micha;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Data {
    private final List<Class> theList = new ArrayList<>();
    private int idCount;

    public Data() {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            File timetable = new File(classloader.getResource("Timetable.json").getFile());
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(timetable).get("Classes");
            for (final JsonNode objNode : node) {
                Class tempClass = new Class(
                        objNode.get("Name").toString().substring(1, objNode.get("Name").toString().length() - 1),
                        objNode.get("Lecturer").toString().substring(1, objNode.get("Lecturer").toString().length() - 1),
                        objNode.get("Type").toString().substring(1, objNode.get("Type").toString().length() - 1),
                        objNode.get("Day").toString().substring(1, objNode.get("Day").toString().length() - 1),
                        objNode.get("Time").toString().substring(1, objNode.get("Time").toString().length() - 1),
                        objNode.get("Length").toString().substring(1, objNode.get("Length").toString().length() - 1),
                        objNode.get("Notes").toString().substring(1, objNode.get("Notes").toString().length() - 1)
                );
                theList.add(tempClass);
                idCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Class> getData() {
        return theList;
    }

    public void refreshData() {
        try {
            theList.clear();
            ClassLoader classloader = this.getClass().getClassLoader();
            File timetable = new File(classloader.getResource("Timetable.json").getFile());
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(timetable).get("Classes");
            for (final JsonNode objNode : node) {
                Class tempClass = new Class(
                        objNode.get("Name").toString().substring(1, objNode.get("Name").toString().length() - 1),
                        objNode.get("Lecturer").toString().substring(1, objNode.get("Lecturer").toString().length() - 1),
                        objNode.get("Type").toString().substring(1, objNode.get("Type").toString().length() - 1),
                        objNode.get("Day").toString().substring(1, objNode.get("Day").toString().length() - 1),
                        objNode.get("Time").toString().substring(1, objNode.get("Time").toString().length() - 1),
                        objNode.get("Length").toString().substring(1, objNode.get("Length").toString().length() - 1),
                        objNode.get("Notes").toString().substring(1, objNode.get("Notes").toString().length() - 1)
                );
                theList.add(tempClass);
                idCount++;
            }
            new Class("","","","","","","").setIdCount(idCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClass(String name, String lecturer, String type, String day, String time, String length, String notes) {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            File timetable = new File(classloader.getResource("Timetable.json").getFile());
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            ObjectNode root = (ObjectNode) mapper.readTree(timetable);

            Class tempClass = new Class(name, lecturer, type, day, time, length, notes);

            ObjectNode aNode = new ObjectNode(mapper.getNodeFactory());
            aNode.put("ID", tempClass.getId());
            aNode.put("Name", tempClass.getName());
            aNode.put("Lecturer", tempClass.getLecturer());
            aNode.put("Type", tempClass.getType());
            aNode.put("Day", tempClass.getDay());
            aNode.put("Time", tempClass.getTime());
            aNode.put("Length", tempClass.getLength());
            aNode.put("Notes", tempClass.getNotes());
            ArrayNode classArray = (ArrayNode) root.get("Classes");
            ObjectNode theClassNode = mapper.createObjectNode();

            classArray.add(aNode);
            theClassNode.set("Classes", classArray);
            mapper.writeValue(timetable, theClassNode);
            refreshData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteClass(int id) {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            File timetable = new File(classloader.getResource("Timetable.json").getFile());
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectNode root = (ObjectNode) mapper.readTree(timetable);
            ArrayNode array = (ArrayNode) root.get("Classes");

            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).get("ID").asInt() == id) {
                    array.remove(i);
                    break;
                }
            }

            root.replace("Classes", array);
            mapper.writeValue(timetable, root);
            refreshData();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void editClass(int id, Class replacementClass) {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            File timetable = new File(classloader.getResource("Timetable.json").getFile());
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectNode root = (ObjectNode) mapper.readTree(timetable);
            ArrayNode array = (ArrayNode) root.get("Classes");

            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).get("ID").asInt() == id) {
                    JsonNode jsontemp = array.get(i);
                    ObjectNode objtemp = (ObjectNode) jsontemp;
                    objtemp.put("Name", replacementClass.getName());
                    objtemp.put("Lecturer", replacementClass.getLecturer());
                    objtemp.put("Type", replacementClass.getType());
                    objtemp.put("Day", replacementClass.getDay());
                    objtemp.put("Time", replacementClass.getTime());
                    objtemp.put("Length", replacementClass.getLength());
                    objtemp.put("Notes", replacementClass.getNotes());
                }
            }
            root.replace("Classes", array);
            mapper.writeValue(timetable, root);
            refreshData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}