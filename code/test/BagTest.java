import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

//Class containing methods and attributes used to test the bag class used in the main project
public class BagTest {
    ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11)); //Array list of weights
    Bag testBag = new Bag("testBag", "black", weights); //Mock object bag initialised with the weights in the above array

    //Test method to check that the Bag.getPebbles() method works correctly
    @Test
    public void getPebblesTest() {
        assertEquals(weights, testBag.getPebbles());
    }

    //Test method to check that the Bag.clearBag() method correctly empties the bag
    @Test
    public void clearBagTest() {
        ArrayList<Integer> weights1 = new ArrayList<Integer>();
        testBag.clearBag();
        assertEquals(weights1, testBag.getPebbles());
    }

    //Test method to check that the Bag.addPebble() correctly adds a given pebble weight to the correct bag
    @Test
    public void addPebbleTest() {
        ArrayList<Integer> weights2 = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,1,2));
        testBag.addPebble(1);
        testBag.addPebble(2);
        assertEquals(weights2, testBag.getPebbles());
    }

    /*
     * Test method to check that the Bag.isNotEmpty() method correctly reutrns
     * the boolean value dependant on whether the bag is empty
     */
    @Test
    public void isNotEmptyTest() {
        assertTrue(testBag.isNotEmpty());
        testBag.clearBag();
        assertFalse(testBag.isNotEmpty());
    }

    //Test method to check the Bag.takePebble() correctly takes a pebble out of the correct bag
    @Test
    public void takePebbleTest() {
        testBag.clearBag();
        testBag.addPebble(2);
        int rnd = testBag.takePebble();
        assertEquals(2,rnd);
    }

    //Test method to check the Bag.getName() method returns the correct name of a bag
    @Test
    public void getNameTest() {
        assertEquals("testBag", testBag.getName());
    }
}