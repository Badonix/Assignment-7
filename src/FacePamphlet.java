/*
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.Program;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FacePamphlet extends Program
        implements FacePamphletConstants {

    private final JTextField nameInput = new JTextField(TEXT_FIELD_SIZE);
    private final JTextField editStatusInput = new JTextField(TEXT_FIELD_SIZE);
    private final JTextField editImageInput = new JTextField(TEXT_FIELD_SIZE);
    private final JTextField addFriendInput = new JTextField(TEXT_FIELD_SIZE);
    private final JButton addButton = new JButton("Add");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton lookupButton = new JButton("Lookup");
    private final JButton editStatusButton = new JButton("Change Status");
    private final JButton editImageButton = new JButton("Change Picture");
    private final JButton addFriendButton = new JButton("Add Friend");

    FacePamphletDatabase db;

    /**
     * This method has the responsibility for initializing the
     * interactors in the application, and taking care of any other
     * initialization that needs to be performed.
     */
    public void init() {
        initTopbar();
        initSidebar();
        addActionListeners();
        editStatusInput.addActionListener(this);
        editImageInput.addActionListener(this);
        addFriendInput.addActionListener(this);

        db = new FacePamphletDatabase();
    }


    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
        Object actionSource = e.getSource();
        if (actionSource == addButton) {
            handleAddEvent();
        } else if (actionSource == deleteButton) {
            handleDeleteEvent();
        } else if (actionSource == lookupButton) {
            handleLookup();
        } else if (actionSource == editStatusButton || actionSource == editStatusInput) {
            handleEditStatusEvent();
        } else if (actionSource == editImageButton || actionSource == editImageInput) {
            handleEditImageEvent();
        } else if (actionSource == addFriendButton || actionSource == addFriendInput) {
            handleAddFriendEvent();
        }
    }

    private void handleNotFound(String name) {

    }

    private void handleAddFriendEvent() {
        String inputValue = addFriendInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        println("Add Friend " + inputValue);
    }

    private void handleEditImageEvent() {
        String inputValue = editImageInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        println("Edit Image " + inputValue);
    }

    private void handleEditStatusEvent() {
        String inputValue = editStatusInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        println("Edit Status " + inputValue);
    }

    private void handleLookup() {
        String inputValue = nameInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        if (!db.containsProfile(inputValue)) {
            println("Profile " + inputValue + " does not exist");
            return;
        }
        println("Lookup " + db.getProfile(inputValue).toString());
    }

    private void handleDeleteEvent() {
        String inputValue = nameInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        if (!db.containsProfile(inputValue)) {
            println("Profile " + inputValue + " does not exist");
            return;
        }
        db.deleteProfile(inputValue);
        println("Profile " + inputValue + " deleted");
    }

    private void handleAddEvent() {
        String inputValue = nameInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        if (db.containsProfile(inputValue)) {
            // Message that already exists
            println("Already Exists! " + db.getProfile(inputValue).toString());
            return;
        }
        FacePamphletProfile profile = new FacePamphletProfile(inputValue);
        db.addProfile(profile);
        println("Add Profile: " + profile);

    }

    private void initTopbar() {
        add(new JLabel("Name "), NORTH);
        add(nameInput, NORTH);
        add(addButton, NORTH);
        add(deleteButton, NORTH);
        add(lookupButton, NORTH);
    }

    private void initSidebar() {
        add(editStatusInput, WEST);
        add(editStatusButton, WEST);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST);
        add(editImageInput, WEST);
        add(editImageButton, WEST);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST);
        add(addFriendInput, WEST);
        add(addFriendButton, WEST);
    }

}
