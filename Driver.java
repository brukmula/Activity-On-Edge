/*
Driver
Bruk Mulatu
COSC 2203
Assignment : Can It Be Done?
Due Date: November 18, 2020
Approved by: Robel Tadele
 */
import java.util.Scanner;

public class Driver {
    public static void main(String [] args) {

        Scanner input = new Scanner(System.in);// Scanner

        //Setting boolean value
        boolean feasibility = true;

        //Declaring matrix array
        int[][] matrix;

        int numStages = 0; // Node number
        int stageColumn = 0; // Store adjacency size
        int outgoingEdges = 0;// Store outgoing edges

        String critical; // String for critical activities

        int[] stageValue; // Declare current node or vertices
        int [] earlyActivity;
        int [] lateActivity;

        int[] edgesIn; //Store the number of edges going into a stage
        int [] topArray;
        int [] est;
        int [] lst;

        int edgesInCount = 0; //Count the number of edges in a column
        int activityCount = 0;

        int currentColumn = 0;

        System.out.println("This program creates an Activity on Edge digraph");
        System.out.println("Enter the input as specified in the programming guidelines: ");
        numStages = input.nextInt(); // Input the number of stages(vertices)

        //Array to store value of nodes
        stageValue = new int[numStages];

        //Setting the matrix size
        matrix = new int[numStages][numStages];

        //Store the number of edges going into a stage
        edgesIn = new int[numStages];

        //Input a value that makes all values negative before replacing them later
        for (int i = 0; i < numStages; i++) {
            for (int j = 0; j < numStages; j++) {
                matrix[i][j] = -1; //Random negative value to replicate infinity
            }
        }

        //For loop that runs for the number of stages
        for (int i = 0; i < numStages; i++) {
            //Store the stage values in an array
            stageValue[i] = input.nextInt();
            outgoingEdges = input.nextInt();

            //for loop that runs for the number of edges
            for (int j = 0; j < outgoingEdges; j++) {
                stageColumn = input.nextInt();
                //Store value in stage column minus 1 since array starts at 0
                matrix[i][stageColumn-1] = input.nextInt();

            }

        }

        for (currentColumn = 0; currentColumn < numStages; currentColumn++){
            edgesInCount = 0;
            int j = currentColumn;
            for (int i = 0; i < numStages; i++) {

                    if (!(matrix[i][j] == -1)) {
                        edgesInCount++;
                        activityCount++;
                }
            }
            edgesIn[j] = edgesInCount;
         }

        GraphClass graph = new GraphClass(matrix,edgesIn); // Create graph object

        //Calling topological method and storing it in an array
        topArray = graph.topOrder(stageValue);

        //Check if graph is feasible
        feasibility = graph.isFeasible();

        System.out.println();
        //Print feasibility statements
        if (feasibility){
            System.out.println("Project is Feasible");
        } else {
            System.out.println("Project is not feasible");
            System.exit(0);
        }

        //Set early stage time
        est = graph.setEarlyStage(topArray, stageValue);

        // Set late stage time and pass the total weight 
        lst = graph.setLateStage(est[est.length-1],topArray,stageValue);

        earlyActivity = graph.setEarlyActivity(activityCount, stageValue.length, est);

        lateActivity = graph.setLateActivity(lst,stageValue.length ,activityCount );

        critical = graph.criticalActivities(lateActivity, earlyActivity);


        /*--- Output ---*/

        // Print topological order
        System.out.println();
        System.out.print("Ordering: ");
        for (int i = 0; i <topArray.length; i++) {
            System.out.print(topArray[i] + " ");

        }



        System.out.println();
        System.out.println();
        System.out.println("Stage    Early         Late");

        for (int i = 0; i < lst.length; i++) {
            //System.out.println(i+1 + "         " + est[topArray[i]]);
            if(i+1 == topArray[i])
            System.out.println(i+1 + "        " + est[topArray[i]-1] + "             " + lst[topArray[i]-1]);
            else
                System.out.println(i+1 + "        " + est[i] + "            " + lst[i]);
        }

        System.out.println();
        System.out.println("Total Project Time: " + est[est.length-1]);

        System.out.println();
        System.out.println("Activity    Early    Late");

        for (int i = 0; i <earlyActivity.length ; i++) {
            System.out.println(i+1 + "            " + earlyActivity[i] + "         " + lateActivity[i]);
        }

        System.out.println();

        System.out.println("Critical Activities: " + critical);

    }
    
}
