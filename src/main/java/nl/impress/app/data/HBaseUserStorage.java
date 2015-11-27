package nl.impress.app.data;


import com.google.inject.Singleton;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.ImageStoreException;
import nl.impress.app.exceptions.UserStoreException;

/**
 * Implement this class!
 * It should talk to HBase in order to store/retreive users.
 */
@Singleton
public class HBaseUserStorage implements UserStorage {

    @Override
    public User findByUsername(String username)
            throws UserStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void storeUser(User user)
            throws UserStoreException.AlreadyExists {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void updateUser(User user)
            throws UserStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void removeUser(User user)
            throws UserStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void storeProfilePicture(User user, Thumbnail thumbnail)
            throws UserStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Thumbnail getProfilePicture(String userName)
            throws ImageStoreException.NotFound {
        throw new RuntimeException("Not yet implemented");
    }
}
