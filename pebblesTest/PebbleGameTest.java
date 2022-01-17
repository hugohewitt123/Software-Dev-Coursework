import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.io.*;
import java.util.Scanner;
import static org.junit.Assert.*;

//Class containing methods and attributes used to test the PebbleGame class used in the project
public class PebbleGameTest {
    PebbleGame.Player player1 = new PebbleGame.Player(); // Creating a mock object player
    PebbleGame.Game testGame = new PebbleGame.Game(); // Creating a mock object game
    PebbleGame.Player player3 = new PebbleGame.Player(); // Creating a second player mock object
    ArrayList<Integer> pebbleWeightsTest = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));
    //Creating mock object bags and adding them to an ArrayList: bagsTest
    Bag bagA = new Bag("A", "white");                   //0
    Bag bagB = new Bag("B", "white");                   //1
    Bag bagC = new Bag("C", "white");                   //2
    Bag bagX = new Bag("X", "black", pebbleWeightsTest);  //3
    Bag bagY = new Bag("Y", "black", pebbleWeightsTest);   //4
    Bag bagZ = new Bag("Z", "black", pebbleWeightsTest);   //5
    ArrayList<Bag> bagsTest = new ArrayList<Bag>(Arrays.asList(bagA, bagB, bagC, bagX, bagY, bagZ));

    //Test method to check that the Player.addWeight() method correctly adds a weight to a players hand
    @Test
    public void addWeightTest() {
        player1.addWeight(1);
        player1.addWeight(2);
        ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,2));
        assertEquals(weights, player1.getWeights());
    }

    /*
     * Test method to check that the Player.checkTotal() method gives the correct
     * boolean output depending on the weights in a players hand
     */
    @Test
    public void checkTotalTest() {
        assertTrue(player1.checkTotal());
        player1.addWeight(100);
        assertFalse(player1.checkTotal());
    }

    //Test method to check that the Player.discard() method correctly discards a pebble into a new bag
    @Test
    public void discardTest() {
        player1.addWeight(2);
        ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(2));
        assertEquals(weights, player1.getWeights());
        int discardPebble = player1.discard();
        assertEquals(2, discardPebble);
    }

    //Test method to check that the Player.addWeight() method correctly adds a given weight to a players hand
    @Test
    public void getWeightsTest() {
        player1.addWeight(1);
        player1.addWeight(2);
        player1.addWeight(3);
        ArrayList<Integer> weights = new ArrayList<Integer>(Arrays.asList(1,2,3));
        assertEquals(weights, player1.getWeights());
    }

    //Test method to check the Player constructor correctly creates a new player object
    @Test
    public void PlayerTest() {
        PebbleGame.Player player2 = new PebbleGame.Player();
        ArrayList<Integer> weights = new ArrayList<Integer>();
        assertEquals(weights, player2.getWeights());
    }

    /*
     * Test method to check that the PebbleGame.checkFile() method correctly takes in a
     * filepath parameter and then returns an ArrayList of the integers in the CSV file
     */
    @Test
    public void testCheckFile() throws Exception{
        ArrayList<Integer> pebbleWeights = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11));
        assertEquals(pebbleWeights, PebbleGame.checkFile("testFile.txt"));
    }

    // Test method to check that the Game.initialize() method correctly
    // initialises a player with the correct amount of pebbles in their hands
    @Test
    public void initializeTest() {
        testGame.initialize(player3, bagsTest);
        assertEquals(10, player3.getWeights().size());
    }

    /*
     * Test method to check that the Game.writeFile() method correctly takes in a String and
     * a player number and creates a file with the correct file name and data
     */
    @Test
    public void writeFileTest() {
        testGame.writeFile("This is a test message!", 0);
        String data = "";
        try {
            File myObj = new File(System.getProperty("user.dir")+"/player1_output.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = data + myReader.nextLine();
            }
            myReader.close();
            myObj.delete();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        assertEquals("This is a test message!", data);
    }

    // Test method to check that the Game.discardPebble correctly discards a Pebble into the correct bag
    @Test
    public void discardPebbleTest() {
        testGame.initialize(player3, bagsTest);
        int size = bagsTest.get(0).getPebbles().size();
        int size2 = player3.getWeights().size();
        try{
            testGame.discardPebble(player3,bagsTest.get(0), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int size1 = bagsTest.get(0).getPebbles().size();
        int size3 = player3.getWeights().size();
        assertEquals((size + 1), size1);
        assertEquals((size3 + 1), size2);
        try{
            File myObj = new File(System.getProperty("user.dir")+"/player2_output.txt");
            myObj.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test method to check that Game.getPebble() correctly adds a pebble to the correct players hand
    @Test
    public void getPebbleTest(){
        testGame.initialize(player1,bagsTest);
        int size = player1.getWeights().size();
        try {
            testGame.getPebble(player1, 0, bagsTest);
        } catch (Exception e){
            e.printStackTrace();
        }
        int size1 = player1.getWeights().size();
        assertEquals((size + 1), size1);
        try{
            File myObj = new File(System.getProperty("user.dir")+"/player1_output.txt");
            myObj.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Test method to check that Game.getPebble() takes the correct random pebble
     * from the bag and that the same pebble is then deleted from the bag
     */
    @Test
    public void getPebbleSingleTest(){
        int pebbleTaken = testGame.getPebble(bagsTest.get(3));
        int size = bagsTest.get(3).getPebbles().size();
        assertEquals(size, 19);
        int checkPebble = bagsTest.get(3).getPebbles().get(pebbleTaken-1);
        assertNotEquals(pebbleTaken, checkPebble);
    }
}