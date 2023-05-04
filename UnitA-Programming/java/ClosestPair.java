/**
 * CS4102 Spring 2022 - Unit A Programming 
 *********************************************
 * Collaboration Policy: You are encouraged to collaborate with up to 3 other
 * students, but all work submitted must be your own independently written
 * solution. List the computing ids of all of your collaborators in the
 * comments at the top of each submitted file. Do not share written notes,
 * documents (including Google docs, Overleaf docs, discussion notes, PDFs), or
 * code. Do not seek published or online solutions, including pseudocode, for
 * this assignment. If you use any published or online resources (which may not
 * include solutions) when completing this assignment, be sure to cite them. Do
 * not submit a solution that you are unable to explain orally to a member of
 * the course staff. Any solutions that share similar text/code will be
 * considered in breach of this policy. Please refer to the syllabus for a
 * complete description of the collaboration policy.
 *********************************
 * Your Computing ID: nem2pq
 * Collaborators: 
 * Sources: Introduction to Algorithms, Cormen
 * 1. https://www.codegrepper.com/code-examples/java/sort+2d+array+by+column+java
 **************************************/
import java.util.List;
import java.util.*;
public class ClosestPair {

    /**
     * This is the method that should set off the computation
     * of closest pair.  It takes as input a list containing lines of input
     * as strings.  You should parse that input and then call a
     * subroutine that you write to compute the closest pair distances
     * and return those values from this method.
     *
     * @return the distances between the closest pair and second closest pair 
     * with closest at position 0 and second at position 1 
     */
     public double[][] parses(List<String> fileData){
        int sizeList = fileData.size();
        double[][] pointList = new double[sizeList][2];
        for (int i = 0; i < fileData.size(); i++){
            String a = fileData.get(i);
            String[] b = a.split(" ");
            String c = b[0];
            String d = b[1];
            double x = Double.parseDouble(c);
            double y = Double.parseDouble(d);
            pointList[i][0] = x;
            pointList[i][1] = y;
            //System.out.println(Double.toString(pointList[i][0]) + "," + Double.toString(pointList[i][1]));
        };
        return pointList;
    }
    // helper method to find the distance between two points
    public double distPoints( double[][] points, int a, int b){
        return Math.sqrt(((points[a][0]- points[b][0])*(points[a][0]- points[b][0])) 
        + ((points[a][1]- points[b][1])*(points[a][1]- points[b][1])));
    }

    // helper method to brute force min distances- runs in n^2 time 
    // only use on n < 3 probably
    public double[] bForce(double[][] points, int n){
        double[] mins = new double[2];
        mins[0] = Double.MAX_VALUE;
        mins[1] = Double.MAX_VALUE;
        for (int i = 0; i< n-1; i++){
            for(int j = i+1; j <n; j++){
                if(distPoints(points, i, j) <= mins[0] ){
                    mins[1] = mins[0];
                    mins[0] = distPoints(points, i, j);
                    break;
                };
                if(distPoints(points, i, j) < mins[1]){
                    mins[1] = distPoints(points, i, j);
                };
            }
        }
        return mins;
    }


