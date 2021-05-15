/*
Graph Class
Bruk Mulatu
COSC 2203
Assignment : Can It Be Done?
Due Date: November 18, 2020
Approved by: Robel Tadele
 */

public class GraphClass {

    // Two dimensional array for matrix
    int [] [] matrix;

    BrukListStack <Integer> stacker = new BrukListStack<>(); // Stack object

    int [] edgesIn; //Store the number of edges going into a stage

    boolean feasibility;
    int temp;

    //Constructor that receives the adjacency matrix
    GraphClass(int [][] adjacencyMatrix, int [] edgesInCount){
        this.matrix = adjacencyMatrix;
        this.edgesIn = edgesInCount;

        feasibility = false;
    }

    /*--------------------------Make methods return int [][] arrays  ------------------------*/

    //Method to get top Order
    public int[] topOrder(int[] stages) {

        int topVal = 0;
        int counter = 0;
        int[] topo = new int[stages.length];

        for(int i = 0; i < edgesIn.length; i++) {

            if(edgesIn[i] == 0) {
                stacker.push(i);
            }
        }

        while(!stacker.isEmpty()) {

            topVal = stacker.pop();
            topo[counter] = stages[topVal];

            for(int j = 0; j < matrix[topVal].length; j++) {

                if(matrix[topVal][j] != -1) {

                    edgesIn[j]--;

                    if(edgesIn[j] == 0) {

                        stacker.push(j);
                    }
                }
            }
            counter++;

            if(topo.length == stages.length) {
                feasibility = true;
            }
        }


        return topo;
    }

    //Method to check if it is feasible
    public boolean isFeasible (){
        return feasibility;
    }


    //The EST is the max of the sum of the EST of pre-req stages and the cost of
    //the connecting activity.
    public int [] setEarlyStage(int [] stage, int [] topOrder){

        int [] earlyStage = new int[stage.length];

        int [] edges = new int [stage.length];

        int count = 0;
        int maxTime = 0;

        //For loop that runs from 1 to the end of the topological order
        for (int i = 0; i <stage.length; i++) {

            //Store topological order value into int loc
            int loc = topOrder[i];

            //for length of topological array
            for (int j = 0; j < stage.length; j++) {

                //If matrix location is not empty
                if (!(matrix[j][loc-1] == -1)) {
                    //Store time value in temp variable
                    edges[count] = earlyStage[j] + matrix[j][loc - 1];

                    //Increment index
                    count++;
                }

            }

            for (int j = 0; j < count; j++) {
                if (edges[j] > maxTime)
                    maxTime = edges[j];
            }
            earlyStage[loc-1] = maxTime;

            count = 0;
            maxTime =0;

        }

        return earlyStage;
    }

    //The LST of other stages is the minimum of the
    //difference between the LST of outgoing activities end stage and the cost of the activity.
    public int [] setLateStage(int totalWeight, int [] topOrder, int [] stage){

        int[] lateStage = new int[stage.length];

        //calling the reverse method on the topological order:
        int [] topological = backwards(topOrder);

        //array for holding all possible paths to the next node:
        int[] edge = new int[stage.length];

        int index = 0;
        int min = totalWeight;

        //For loop for rows:
        for(int i = 0; i < stage.length; i++) {

            //For loop for columns:
            for(int j = 0; j < stage.length; j++) {

                //if there is a path:
                if(matrix[topological[i] - 1][j] != -1) {

                    edge[index] = lateStage[i] - matrix[topological[i] - 1][j];
                    index++;
                }
            }

            //Find the weight
            for(int j= 0; j < index; j++) {
                if(edge[j] < min && edge[j] >= 0) {
                    min = edge[j];
                }
            }

            //Store late stage value into an array
            lateStage[topological[i] - 1] = min;

            //Set back to 0
            index = 0;
            min = totalWeight;
        }
        return lateStage;
    }

    // Method to get early times
    public int [] setEarlyActivity(int activityCount, int stageCount, int [] earlStageTime){

        int [] earlyActivities = new int[activityCount];
        int count =0;

        for (int i = 0; i < stageCount; i++) {
            for (int j = 0; j <stageCount; j++) {

                if (!(matrix[i][j] == -1)){
                    earlyActivities[count] = earlStageTime[i];
                    count++;
                }
            }
        }
        return earlyActivities;
    }

    //Method to get late times
    public int [] setLateActivity(int [] lateStageTime, int stageCount, int activityCount){

        int [] lateActivities = new int [activityCount];

        int count = 0;


        for (int i = 0; i <stageCount ; i++) {
            for (int j = 0; j <stageCount ; j++) {

                if (!(matrix[i][j]==-1)){
                    lateActivities[count] = lateStageTime[j] - matrix[i][j];
                    count++;
                }
            }
        }
        return lateActivities;
    }

    //Method to reverse an array
    public int [] backwards(int [] array){

        int [] backwards = array.clone();

        for (int i = 0; i <array.length/2 ; i++) {
            int temp = backwards[i];
            backwards[i] = backwards[array.length-1-i];
            backwards[array.length-1-i] = temp;
        }

        return backwards;
    }

    public String criticalActivities (int [] lateActivities, int [] earlyActivities){

        String activities = "";

        for (int i = 0; i <earlyActivities.length; i++) {
            if (lateActivities[i] - earlyActivities[i] == 0){
                //Covert the int to string and append it to the original string
                activities += Integer.toString(i+1) + " ";
            }
        }
        return activities;
    }
}
