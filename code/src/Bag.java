import java.util.Random;
import java.util.*;

/**
 * The bag class is used to create bags which can contain pebbles of different weights. Each bag is instantiated with a name and a colour
 */
public class Bag {
    //String which is the name of the bag
    private final String name;
    // String which is the colour of the bag
    private final String colour;
    // Array list of integers which holds all of the weights of each pebble within a bag
    private ArrayList<Integer> pebbleWeights;

    //Method to get the array list of pebbles within a bag
    public ArrayList<Integer> getPebbles(){
        return this.pebbleWeights;
    }

    //Method to empty a bag of all weights
    public void clearBag(){
        this.pebbleWeights.clear();
    }

    //Method to add a pebble to a bag with the weight of the pebble as parameter
    public void addPebble(int weight){
        this.pebbleWeights.add(weight);
    }

    //Method to check if the bag is not empty which returns true if the bag contains weights
    public boolean isNotEmpty() {
        return this.pebbleWeights.size() != 0;
    }

    //Method to take a random pebble out of a bag and returns the integer weight of the pebble
    public Integer takePebble() {
        Random rand = new Random();
        int upperbound = this.pebbleWeights.size() - 1;
        int int_random = 0;
        if(upperbound != 0) {
            int_random = rand.nextInt(upperbound);
        }
        Integer randomPebble = this.pebbleWeights.get(int_random);
        this.pebbleWeights.remove(int_random);
        return randomPebble;
    }

    //Overloaded constructor to create a bag with the name, colour and weights as an attribute
    public Bag(String name, String colour, ArrayList<Integer> pebbleWeights) {
        this.name = name;
        this.colour = colour;
        this.pebbleWeights = pebbleWeights;
    }

    //Overloaded constructor to create a bag with the name and colour of a bag as attributes
    public Bag(String name, String colour) {
        this.name = name;
        this.colour = colour;
        this.pebbleWeights = new ArrayList<>();
    }

    //Method to return the name of a bag
    public String getName(){
        return this.name;
    }
}