    // points[n][0] is x coord, points[n][1] is y coord
    public double[] twoClosestPairs(double[][] points, int n){
        // check base case
        if (n <=3){
            return bForce(points, n);
        }
        // find median
        int midPoint = n/2;
        double med = points[midPoint][0];
        // make two new arrays
        double[][] pListLow = new double[midPoint][2];
        double[][] pListHigh = new double[(n-midPoint)][2];
        for(int i = 0; i < midPoint; i++){
            pListLow[i][0] = points[i][0];
            pListLow[i][1] = points[i][1];
        }
        for(int i = midPoint; i < n; i++){
            pListHigh[(i-midPoint)][0] = points[i][0];
            pListHigh[(i-midPoint)][1] = points[i][1];
        }
        // find smallest distance each side
        // this is recursive? how is this done
        double[] leftMins = twoClosestPairs(pListLow, midPoint);
        double[] rightMins = twoClosestPairs(pListHigh, (n-midPoint));
        // I believe we also have to sort by y coords at 
        // somepoint in here


        // 2 mins distances of left and right
        double runwayWidth = Double.MAX_VALUE;
        if(leftMins[0] < rightMins[0]){
            runwayWidth = leftMins[0];
        }else{
            runwayWidth = rightMins[0];
        };
        double runwayStart = med - runwayWidth;
        double runwayEnd = med + runwayWidth;

        double[][] runwayPoints = new double[n][2];
        int index = 0;
        for (int i = midPoint; i >= 0 ; i--){
            if (points[i][0] >= runwayStart && points[i][0] <= runwayEnd){
                runwayPoints[index][0] = points[i][0];
                runwayPoints[index][1] = points[i][1];
                index++;
            };
            if (points[i][0] < runwayStart || points[i][0] > runwayEnd){
                break;
            };
            }
        for (int i = midPoint+1; i < n ; i++){
            if (points[i][0] >= runwayStart && points[i][0] <= runwayEnd){
                runwayPoints[index][0] = points[i][0];
                runwayPoints[index][1] = points[i][1];
                index++;
            };
            if (points[i][0] < runwayStart || points[i][0] > runwayEnd){
                break;
            };
        }
        double[][] runwayPoints1 = new double[index][2];
        for(int i = 0; i < index; i++){
            runwayPoints1[i][0] = runwayPoints[i][0];
            runwayPoints1[i][1] = runwayPoints[i][1];
        }
        // make runway: distance the min of left and right from median
        // construct list of points in the runway
        // sorting by y because idk when else to do it
        // this is fucking things up, idk why it is
        // Arrays.sort(runwayPoints1, Comparator.comparingDouble(o -> o[1]));

        // save closest two pairs distance
        double[] runwayMins = new double[2];
        runwayMins[0] = Double.MAX_VALUE;
        runwayMins[1] = Double.MAX_VALUE;
        // Merge sorted list of points by 𝑦-coordinate and construct list of points in the runway
        // (sorted by 𝑦-coordinate), watch lecture on this?
        // some merged version from recursion
        for (int i = 0; i < index; i++){
            // compare each point to 15 points above it
            for(int j = i+1; j< i+16 && j < index; j++){
                if(distPoints(runwayPoints1, i, j) < runwayMins[0]){
                    runwayMins[1] = runwayMins[0];
                    runwayMins[0] = distPoints(runwayPoints1, i, j);
                    break;
                }
                if(distPoints(runwayPoints1, i, j) < runwayMins[1]){
                    runwayMins[1] = distPoints(runwayPoints1, i, j);
                }
            }
        }
        double[] finalMins = new double[2];
        finalMins[0] = leftMins[0];
        finalMins[1] = leftMins[1];
        System.out.println(Double.toString(finalMins[0]));
        System.out.println(Double.toString(finalMins[1]));
        System.out.println(Double.toString(rightMins[0]));
        System.out.println(Double.toString(rightMins[1]));
        System.out.println(Double.toString(runwayMins[0]));
        System.out.println(Double.toString(runwayMins[1]));

        if(finalMins[0] >= rightMins[0]){
            finalMins[1] = finalMins[0];
            finalMins[0] = rightMins[0];
            };
        if(finalMins[1] > rightMins[1]){
                finalMins[1] = rightMins[1];
        };
        if(finalMins[0] >= runwayMins[0]){
            finalMins[1] = finalMins[0];
            finalMins[0] = runwayMins[0];
            };
        if(finalMins[1] > runwayMins[1]){
                finalMins[1] = runwayMins[1];
        };
        // save closest two pairs distance
        return finalMins;
        // output smallest two distances of six distances

    }

    public double[] compute(List<String> fileData) {
        int size = fileData.size();
        double[][] nfileData = parses(fileData);
        // Initialization sort by x coordinate (from source 1)
        Arrays.sort(nfileData, Comparator.comparingDouble(o -> o[0]));
        //for (int i = 0; i < size; i++){
        //    System.out.println(Double.toString(nfileData[i][0]) + "," + Double.toString(nfileData[i][1]));
        // }
        // Call Closest Pairs algorithm
        double[] minimums = twoClosestPairs(nfileData, size);
        double[] closest = new double[2];
        closest[0] = 0.0; // closest pair distance
        closest[1] = 0.1; // second closest pair distance
        return minimums;
    }

}

