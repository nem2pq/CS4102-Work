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

public class point implements Comparable<point>{
    double x, y;
    boolean low;
    boolean high;

    public point(double a, double b){
        this.x = a;
        this.y = b;
        this.low = false;
        this.high = false;
    }
    public void setX(double x1){
        this.x = x1;
    }
    public void setY(double y1){
        this.y = y1;
    }
    public void sLow(){
        this.low = true;
    }
    public void sHigh(){
        this.high = true;
    }
    public void nLow(){
        this.low = false;
    }
    public void nHigh(){
        this.high = false;
    }
    public boolean getLow(){
        return this.low;
    }
    public boolean getHigh(){
        return this.high;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    @Override
    public int compareTo(point otherPoint) {
        return Double.compare(getX(), otherPoint.getX());
    }

}

