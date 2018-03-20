/*	Lillian Arguelles
* 	CS 2101 - Final Project
* 	12-09-2015
* 	Traffic Jam Individual Player Program - Player
*/

public class Player {

    /*	******************************************************************************************************
     * 	********************************************* GLOBAL VARIABLES ***************************************
     * 	******************************************************************************************************
     */
    public String value;				// Who the player is - ie X1 or O3
    public int currentPosition;			// Where the player is currently located


    /*	******************************************************************************************************
     * 	********************************************* CONSTRUCTOR ********************************************
     * 	******************************************************************************************************
     */
    public Player() {
        String value;
        int currentPosition;
    }


    /*	******************************************************************************************************
     * 	*********************************************** METHODS **********************************************
     * 	******************************************************************************************************
     */
    // ***** Set the Value *****
    public void setVal(String x){
        this.value = x;
    }

    // ***** Get the Value *****
    public String getValue(){
        return this.value;
    }

    // ***** Set the Position *****
    public void setPos(int x){
        this.currentPosition = x;
    }

    // ***** Get the Position *****
    public int getPosition(){
        return this.currentPosition;
    }

}
