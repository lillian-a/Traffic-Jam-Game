/*	Lillian Arguelles
* 	CS 2101 - Final Project
* 	12-09-2015
* 	Traffic Jam Play Demo Program - PlayTraffic
*/
import java.util.Scanner;


public class PlayTraffic {
    /*	******************************************************************************************************
     * 	********************************************* GLOBAL VARIABLES ***************************************
     * 	******************************************************************************************************
     */
    static Scanner in = new Scanner(System.in);
    static Player[] order;	// player array
    static int x;			// number of left side players - inputed by user
    static int o;			// number of right side players - inputed by user
    static int totalNumMoves;

    /*	******************************************************************************************************
     * 	************************************************ MAIN ************************************************
     * 	******************************************************************************************************
     */
    public static void main(String[] args){
        // calls play game program to start traffic game
        playGame();
    }

    /*	******************************************************************************************************
     * 	************************************************ PLAY ************************************************
     * 	******************************************************************************************************
     */
    public static void playGame(){
        System.out.println("Welcome to the Traffic Jam Game!");
        System.out.println("*************************************");
        System.out.println("");

        // get the numbers from the user
        getNum();

        // size of the full game
        int SIZE = x+o+1;
        // based on formula:
        // N(M + 1) + M(N + 1) - MN = MN + M + N
        //
        totalNumMoves = (x*o) + x + o;

        // instantiate the player array
        order = new Player[SIZE];

        // instantiate the player objects
        for(int i = 0; i < SIZE; i++){
            order[i] = new Player();
        }

        // create the team object - pass in the player array, the size of the left side and the size of the right side
        TrafficTeam team = new TrafficTeam(order, x, o);

        // have the team object create the sides
        team.createSides();

        // boolean variable to know when game is done - starts as false
        boolean done = false;
        int count = 0;
        // while loop - keeps looping while the game is not done
        while(!done){

            // LEFT SIDE TURN = an integer is returned; 0 if no move is made
            // a positive number based on number of moves made
            // the number return does not matter unless it is 0
            int a = team.leftTurn();

            // RIGHT SIDE TURN = an integer is returned; 0 if no move is made
            int b = team.rightTurn();

            // compares a and b - if both are 0 than neither side can move
            // assumes that if neither side moves in a row then the game is solved
            if(a == 0 && b == 0){
                count++;
            }
            if(count == 2){
                done = true;
            }
        }

        // when done prints that it is done
        System.out.println("");
        System.out.println("");
        System.out.println("*************************************");
        System.out.println("Traffic Jam Solved.");
        System.out.println("*************************************");
        System.out.println("Total Number of Moves: " + totalNumMoves);
        System.out.println("*************************************");
        System.out.println("");
        String s;
        System.out.print("Enter Y to play again, or Enter any other letter to exit: ");
        s = in.next();
        s = s.toUpperCase();
        if(s.charAt(0) == 'Y'){
            System.out.println("");
            System.out.println("*************************************");
            playGame();
        }
        else{
            System.out.println("Thanks for playing! Good Bye!");
            System.out.println("*************************************");
            System.exit(0);
        }
    }

    /*	******************************************************************************************************
     * 	************************************************ NUMS ************************************************
     * 	******************************************************************************************************
     */
    // get the numbers from the user for sizes of the left and right sides
    public static void getNum(){
        // left side
        System.out.print("How many X's on the left side: ");
        x = in.nextInt();
        // right side
        System.out.print("How many O's on the right side: ");
        o = in.nextInt();
        //in.nextLine();
    }



}
