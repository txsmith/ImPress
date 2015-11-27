package nl.impress.app;


import nl.impress.app.data.HBaseImageStorage;

import java.io.IOException;

public class HBaseImageStorageTest extends ImageStorageTest {

    @Override
    public void setup() throws IOException {
        super.setup();
        this.imageStore = new HBaseImageStorage();
        /* Create schema in HBase if necessary */
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
