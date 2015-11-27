package nl.impress.app.data;

import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.UserStoreException;
import nl.impress.app.exceptions.ImageStoreException;

/**
 * Classes implementing this interfaces should allow you to
 * store any User object.
 *
 * There are is currently one implementation: InMemoryUserStorage
 *
 * You are going to create another implementation for this interface
 * that uses HBase for find/store operations!
 */
public interface UserStorage {

    /**
     * Retrieve a user form the data store.
     *
     * @param username unique identifier for every user.
     * @return fully initialized {@link User} object.
     * @throws UserStoreException.NotFound if the username does not
     *                                     exist in the data store.
     */
    User findByUsername(String username) throws UserStoreException.NotFound;

    /**
     * Store a newly created user into the database.
     *
     * @param user Fully initialized {@link User} object.
     * @throws UserStoreException.AlreadyExists if there already exists
     *                                       a user with the same name.
     */
    void storeUser(User user) throws UserStoreException.AlreadyExists;

    /**
     * Store updated user information into the database.
     *
     * @param user Fully initialized {@link User} object.
     * @throws UserStoreException.NotFound if the username does not
     *                                     exist in the data store.
     */
    void updateUser(User user) throws UserStoreException.NotFound;

    /**
     * Remove an existing user from the data store.
     *
     * @param user Fully initialized {@link User} object.
     * @throws UserStoreException.NotFound if the username does not
     *                                     exist in the data store.
     */
    void removeUser(User user) throws UserStoreException.NotFound;

    /**
     * Store a thumbnail sized picture ans associate it with the given user.
     * @param user The user that uploaded the picture.
     * @param thumbnail The profile image.
     * @throws UserStoreException.NotFound if the user does not exist in the database.
     */
    void storeProfilePicture(User user, Thumbnail thumbnail) throws UserStoreException.NotFound;

    /**
     * Get the profile picture currently set by that user.
     * @param userName The user to get the picture from.
     * @return Thumbnail sized image.
     * @throws ImageStoreException.NotFound if the user has no profile picture.
     */
    Thumbnail getProfilePicture(String userName) throws ImageStoreException.NotFound;
}
