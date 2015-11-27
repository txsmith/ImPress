package nl.impress.app;

import nl.impress.app.data.InMemoryUserStorage;

public class InMemoryUserStoreTest extends UserStorageTest {
    public void setup() {
        /* Create HBase schema if necessary */
        userStore = new InMemoryUserStorage();
    }

    public void cleanup() { }
}
