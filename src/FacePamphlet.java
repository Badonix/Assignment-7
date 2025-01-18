/*
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.graphics.GImage;
import acm.program.Program;
import acm.util.ErrorException;

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

    FacePamphletProfile activeProfile;

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

    private void handleAddFriendEvent() {
        String inputValue = addFriendInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        if (activeProfile == null) {
            println("No active profile");
            return;
        }

        if (inputValue.equals(activeProfile.getName())) {
            println("Cannt become friends with yourself");
            return;
        }
        FacePamphletProfile possibleFriend = db.getProfile(inputValue);
        if (possibleFriend == null) {
            println("Such Profile does not exist");
            return;
        }

        activeProfile.addFriend(inputValue);
        possibleFriend.addFriend(activeProfile.getName());
        println("Friend Added " + inputValue);
    }

    private void handleEditImageEvent() {
        String inputValue = editImageInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        if (activeProfile == null) {
            println("No active profile");
            return;
        }
        GImage image;
        try {
            image = new GImage(inputValue);
        } catch (ErrorException ex) {
            println("Error, Image not found");
            return;
        }
        activeProfile.setImage(image);
        db.addProfile(activeProfile);
        println("Edit Image " + inputValue);
    }

    private void handleEditStatusEvent() {
        String inputValue = editStatusInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        if (activeProfile == null) {
            println("No active profile");
            return;
        }
        activeProfile.setStatus(inputValue);
        db.addProfile(activeProfile);
        println("New Status: " + activeProfile.toString());
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
        activeProfile = db.getProfile(inputValue);
        println("Lookup " + activeProfile.toString());
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
        activeProfile = null;
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
        activeProfile = profile;
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
