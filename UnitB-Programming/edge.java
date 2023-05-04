
import java.util.*;

public class edge{
    vertex p1;
    vertex p2;
    int length;
    boolean valid;
    // constructor, goes from v1  to v2
    public edge(vertex v1, vertex v2, int l){
        this.p1 = v1;
        this.p2 = v2; 
        this.length = l;
    }
    // function to return if an edge is a valid edge
    public boolean isValid(){
        return true;
    }
}
// for priority queue (min to max)
class EdgeComparator implements Comparator<edge>{
    public int compare(edge e1, edge e2){
        return e1.length - e2.length;
    }
}