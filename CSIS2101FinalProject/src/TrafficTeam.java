/*	Lillian Arguelles
* 	CS 2101 - Final Project
* 	12-09-2015
* 	Traffic Jam Team Program - TrafficTeam
*/

import java.util.Scanner;


public class TrafficTeam {

    /*	******************************************************************************************************
     * 	********************************************* GLOBAL VARIABLES ***************************************
     * 	******************************************************************************************************
     */
    static Player[] order;                // the object array of all the individuals
    static int[] LEFT;                    // array to keep track of left side individuals
    static int[] RIGHT;                    // array to keep track of left side individuals
    static int blankLocation;            // location of the blank - used for troubleshooting
    public static int LEFT_SIZE;        // size of the left side
    public static int RIGHT_SIZE;        // size of the right side
    public static int GAME_SIZE;        // size of the full game (left, right and blank)
    static Scanner in = new Scanner(System.in);
    public static int counter;

	/*	******************************************************************************************************
     * 	********************************************* CONSTRUCTOR ********************************************
	 * 	******************************************************************************************************
	 * 		Pass in:
	 * 			the object array of the individuals
	 * 			x - integer of the size of the left side
	 * 			y - integer of the size of the right side
	 * 		Copies the object array that was passed in into an object array
	 * 		Instantiates the variables:
	 * 			LEFT_SIZE - to hold the size of the left side
	 * 			RIGHT_SIZE - to hold the size of the right side
	 * 			GAME_SIZE - to hold the size of the entire 'game'
	 */

    public TrafficTeam(Player[] order2, int x, int y) {

        order = order2;
        LEFT_SIZE = x;
        RIGHT_SIZE = y;
        GAME_SIZE = x + y + 1;
        counter = 0;
    }


	/*	******************************************************************************************************
	 * 	*********************************************** PRINT ************************************************
	 * 	******************************************************************************************************
	 * 		Prints order of the object array using printf - only prints the values
	 */

    public static void printOrder() {
        // print 1st line of move - numbers
        String moveNumString = "Move " + counter + " : ";
        System.out.printf("%-15s", moveNumString);
        for (int i = 0; i < order.length; i++) {
            // System.out.printf("%-1s  %-4s: %-4s", "*", i, order[i].getValue());
            System.out.printf("%-5s", i);
        }
        System.out.printf("%n");
        // print 2nd line of move - value
        System.out.printf("%-15s", " ");
        for (int i = 0; i < order.length; i++) {
            // System.out.printf("%-1s  %-4s: %-4s", "*", i, order[i].getValue());
            System.out.printf("%-5s", order[i].getValue());
        }
        System.out.printf("%n");
    }

	/*	******************************************************************************************************
	 * 	***************************************** CREATE TEAM MEMBERS ****************************************
	 * 	******************************************************************************************************
	 */

    // Create the sides, blank and then print the initial order
    public static void createSides() {
        createLeft();
        createRight();
        createBlank();
        System.out.println("");
        printOrder();
    }

    // ***** Create Blank Object *****
    // * Blank represented by a '.' *
    public static void createBlank() {
        order[LEFT_SIZE].setVal(".");
        order[LEFT_SIZE].setPos(LEFT_SIZE);
        blankLocation = LEFT_SIZE;
    }

    // ***** Create Left Side *****
    public static void createLeft() {
        // create the left side array and give the left side objects their values and positions
        LEFT = new int[LEFT_SIZE];
        for (int i = 0, j = LEFT_SIZE; i < LEFT_SIZE; i++, j--) {
            String z = Integer.toString(j);
            String val = ("X" + z);
            order[i].setVal(val);
            order[i].setPos(i);
            LEFT[i] = i;
        }
    }

    // ***** Create Right Side *****
    public static void createRight() {
        // create the right side array
        RIGHT = new int[RIGHT_SIZE];
        // give the right side objects their values and positions
        for (int i = 0; i < RIGHT_SIZE; i++) {
            String val = "O" + (i + 1);
            order[LEFT_SIZE + 1 + i].setVal(val);
            order[LEFT_SIZE + 1 + i].setPos(LEFT_SIZE + 1 + i);
            RIGHT[i] = LEFT_SIZE + 1 + i;
        }
    }


	/*	******************************************************************************************************
	 * 	******************************************** TAKE A TURN *********************************************
	 * 	******************************************************************************************************
	 */

    // LEFT TURN - Returns an integer if a move has been made
    public int leftTurn() {
        int x;
        int count = 0;
        // loop through the left side array
        for (int i = LEFT.length; i > 0; i--) {
            x = LEFT[i - 1];
            count = 0;
            // check if can jump
            if (canJumpRight(x)) {
                jumpRight(x, i - 1);
                count++;
            }
            // check if can move
            if (canMoveRight(x)) {
                moveRight(x, i - 1);
                count++;
            }
        }
        return count;
    }

    // RIGHT TURN - Returns an integer if a move has been made
    public int rightTurn() {
        int x;
        int cmove = 0;
        int cjump = 0;
        int count = 0;
        // loop through the right side array
        for (int i = 0; i < RIGHT.length; i++) {
            cjump = 0;
            x = RIGHT[i];
            // check if can jump
            if (cjump == 0 && cmove == 0 && canJumpLeft(x)) {
                jumpLeft(x, i);
                count++;
                cjump++;
            }
            // check if can move
            if (cjump == 0 && cmove == 0 && canMoveLeft(x)) {
                moveLeft(x, i);
                count++;
                cmove++;
            }
        }
        return count;
    }


