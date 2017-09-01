package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.fail;

public class DataProviderUtility {

    /**
     * Loads a single text file content to an Iterator
     *
     * @param dataFileLocation the resource path
     * @param disableShuffle   turns off shuffling
     * @return the object array of data
     */

    public static List<String> readFile(String dataFileLocation, boolean disableShuffle) {
        List<String> data = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                DataProviderUtility.class.getClassLoader().getResourceAsStream(dataFileLocation)))) {

            String line;
            while ((line = fileReader.readLine()) != null) {

                if (!line.startsWith("#") && !line.trim().isEmpty()) {
                    //data.add((line.split(",")));
                    data.add(line);
                }
            }
        } catch (Exception e) {
            fail();
        }

        //randomize the order of the data set
        if (!disableShuffle) {
            Collections.shuffle(data);
        }

        //return the list segment
        return data;
    }

    public static Iterator<Object[]> getTestDataFromCsvFile(String dataFileLocation, boolean disableShuffle) {
        List<Object[]> data = new ArrayList<>();

        int counter = 0;
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                DataProviderUtility.class.getClassLoader().getResourceAsStream(dataFileLocation)))) {

            String line = null;
            while ((line = fileReader.readLine()) != null) {

                if (!line.startsWith("#") && !line.trim().isEmpty()) {
                    //data.add((line.split(",")));
                    data.add(new Object[]{new TestRecord(line.split(","))});
                    counter++;
                }
            }
        } catch (Exception e) {
            fail();
        }

        //randomize the order of the data set
        if (!disableShuffle) {
            Collections.shuffle(data);
        }

        //return the list segment
        return data.iterator();
    }
}
