package nl.impress.app;

import nl.impress.app.data.InMemoryImageStorage;

import java.io.IOException;


public class InMemoryImageStorageTest extends ImageStorageTest {

    public InMemoryImageStorageTest() throws IOException {
    }

    @Override
    public void setup() throws IOException {
        super.setup();
        imageStore = new InMemoryImageStorage();
    }

    @Override
    public void cleanup() { }
}
