package nl.impress.app;


import nl.impress.app.data.User;
import nl.impress.app.data.UserStorage;
import nl.impress.app.data.image.Thumbnail;
import nl.impress.app.exceptions.ImageStoreException;
import nl.impress.app.exceptions.UserStoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public abstract class UserStorageTest {

    protected UserStorage userStore;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Spy
    protected User spyUser = new User("test-username", "test-password", "test-bio");

    @Before
    public abstract void setup();

    @After
    public abstract void cleanup();

    @Test
    public void findUser_byUsername_NotFound() {
        thrown.expect(UserStoreException.NotFound.class);
        userStore.findByUsername("godzilla");
    }

    @Test
    public void insertUser_requiresUsername() {
        userStore.storeUser(spyUser);
        verify(spyUser, atLeastOnce()).getUsername();
    }

    @Test
    public void insertAndRetrieveUser_equal() {
        userStore.storeUser(spyUser);
        User actual = userStore.findByUsername(spyUser.getUsername());
        assertEquals(spyUser, actual);
    }

    @Test
    public void insertTwo_retrieveCorrectUser() {
        User userOne = new User("1", "password", "bio");
        User userTwo = new User("2", "drowssap", "oib");

        userStore.storeUser(userOne);
        userStore.storeUser(userTwo);

        assertEquals(userOne, userStore.findByUsername(userOne.getUsername()));
        assertEquals(userTwo, userStore.findByUsername(userTwo.getUsername()));
    }

    @Test
    public void insertUser_duplicate_AlreadyExists() {
        thrown.expect(UserStoreException.AlreadyExists.class);
        userStore.storeUser(new User("1", "password", "bio"));
        userStore.storeUser(new User("1", "drowssap", "oib"));
    }

    @Test
    public void updateUser_newUsername_NotFoundException() {
        thrown.expect(UserStoreException.NotFound.class);
        User userOne = new User("oldUsername", "password", "bio");
        User userUpdated = new User("newUsername", "password", "bio");

        userStore.storeUser(userOne);
        userStore.updateUser(userUpdated);
    }

    @Test
    public void updateUser_newPassword() {
        String username = "1";
        String newPassword = "newPassword";
        User userOne = new User(username, "password", "bio");
        User userUpdated = new User(username, newPassword, "bio");

        userStore.storeUser(userOne);
        userStore.updateUser(userUpdated);

        assertEquals(newPassword, userStore.findByUsername(username).getPassword());
    }

    @Test
    public void updateUser_newBio() {
        String username = "1";
        String newBio = "newBio";
        User userOne = new User(username, "password", "bio");
        User userUpdated = new User(username, "password", newBio);

        userStore.storeUser(userOne);
        userStore.updateUser(userUpdated);

        assertEquals(newBio, userStore.findByUsername(username).getBio());
    }

    @Test
    public void updateUser_setHasProfilePic() {
        String username = "1";
        User userOne = new User(username, "password", "bio", false);
        User userUpdated = new User(username, "password", "bio", true);

        userStore.storeUser(userOne);
        userStore.updateUser(userUpdated);

        assertTrue(userStore.findByUsername(userOne.getUsername()).hasProfilePicture());
    }

    @Test
    public void storeProfilePicture_invalidUser_notFound() {
        thrown.expect(UserStoreException.NotFound.class);
        Thumbnail t = mock(Thumbnail.class);
        userStore.storeProfilePicture(spyUser, t);
    }

    @Test
    public void getProfilePicture_InvalidUser_NotFoundException() {
        thrown.expect(ImageStoreException.NotFound.class);
        userStore.getProfilePicture("someNonExistingUser");
    }

    @Test
    public void getProfilePicture_userWithoutPicture_NotFound() {
        thrown.expect(ImageStoreException.NotFound.class);
        userStore.storeUser(spyUser);
        userStore.getProfilePicture(spyUser.getUsername());
    }

    @Test
    public void storeAndRetrieveProfilePicture_ShouleBeEqual() throws IOException {
        Thumbnail t = Util.getThumbnail();

        userStore.storeUser(spyUser);
        userStore.storeProfilePicture(spyUser, t);
        assertEquals(t, userStore.getProfilePicture(spyUser.getUsername()));
    }

    @Test
    public void deleteUser_invalidUser_NotFoundException() {
        thrown.expect(UserStoreException.NotFound.class);
        userStore.removeUser(spyUser);
    }

    @Test
    public void deleteUser() {
        userStore.storeUser(spyUser);
        userStore.removeUser(spyUser);

        thrown.expect(UserStoreException.NotFound.class);
        userStore.findByUsername(spyUser.getUsername());
    }

}
