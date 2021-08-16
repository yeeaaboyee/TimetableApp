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
    private final List<Class> theList = new ArrayList<>(); // List of classes.
    private int idCount; // Number for keeping track of classes in the list.

    public Data() {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            File timetable = new File(classloader.getResource("Timetable.json").getFile()); // Reads the JSON file.
            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(timetable).get("Classes"); // Translate JSON to a JsonNode object...
            for (final JsonNode objNode : node) { // ...then to a class.
                Class tempClass = new Class( // Put the info into a temporary class.
                        objNode.get("Name").toString().substring(1, objNode.get("Name").toString().length() - 1),
                        objNode.get("Lecturer").toString().substring(1, objNode.get("Lecturer").toString().length() - 1),
                        objNode.get("Type").toString().substring(1, objNode.get("Type").toString().length() - 1),
                        objNode.get("Day").toString().substring(1, objNode.get("Day").toString().length() - 1),
                        objNode.get("Time").toString().substring(1, objNode.get("Time").toString().length() - 1),
                        objNode.get("Length").toString().substring(1, objNode.get("Length").toString().length() - 1),
                        objNode.get("Notes").toString().substring(1, objNode.get("Notes").toString().length() - 1)
                );
                theList.add(tempClass); // Add the temporary class to the list.
                idCount++; // Increase the idCount so the program can keep track of the list.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Class> getData() {
        return theList;
    }

    public void refreshData() { // Called every time a change is made. This class does not record the changes made to the JSON file.
        // Rather it makes the changes and repopulates its list with the JSON file once the JSON file has been changed.
        try {
            theList.clear(); // Clear the current list.
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
            // Code above is the same as the constructor.
            new Class("","","","","","","").setIdCount(idCount); // Make a temporary class to set the static id variable
            // to the id value of this class. Ensures the id variables line up in every case.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClass(String name, String lecturer, String type, String day, String time, String length, String notes) {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            File timetable = new File(classloader.getResource("Timetable.json").getFile()); // Read JSON file.
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            ObjectNode root = (ObjectNode) mapper.readTree(timetable); // Translate JSON to an ObjectNode.

            Class tempClass = new Class(name, lecturer, type, day, time, length, notes); // Temporary class to put the information into.

            ObjectNode aNode = new ObjectNode(mapper.getNodeFactory()); // A new node to insert data through.
            aNode.put("ID", tempClass.getId());
            aNode.put("Name", tempClass.getName());
            aNode.put("Lecturer", tempClass.getLecturer());
            aNode.put("Type", tempClass.getType());
            aNode.put("Day", tempClass.getDay());
            aNode.put("Time", tempClass.getTime());
            aNode.put("Length", tempClass.getLength());
            aNode.put("Notes", tempClass.getNotes()); // Add the data.
            ArrayNode classArray = (ArrayNode) root.get("Classes"); // ArrayNode of the classes in the JSON file.
            ObjectNode theClassNode = mapper.createObjectNode(); // The ArrayNode translated into an ObjectNode.

            classArray.add(aNode); // Add the new information ObjectNode into the Array from the JSON file.
            theClassNode.set("Classes", classArray); // Take the old array of information, and replace it with the new one with the new class.
            mapper.writeValue(timetable, theClassNode); // Write the new array into the JSON file.
            refreshData(); // Update the list of this class.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteClass(int id) {
        try {
            ClassLoader classloader = this.getClass().getClassLoader();
            File timetable = new File(classloader.getResource("Timetable.json").getFile()); // Read JSON file.
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            ObjectNode root = (ObjectNode) mapper.readTree(timetable);
            ArrayNode array = (ArrayNode) root.get("Classes"); // Translate the JSON into an ArrayNode.

            for (int i = 0; i < array.size(); i++) { // Loop that deletes the class using the id.
                if (id == i) {
                    array.remove(i); // Delete class.
                    break;
                }
            }

            root.replace("Classes", array); // Replace the data with this new array of data.
            mapper.writeValue(timetable, root); // Write that data to the JSON file.
            refreshData(); // Update the list of this class.
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
            // Above code converts JSON to an ArrayNode of class data.

            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).get("ID").asInt() == id) { // If the id matches the one provided...
                    JsonNode jsontemp = array.get(i);
                    ObjectNode objtemp = (ObjectNode) jsontemp;
                    objtemp.put("Name", replacementClass.getName());
                    objtemp.put("Lecturer", replacementClass.getLecturer());
                    objtemp.put("Type", replacementClass.getType());
                    objtemp.put("Day", replacementClass.getDay());
                    objtemp.put("Time", replacementClass.getTime());
                    objtemp.put("Length", replacementClass.getLength());
                    objtemp.put("Notes", replacementClass.getNotes());
                    // ...then replace the class data with the data provided.
                }
            }
            root.replace("Classes", array); // Replace the array with the new one.
            mapper.writeValue(timetable, root); // Write it to the JSON file.
            refreshData(); // Refresh the list.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}