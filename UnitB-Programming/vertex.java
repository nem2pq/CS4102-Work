import java.util.*;

public class vertex {
    int id;
    int type; // 1 is store, 2 is dist, 3 rail hub, 4 is port
    String name;
    String dist_name;
    ArrayList<edge> edges;
    public vertex(){
        this.type = 0;
        this.name = null;
        this.dist_name = null;
    }

    public vertex(int t, String n, int i){
        this.type = t;
        this.name = n;
        this.dist_name = null;
        this.id = i;
    }
    // for stores only
    public vertex(int t, String n, String d, int i){
        this.type = t;
        this.name = n;
        this.dist_name = d;
        this.id = i;
        
    }
    // this should constructor should be used when using a distribution center
    public void addEdge(edge e){
        edges.add(e);
    }
    public int getType(){
        return this.type;
    }
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }


}