	/*	******************************************************************************************************
	 * 	********************************************** JUMPING ***********************************************
	 *  ******************************************************************************************************
	 */

    // check if right can jump to the left
    public static boolean canJumpLeft(int n) {
        // is the spot 2 to the left greater than or equal to 0
        if (n - 2 >= 0) {
            // is the spot 2 to the left the blank object
            if (order[n - 2].getValue() == ".") {
                // is the spot 3 to the right less that the game size
                if (n - 3 < 0) {
                    // get the value (ie X1) of the spot 2 to the left
                    String s = order[n - 2].getValue();
                    // does that value start with an O - meaning is it an object from the right side
                    if (s.charAt(0) == 'O') {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    // get the value (ie X1) of the spot 3 to the left
                    String s = order[n - 3].getValue();
                    // does that value start with an X - meaning is it an object from the left side
                    if (s.charAt(0) == 'X') {
                        // get the value (ie X1) of the spot 1 to the left
                        String t = order[n - 1].getValue();
                        // does that value start with an X - meaning is it an object from the left side
                        if (t.charAt(0) == 'X') {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        // are you across the middle of the game board
                        if (n - 2 <= (Math.round((GAME_SIZE) / 2))) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    // check if left can jump to the right
    public static boolean canJumpRight(int n) {
        // is the spot 2 to the right less that the game size
        if (n + 2 < GAME_SIZE) {
            // check if spot 2 to the right is blank
            if (order[n + 2].getValue() == ".") {
                // get the value (ie X1) of the spot 1 to the right
                String s = order[n + 1].getValue();
                // does that value start with an O - meaning is it an object from the right side
                if (s.charAt(0) == 'O') {
                    // get the value (ie X1) of the spot 1 to the right
                    String t = order[n + 1].getValue();
                    // does that value start with an O - meaning is it an object from the right side
                    if (t.charAt(0) == 'O') {
                        return true;
                    } else {
                        return false;
                    }
                }
                // if not
                else {
                    // are you across the middle of the game board
                    if (n >= (Math.round((GAME_SIZE) / 2))) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    // right side jump to the left
    public static void jumpLeft(int n, int m) {
        // adjust the right array position
        RIGHT[m] = n - 2;
        // swap the 2 elements n and n-2
        swapPlaces(n, (n - 2));
    }

    // left side jump to the right
    public static void jumpRight(int n, int m) {
        // adjust the left array position
        LEFT[m] = n + 2;
        // swap the 2 elements n and n+2
        swapPlaces(n, (n + 2));
    }

	/*	******************************************************************************************************
	 * 	*********************************************** MOVING ***********************************************
	 *  ******************************************************************************************************
	 */

    // check if right can move to the left
    public boolean canMoveLeft(int n) {
        // is the spot 2 to the left greater than or equal to -1
        // -1 and not 0 as it normally would be because this catches the even side game board sizes
        if (n - 2 >= -1) {
            // is the spot at the left the blank object
            if (order[n - 1].getValue() == ".") {
                // is the spot 2 to the left less than 0
                if (n - 2 < 0) {
                    return true;
                }
                // if not
                else {
                    // get the value (ie X1) of the spot 2 to the right
                    String s = order[n - 2].getValue();
                    // does that value start with an X - meaning is it an object from the left side
                    if (s.charAt(0) == 'X') {
                        return true;
                    }
                    // are you across the middle of the game board
                    if (n <= (Math.round((GAME_SIZE) / 2))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // check if left can move to the right
    public boolean canMoveRight(int n) {
        // is the spot 2 to the right less than or equal to the game size
        if (n + 2 <= GAME_SIZE) {
            // is the spot at the right the blank object
            if (order[n + 1].getValue() == ".") {
                // is the spot 2 to the right greater than or equal to the game size
                if (n + 2 >= GAME_SIZE) {
                    return true;
                }
                // if not
                else {
                    // get the value (ie X1) of the spot 2 to the right
                    String s = order[n + 2].getValue();
                    //System.out.println(" N: " + n + " N+2: " + order[n + 2].getValue());
                    // does that value start with an O - meaning is it an object from the right side
                    if (s.charAt(0) == 'O') {
                        return true;
                    }
                    // are you across the middle of the game board
                    if (n >= (Math.round((GAME_SIZE) / 2))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // left side move to the right
    public void moveLeft(int n, int m) {
        // adjust the right side array to new position
        RIGHT[m] = n - 1;
        // adjust the blank location to new position
        blankLocation = n;
        // swap the 2 elements n and n-1
        swapPlaces(n, (n - 1));
    }

    // right side move to the left
    public static void moveRight(int n, int m) {
        // adjust the right side array to new position
        LEFT[m] = n + 1;
        // adjust the blank location to new position
        blankLocation = n;
        // swap the 2 elements n and n+1
        swapPlaces(n, (n + 1));
    }

	/*	************************************************************************************************
	 * 	********************************************* SWAP *********************************************
	 *  ************************************************************************************************
	 */

    public static void swapPlaces(int a, int b) {
        in.nextLine();
        counter++;
        // System.out.println("Move "+counter+":");
        // create a temporary object
        Player temp = new Player();
        // give the temporary object the variables of a object
        temp.setVal(order[a].getValue());
        temp.setPos(order[a].getPosition());
        // give the a object the variables of b object
        order[a].setVal(order[b].getValue());
        order[a].setPos(order[b].getPosition());
        // give the b object the variables of the temporary object
        order[b].setVal(temp.getValue());
        order[b].setPos(temp.getPosition());
        printOrder();
    }

}
