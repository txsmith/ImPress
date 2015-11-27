package nl.impress.app;

import nl.impress.app.data.HBaseUserStorage;

public class HBaseUserStorageTest extends UserStorageTest {

    @Override
    public void setup() {
        userStore = new HBaseUserStorage();
        /* Create HBase schema if necessary */
    }

    /**
     * IMPORTANT, change this to clear HBase from all data
     * Otherwise, data will persist between tests
     * and test data will pollute the application.
     */
    @Override
    public void cleanup() {
        throw new RuntimeException("Not yet implemented");
    }
}
