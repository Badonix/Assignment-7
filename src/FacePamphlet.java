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

    /**
     * This method has the responsibility for initializing the
     * interactors in the application, and taking care of any other
     * initialization that needs to be performed.
     */
    public void init() {
        initTopbar();
        initSidebar();
    }


    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
        // You fill this in as well as add any additional methods
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
