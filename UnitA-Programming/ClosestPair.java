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
     public List<point> parses(List<String> fileData){
        int sizeList = fileData.size();
        List<point> pointList = new ArrayList<>();
        for (int i = 0; i < fileData.size(); i++){
            String a = fileData.get(i);
            String[] b = a.split(" ");
            String c = b[0];
            String d = b[1];
            double x = Double.parseDouble(c);
            double y = Double.parseDouble(d);
            point e = new point(x, y);
            pointList.add(e);
            //System.out.println(Double.toString(pointList[i][0]) + "," + Double.toString(pointList[i][1]));
        };
        return pointList;
    }
    // helper method to find the distance between two points
    public double distPoints(List<point> point, int a, int b){
        if((point.get(a).getX() == point.get(b).getX()) && (point.get(a).getY() == point.get(b).getY())){
            return Double.MAX_VALUE;
        }
        if(point.get(a).getX() == point.get(b).getX()){
            return Math.sqrt(((point.get(a).getY() - point.get(b).getY())*(point.get(a).getY() - point.get(b).getY())));
        }
        if(point.get(a).getY() == point.get(b).getY()){
            return Math.sqrt(((point.get(a).getX() - point.get(b).getX())*(point.get(a).getX() - point.get(b).getX())));
        }
        return Math.sqrt(((point.get(a).getX() - point.get(b).getX())*(point.get(a).getX() - point.get(b).getX())) 
        + ((point.get(a).getY() - point.get(b).getY())*(point.get(a).getY() - point.get(b).getY())));
    }

    // helper method to brute force min distances- runs in n^2 time 
    // only use on n < 3 probably
    public double[] bForce(List<point> points, int n){
        double[] mins = new double[2];
        mins[1] = Double.MAX_VALUE;
        mins[0] = Double.MAX_VALUE;
        if(n <= 1){
            return mins;
        }
        if(n == 2){
             mins[0] = distPoints(points, (n-1), (n-2));
             return mins;
        }
        for (int i = 0; i< n-1; i++){
            for(int j = i+1; j <n; j++){
                if(distPoints(points, i, j) <= mins[0] ){
                    mins[1] = mins[0];
                    mins[0] = distPoints(points, i, j);
                } else if(distPoints(points, i, j) <= mins[1]){
                    mins[1] = distPoints(points, i, j);
                };
            }
        }
        return mins;
    }


    public double[] twoClosestPairs(List<point> points, int n){
        // check base case
        if (n <=3){
            return bForce(points, n);
        }
        // find median
        //System.out.println(Integer.toString(n));
        int midPoint = n/2;
        //System.out.println(Integer.toString(midPoint));
        double med = points.get(midPoint).getX();
        double medy = points.get(midPoint).getY();
        // make two new arrays
        List<point> pListLow = new ArrayList<>();
        List<point> pListHigh = new ArrayList<>();
        for(int i = 0; i <= midPoint; i++){
            pListLow.add(points.get(i));
        }
        /*
        System.out.println("low");
        for (int i = 0; i <= midPoint; i++){
            System.out.println(Double.toString(pListLow.get(i).getX()) + "," + Double.toString(pListLow.get(i).getY()));
        }
        */
        for(int i = midPoint+1; i < n; i++){
            pListHigh.add(points.get(i));
        }
        /*
         System.out.println("high");
        for (int i = 0; i < (n-midPoint-1); i++){
            System.out.println(Double.toString(pListHigh.get(i).getX()) + "," + Double.toString(pListHigh.get(i).getY()));
        }
        */
        // find smallest distance each side
        // this is recursive? how is this done
        double[] leftMins = twoClosestPairs(pListLow, (midPoint+1));
        //System.out.println(Double.toString(leftMins[0]));
        //System.out.println(Double.toString(leftMins[1]));
        double[] rightMins = twoClosestPairs(pListHigh, (n-midPoint-1));
        //System.out.println(Double.toString(rightMins[0]));
        //System.out.println(Double.toString(rightMins[1]));


        // 2 mins distances of left and right
        double runwayWidth = Double.MAX_VALUE;
        if(leftMins[0] < rightMins[0]){
            runwayWidth = leftMins[0];
        }else if(leftMins[0] >= rightMins[0]){
            runwayWidth = rightMins[0];
        };
        //System.out.println(Double.toString(runwayWidth));

        double runwayStart = med - runwayWidth;
        double runwayEnd = med + runwayWidth;

        List<point> runwayPoints = new ArrayList<>();
        int index = 0;
        int midInd = 0;
        int flip = 0;
        for (int i = 0; i < n ; i++){
            if (points.get(i).getX()> runwayStart && points.get(i).getX() < runwayEnd && flip == 0){
                points.get(i).sLow();
                points.get(i).nHigh();
                runwayPoints.add(points.get(i));
                index++;
            };
            if (points.get(i).getX()> runwayStart && points.get(i).getX() < runwayEnd && flip == 1){
                points.get(i).nLow();
                points.get(i).sHigh();
                runwayPoints.add(points.get(i));
                index++;
            };
            if(i == midPoint){
                    midInd = index- 1;
                    flip = 1;
                }
        }
        //int midPoint2 = index;
        Comparator<point> pointY = (o1, o2) -> Double.compare(o2.getY(), o1.getY());
        /*
        for (int i = midPoint; i < n ; i++){
            if (points.get(i).getX() >= runwayStart && points.get(i).getX() <= runwayEnd){
                runwayPoints.add(points.get(i));
                index++;
            };
        }
        */
        //System.out.println("Runway array");
        Collections.sort(runwayPoints, pointY);
        /*
        for (int i = 0; i < index; i++){
            System.out.println(Double.toString(runwayPoints.get(i).getX()) + "," + Double.toString(runwayPoints.get(i).getY()));
        }
        System.out.println("Runway array end");
        */

        // make runway: distance the min of left and right from median
        // construct list of points in the runway

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
                // any points on the same side of the runway we don't care to check their distance again
                if((runwayPoints.get(i).getLow()) && (runwayPoints.get(j).getLow())){
                   break;
                }
                if((runwayPoints.get(i).getHigh()) && (runwayPoints.get(j).getHigh())){
                   break;
                }
                // if condiiton to not compare median to lesser x values
                if(distPoints(runwayPoints, i, j) <= runwayMins[0]){
                    runwayMins[1] = runwayMins[0];
                    runwayMins[0] = distPoints(runwayPoints, i, j);
                    break;
                }
                if(distPoints(runwayPoints, i, j) < runwayMins[1]){
                    runwayMins[1] = distPoints(runwayPoints, i, j);
                    break;
                }
            }
        }
        double[] finalMins = new double[2];
        finalMins[0] = leftMins[0];
        finalMins[1] = leftMins[1];
        // comparisions for shortest distance
        if(finalMins[0] >= rightMins[0]){
            finalMins[1] = finalMins[0];
            finalMins[0] = rightMins[0];
        }else if(finalMins[1] >= rightMins[0]){
            finalMins[1] = rightMins[0];
        };
        if(finalMins[0] >= runwayMins[0]){
            finalMins[1] = finalMins[0];
            finalMins[0] = runwayMins[0];
        }else if(finalMins[1] >= runwayMins[0]){
            finalMins[1] = runwayMins[0];
        };

        if(finalMins[1] > rightMins[1]){
                finalMins[1] = rightMins[1];
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
        List<point> nfileData = parses(fileData);
        //Comparator<point> byX = Comparator.comparing(point::getX);
        Collections.sort(nfileData);
        // Initialization sort by x coordinate (from source 1)
        //for (int i = 0; i < size; i++){
        //    System.out.println(Double.toString(nfileData.get(i).getX()) + "," + Double.toString(nfileData.get(i).getY()));
        //}
        // Call Closest Pairs algorithm
        double[] minimums = twoClosestPairs(nfileData, size);
        double[] closest = new double[2];
        closest[0] = 0.0; // closest pair distance
        closest[1] = 0.1; // second closest pair distance
        return minimums;
    }

}

