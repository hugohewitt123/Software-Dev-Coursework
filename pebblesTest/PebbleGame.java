import java.util.*;
import java.io.*;
/*
 * PebbleGame class where the main method is ran from
 * PebbleGame contains a nested class Player where player objects can be instantiated from
 * PebbleGame contains nested class Game where many of the methods used when threading are stored
 */
public class PebbleGame {
    //Player class used to create players and edit the weights a player currently has in their hand
    public static class Player {
        //ArrayList of integers which contains all the weights a player currently has in their hand
        private ArrayList<Integer> pebbleWeights;

        //Method to add a pebble to a players hand with the weight of the pebble as the argument
        public void addWeight(int weight) {
            this.pebbleWeights.add(weight);
        }

        /*
         *Method to check if the total weights the player has in their hand sums to 100
         * Returns true if the hand does not equal 100 and false if it does
         */
        public boolean checkTotal() {
            int total = 0;
            for (Integer pebbleWeight : this.pebbleWeights) {
                total += pebbleWeight;
            }
            return total != 100;
        }

        //Discards a random pebble from a players hand and returns the weight of the pebble discard
        public Integer discard() {
            Random rand = new Random();
            int upperbound = this.pebbleWeights.size();
            int int_random = rand.nextInt(upperbound);
            Integer randomPebble = this.pebbleWeights.get(int_random);
            this.pebbleWeights.remove(int_random);
            return randomPebble;
        }

        //Returns the integer list of the weights a player currently has in their hand
        public ArrayList<Integer> getWeights() {
            return this.pebbleWeights;
        }

        //Constructor to create a player object
        public Player() {
            this.pebbleWeights = new ArrayList<>();
        }
    }

