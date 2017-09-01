package utils;

import java.util.Arrays;

public class TestRecord {

    String[] data;

    /**
     * Initializing constructor
     *
     * @param data the data
     */
    public TestRecord(String[] data) {
        this.data = data;
    }

    /**
     * Get the list of data parameters passed into the test
     *
     * @return the data
     */
    public String[] getData() {
        return data;
    }

    @Override
    public String toString() {
        if (data != null) {
            return Arrays.toString(data);
        }
        return "n/a";
    }
}
