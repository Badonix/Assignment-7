/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.HashMap;
import java.util.Iterator;

public class FacePamphletDatabase implements FacePamphletConstants {

    private HashMap<String, FacePamphletProfile> users;

    /**
     * Constructor
     * This method takes care of any initialization needed for
     * the database.
     */
    public FacePamphletDatabase() {
        users = new HashMap<>();
    }


    /**
     * This method adds the given profile to the database.  If the
     * name associated with the profile is the same as an existing
     * name in the database, the existing profile is replaced by
     * the new profile passed in.
     */
    public void addProfile(FacePamphletProfile profile) {
        users.put(profile.getName(), profile);
    }


    /**
     * This method returns the profile associated with the given name
     * in the database.  If there is no profile in the database with
     * the given name, the method returns null.
     */
    public FacePamphletProfile getProfile(String name) {
        return users.get(name);
    }


    /**
     * This method removes the profile associated with the given name
     * from the database.  It also updates the list of friends of all
     * other profiles in the database to make sure that this name is
     * removed from the list of friends of any other profile.
     * <p>
     * If there is no profile in the database with the given name, then
     * the database is unchanged after calling this method.
     */
    public void deleteProfile(String name) {
        // At first, we must remove this person from their friends' friends list
        Iterator<String> profiles = users.get(name).getFriends();
        while (profiles.hasNext()) {
            String friendName = profiles.next();
            // Remove this profile from the friend's list of friends
            users.get(friendName).removeFriend(name);
        }
        // Finally, remove the profile from the users map
        users.remove(name);
    }


    /**
     * This method returns true if there is a profile in the database
     * that has the given name.  It returns false otherwise.
     */
    public boolean containsProfile(String name) {
        return users.containsKey(name);
    }

}