    //main method where the game and all of the threads are ran from
    public static void main(String[] args) throws InterruptedException {
        clrscr(); // Clears the screen
        Scanner input = new Scanner(System.in);
        String displayScreen = "Welcome to the PebbleGame!! \n You will be asked to enter the number of players. \n and then for the location of 3 files in turn containing comma separated integer values for the pebble weights. \n The integer values must be strictly positive. \n The game will be simulated, and output written to files in this directory.\n \n";
        //Initializing variables
        boolean exit = false;
        int playerNum = 0;

        /*
         * Loop allowing player to enter a number of players which will error check
         * to ensure the player has entered a valid input
         */
        while (!exit) {
            System.out.println(displayScreen);
            System.out.println("Please enter the number of players:");
            String input1 = input.nextLine();

            if (input1.equals("E")) {
                exit = true;
                System.exit(0);
            } else {
                try {
                    playerNum = Integer.parseInt(input1);
                    if (playerNum > 0) {
                        exit = true;
                    } else {
                        clrscr();
                        System.out.println("non positive integer inputs not allowed");
                    }
                } catch (NumberFormatException nfe) {
                    //not int
                    clrscr();
                    System.out.println("input not an integer");
                }
            }
        }

        //Initializing variables and lists
        exit = false;
        int counter = 0;
        ArrayList<Integer> pebbleWeightsX = new ArrayList<>();
        ArrayList<Integer> pebbleWeightsY = new ArrayList<>();
        ArrayList<Integer> pebbleWeightsZ = new ArrayList<>();

        /*
         * Loop allowing players to enter 3 csv files containing pebble weights and
         * checking that all files are in a valid format(The file exists, contains only integers,
         * has 11 times the number of weights as there are players playing the game)
         * If they are a valid format the pebbles will be placed in their corresponding bags
         */
        while (!exit) {
            System.out.println("Please enter location of bag number " + counter + " to load:");
            String input1 = input.nextLine();

            if (input1.equals("E")) {
                exit = true;
                System.exit(0);
            } else {
                if (counter == 0) {
                    pebbleWeightsX = checkFile(input1);
                    if (pebbleWeightsX == null) {
                        counter--;
                        System.out.println("incorrect file");
                    } else if (pebbleWeightsX.get(0) == -1) {
                        counter--;
                        System.out.println("cannot have negative weights");
                    } else if ((playerNum * 11) > pebbleWeightsX.size()) {
                        counter--;
                        System.out.println("not enough pebbles in the file");
                    }
                } else if (counter == 1) {
                    pebbleWeightsY = checkFile(input1);
                    if (pebbleWeightsY == null) {
                        counter--;
                        System.out.println("incorrect file");
                    } else if (pebbleWeightsY.get(0) == -1) {
                        counter--;
                        System.out.println("cannot have negative weights");
                    } else if ((playerNum * 11) > pebbleWeightsY.size()) {
                        counter--;
                        System.out.println("not enough pebbles in the file");
                    }
                } else if (counter == 2) {
                    pebbleWeightsZ = checkFile(input1);
                    if (pebbleWeightsZ == null) {
                        counter--;
                        System.out.println("incorrect file");
                    } else if (pebbleWeightsZ.get(0) == -1) {
                        counter--;
                        System.out.println("cannot have negative weights");
                    } else if ((playerNum * 11) > pebbleWeightsZ.size()) {
                        counter--;
                        System.out.println("not enough pebbles in the file");
                    } else {
                        exit = true;
                    }
                }
                counter++;
            }
        }

        //Creating all the bag objects and initializing them with pebbles
        Bag bagA = new Bag("A", "white");                   //0
        Bag bagB = new Bag("B", "white");                   //1
        Bag bagC = new Bag("C", "white");                   //2
        Bag bagX = new Bag("X", "black", Objects.requireNonNull(pebbleWeightsX));   //3
        Bag bagY = new Bag("Y", "black", Objects.requireNonNull(pebbleWeightsY));   //4
        Bag bagZ = new Bag("Z", "black", Objects.requireNonNull(pebbleWeightsZ));   //5

        //Initializing lists of bags, threads and players
        ArrayList<Bag> bags = new ArrayList<Bag>(Arrays.asList(bagA, bagB, bagC, bagX, bagY, bagZ));
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();

        final Game game = new Game();//Creating a game object
        /*
         * Loop to initialize all players threads and their corresponding output files
         */
        for (int i = 0; i < playerNum; i++) {
            final int y = i;
            players.add(new Player());
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    //initialize players pebbles
                    try {
                        game.initialize(players.get(y), bags);
                        game.playGame(players.get(y), y, bags);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));
            try {
                File myObj = new File("player" + (y + 1) + "_output.txt"); // Specify the filename
                myObj.createNewFile();
                PrintWriter writer = new PrintWriter("player" + (y + 1) + "_output.txt");
                writer.print("");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //for... starting each thread
        for (Thread thread : threads) {
            thread.start();
        }
    }

    //nested class game used by the threads to play the game
    public static class Game {
        int endGame = 0; //initializing variables

        /*
         * Method to play the game
         * Starts a loop causing players to draw and discard pebbles
         * until a player wins the game
         * Takes the player, the player number and the list of all the bags as arguments
         */
        public void playGame(Player player, int y, ArrayList<Bag> bags) {
            while (player.checkTotal() && endGame == 0) {
                try {
                    Bag currentBag = getPebble(player, y, bags);
                    discardPebble(player, currentBag, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            endGame++;
            if (endGame == 1) {
                writeFile("Winner is player " + (y+1), y);
                System.out.println("winner is player " + (y+1));
            }
            Thread.currentThread().interrupt();
        }

        /*
         * Method to initialize players with 10 random pebbles from one of three random black bags
         * Takes the player to be initialized and an array list of all the bags as arguments
         */
        public void initialize(Player player, ArrayList<Bag> bags) {
            Random rand = new Random();
            int randNum = rand.nextInt(3);
            if (randNum == 0) {
                for (int i = 0; i < 10; i++) {
                    player.addWeight(getPebble(bags.get(3)));
                }
            } else if (randNum == 1) {
                for (int i = 0; i < 10; i++) {
                    player.addWeight(getPebble(bags.get(4)));
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    player.addWeight(getPebble(bags.get(5)));
                }
            }
        }

        /*
         * Method to write to a file given a message to be written and
         * the player number whose file is to be written to
         */
        public void writeFile(String message, int i) {
            try {
                FileWriter fw = new FileWriter("player" + (i + 1) + "_output.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(message);
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
         * Synchronized method to discard a pebble to a bag given the player,
         * the bag that a player has just drawn a pebble from and the player number as arguments
         * This method can throw an InterruptedException
         */
        public void discardPebble(Player player, Bag currentBag, int i) throws InterruptedException {
            synchronized (this) {
                int number = player.discard();
                currentBag.addPebble(number);
                if (endGame == 0) {
                    writeFile("player" + (i + 1) + " has discarded a " + number + " to bag " + currentBag.getName() + "\n" + "player" + (i + 1) + " hand is " + player.getWeights().toString(), i);
                    System.out.println("player" + (i + 1) + " has discarded a " + number + " to bag " + currentBag.getName() + "\n" + "player" + (i + 1) + " hand is " + player.getWeights().toString());
                }
            }
        }

        /*
         * Synchronized method to take a pebble from a bag and add it to a players hand
         * This method checks if a bag is empty and allows a player to select from another bag if the bag is empty
         * If a player finds a bag empty, that bag is also refilled
         * This method takes the player object who is having a pebble
         * taken from them, the player number, and the Array List of bags as arguments
         * This method returns the random bag a pebble is taken from
         * This method can throw an InterruptedException
         */
        public Bag getPebble(Player player, int i, ArrayList<Bag> bags) throws InterruptedException {
            synchronized (this) {
                Bag returnedBag = bags.get(0);
                boolean done = false;
                while (!done) {
                    Random rand = new Random();
                    int randNum = rand.nextInt(3);
                    int number = 0;
                    Bag drawnFrom = bags.get(3);
                    if (randNum == 0 && bags.get(3).isNotEmpty()) {
                        number = bags.get(3).takePebble();
                        player.addWeight(number);
                        returnedBag = bags.get(0);
                        drawnFrom = bags.get(3);
                        done = true;
                    } else if (randNum == 0 && !bags.get(3).isNotEmpty()) {
                        while (bags.get(0).isNotEmpty()) {
                            bags.get(3).addPebble(bags.get(0).takePebble());
                        }
                        done = false;
                    }
                    if (randNum == 1 && bags.get(4).isNotEmpty()) {
                        number = bags.get(4).takePebble();
                        drawnFrom = bags.get(4);
                        player.addWeight(number);
                        returnedBag = bags.get(1);
                        done = true;
                    } else if (randNum == 1 && !bags.get(4).isNotEmpty()) {
                        while (bags.get(1).isNotEmpty()) {
                            bags.get(4).addPebble(bags.get(1).takePebble());
                        }
                        done = false;
                    }
                    if (randNum == 2 && bags.get(5).isNotEmpty()) {
                        number = bags.get(5).takePebble();
                        drawnFrom = bags.get(5);
                        player.addWeight(number);
                        returnedBag = bags.get(2);
                        done = true;
                    } else if (randNum == 2 && !bags.get(5).isNotEmpty()) {
                        while (bags.get(2).isNotEmpty()) {
                            bags.get(5).addPebble(bags.get(2).takePebble());
                        }
                        done = false;
                    }
                    if (endGame == 0) {
                        writeFile("player" + (i + 1) + " has drawn a " + number + " from bag " + drawnFrom.getName() + "\n" + "player" + (i + 1) + " hand is " + player.getWeights().toString(), i);
                        System.out.println("player" + (i + 1) + " has drawn a " + number + " from bag " + drawnFrom.getName() + "\n" + "player" + (i + 1) + " hand is " + player.getWeights().toString());
                    }
                }
                return returnedBag;

            }
        }

        //Method to get the pebble
        public Integer getPebble(Bag bagSelected) {
            return bagSelected.takePebble();
        }
    }

    /*
     * Method to get the pebbles stored in a csv file and return them in an ArrayList
     * This method takes the filepath as a parameter
     * This method returns an ArrayList of integer weights
     */
    public static ArrayList<Integer> checkFile(String filePath) {
        ArrayList<Integer> pebbleWeights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\s*,\\s*");
                for (String value : values) {
                    if (Integer.parseInt(value) > 0) {
                        pebbleWeights.add(Integer.parseInt(value));
                    } else {
                        pebbleWeights.clear();
                        pebbleWeights.add(-1);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            //not a file
            pebbleWeights = null;
        }//io exception

        return pebbleWeights;
    }

    //Function to clear the screen when the game starts
    public static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}