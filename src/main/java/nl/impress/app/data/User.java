package nl.impress.app.data;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Immutable structure for holding all of a users information.
 */
public class User {

    private final String username;
    private final String password;
    private final String bio;
    private final Boolean hasProfileImage;

    public User(String username, String password, String bio) {
        this(username, password, bio, false);
    }

    public User(String username, String password, String bio, Boolean hasProfileImage) {
        checkNotNull(username, password, bio, hasProfileImage);
        checkArgument(!username.isEmpty());
        checkArgument(!password.isEmpty());
        checkArgument(!bio.isEmpty());
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.hasProfileImage = hasProfileImage;
    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getBio() {
        return this.bio;
    }

    public Boolean hasProfilePicture() {
        return this.hasProfileImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return username.equals(user.username);
    }
    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
