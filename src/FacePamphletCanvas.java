/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.util.Iterator;

public class FacePamphletCanvas extends GCanvas
        implements FacePamphletConstants {

    GLabel displayMessage;
    GLabel nameLabel;
    GLabel statusLabel;
    GLabel friendsTitle;

    /**
     * Constructor
     * This method takes care of any initialization needed for
     * the display
     */
    public FacePamphletCanvas() {
    }


    /**
     * This method displays a message string near the bottom of the
     * canvas.  Every time this method is called, the previously
     * displayed message (if any) is replaced by the new message text
     * passed in.
     */
    public void showMessage(String msg) {
        if (displayMessage != null) {
            remove(displayMessage);
        }
        displayMessage = new GLabel(msg);
        displayMessage.setFont(MESSAGE_FONT);
        double y = getHeight() - BOTTOM_MESSAGE_MARGIN - displayMessage.getAscent();
        double x = (getWidth() - displayMessage.getWidth()) / 2;
        add(displayMessage, x, y);
    }


    /**
     * This method displays the given profile on the canvas.  The
     * canvas is first cleared of all existing items (including
     * messages displayed near the bottom of the screen) and then the
     * given profile is displayed.  The profile display includes the
     * name of the user from the profile, the corresponding image
     * (or an indication that an image does not exist), the status of
     * the user, and a list of the user's friends in the social network.
     */
    public void displayProfile(FacePamphletProfile profile) {
        removeAll();
        renderName(profile.getName());
        renderImage(profile.getImage());
        renderStatus(profile.getStatus());
        renderFriends(profile.getFriends());
    }

    private void renderFriends(Iterator<String> friends) {
        double x = getWidth() / 2.0;
        double y = IMAGE_MARGIN + TOP_MARGIN;
        // Render the header
        y = renderFriendsTitle("Friends:", x, y);
        // Render each friend's label
        while (friends.hasNext()) {
            y = renderFriendLabel(friends.next(), x, y);
        }
    }

    /**
     * Renders the title and returns the updated y-coordinate.
     */
    private double renderFriendsTitle(String title, double x, double y) {
        GLabel titleLabel = new GLabel(title);
        titleLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
        add(titleLabel, x, y);
        return y + titleLabel.getHeight();
    }

    /**
     * Renders a friend's label and returns the updated y-coordinate.
     */
    private double renderFriendLabel(String friendName, double x, double y) {
        GLabel friendLabel = new GLabel(friendName);
        friendLabel.setFont(PROFILE_FRIEND_FONT);
        add(friendLabel, x, y);
        return y + friendLabel.getHeight();
    }

    private void renderImage(GImage image) {
        double y = nameLabel.getAscent() + TOP_MARGIN + IMAGE_MARGIN;
        if (image == null) {
            GRect rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
            rect.setLocation(LEFT_MARGIN, y);
            add(rect);
            GLabel message = new GLabel("No Image");
            message.setFont(PROFILE_IMAGE_FONT);
            message.setLocation(LEFT_MARGIN + (IMAGE_WIDTH / 2) - (message.getWidth() / 2), y + (IMAGE_HEIGHT / 2));
            add(message);
        } else {
            double sx = IMAGE_WIDTH / image.getWidth();
            double sy = IMAGE_HEIGHT / image.getHeight();
            image.scale(sx, sy);
            add(image, LEFT_MARGIN, y);
        }
    }

    private void renderName(String name) {
        nameLabel = new GLabel(name);
        nameLabel.setFont(PROFILE_NAME_FONT);
        add(nameLabel, LEFT_MARGIN, TOP_MARGIN);
    }

    private void renderStatus(String name) {
        double y = nameLabel.getAscent() + TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN;
        statusLabel = new GLabel(name);
        statusLabel.setFont(PROFILE_STATUS_FONT);
        add(statusLabel, LEFT_MARGIN, y);
    }
}
