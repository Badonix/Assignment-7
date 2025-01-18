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
    FacePamphletCanvas canvas;

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
        canvas = new FacePamphletCanvas();
        add(canvas);
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
            canvas.showMessage("Please select a profile to add friend");
            return;
        }

        // Check if is trying to add himself as a friend
        if (inputValue.equals(activeProfile.getName())) {
            canvas.showMessage("Can't become friends with yourself :(");
            return;
        }
        FacePamphletProfile possibleFriend = db.getProfile(inputValue);

        // Check if exists
        if (possibleFriend == null) {
            canvas.showMessage(inputValue + " doesnt exist");
            return;
        }

        // Check if already is a friend
        if (!activeProfile.addFriend(inputValue)) {
            canvas.showMessage(activeProfile.getName() + " already has " + inputValue + " as a friend");
            return;
        }
        possibleFriend.addFriend(activeProfile.getName());
        canvas.showMessage(inputValue + " added as a friend");
    }

    private void handleEditImageEvent() {
        String inputValue = editImageInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        if (activeProfile == null) {
            canvas.showMessage("Please select a profile to change picture");
            return;
        }

        // Trying to get the image
        GImage image;
        try {
            image = new GImage(inputValue);
        } catch (ErrorException ex) {
            canvas.showMessage("Unable to open image file " + inputValue);
            return;
        }
        activeProfile.setImage(image);
        db.addProfile(activeProfile);
        canvas.displayProfile(activeProfile);
        canvas.showMessage("Picture updated");
    }

    // Pretty straightforward...
    private void handleEditStatusEvent() {
        String inputValue = editStatusInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        if (activeProfile == null) {
            canvas.showMessage("Please select a profile to change status");
            return;
        }
        activeProfile.setStatus(inputValue);
        db.addProfile(activeProfile);
        canvas.displayProfile(activeProfile);
        canvas.showMessage("Status updated to " + inputValue);
    }

    private void handleLookup() {
        String inputValue = nameInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        // We need to clear canvas on each lookup
        if (!db.containsProfile(inputValue)) {
            canvas.removeAll();
            canvas.showMessage("Profile with the name " + inputValue + " doesnt exist");
            return;
        }
        activeProfile = db.getProfile(inputValue);
        canvas.displayProfile(activeProfile);
        canvas.showMessage("Displaying " + activeProfile.getName());
    }

    private void handleDeleteEvent() {
        String inputValue = nameInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        // Check if exists...
        if (!db.containsProfile(inputValue)) {
            canvas.showMessage("A profile with the name " + inputValue + " doesnt exist");
            return;
        }

        // Clear canvas and set active to null
        canvas.removeAll();
        db.deleteProfile(inputValue);
        activeProfile = null;
        canvas.showMessage("Profile of " + inputValue + " deleted");
    }

    private void handleAddEvent() {
        String inputValue = nameInput.getText();
        if (inputValue.isEmpty()) {
            return;
        }
        // If it exists we need a displayMessage
        if (db.containsProfile(inputValue)) {
            canvas.displayProfile(db.getProfile(inputValue));
            canvas.showMessage("A profile with the name " + inputValue + " already exists");
            return;
        }
        FacePamphletProfile profile = new FacePamphletProfile(inputValue);
        db.addProfile(profile);
        activeProfile = profile;
        canvas.displayProfile(activeProfile);
        canvas.showMessage("New profile created");

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
