package nl.impress.app.data;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.UserStoreException;
import nl.impress.app.exceptions.ImageStoreException;

import java.util.Map;

/**
 * All users are stored in a {@link Map} in working memory.
 * This class is provided so you can see what functionallity
 * you have to create.
 */
@Singleton
public class InMemoryUserStorage implements UserStorage {

    private Map<String, User> users;
    private Map<String, Thumbnail> profilePictures;

    /**
     * Populate our in-memory 'database' with some standard accounts.
     */
    public InMemoryUserStorage() {
        this.users = Maps.newHashMap();
        this.profilePictures = Maps.newHashMap();
        this.storeUser(new User("thomas", "t", "Software Developer"));
        this.storeUser(new User("firstUser", "verysecret", "Lorum ipsum enzo."));
    }

    @Override
    public User findByUsername(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        } else {
            throw new UserStoreException.NotFound();
        }
    }

    @Override
    public void storeUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new UserStoreException.AlreadyExists();
        } else {
            users.put(user.getUsername(), user);
        }
    }

    @Override
    public void updateUser(User user) {
        if (users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
        } else {
            throw new UserStoreException.NotFound();
        }
    }

    @Override
    public void removeUser(User user) {
        if (users.containsKey(user.getUsername())) {
            users.remove(user.getUsername());
        } else {
            throw new UserStoreException.NotFound();
        }
    }

    @Override
    public void storeProfilePicture(User user, Thumbnail thumbnail) {
        if (!users.containsKey(user.getUsername())) {
            throw new UserStoreException.NotFound();
        }
        profilePictures.put(user.getUsername(), thumbnail);
    }

    @Override
    public Thumbnail getProfilePicture(String userName) {
        Thumbnail result = profilePictures.get(userName);
        if (result == null) {
            throw new ImageStoreException.NotFound();
        }
        return result;
    }
}
