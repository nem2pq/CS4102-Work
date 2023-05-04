import java.util.*;
public class myGraph2 {
    ArrayList<vertex> vertices;
    ArrayList<edge> edges;
    int numNodes;
    int numEdges;
    int id;

    public myGraph2(){
        this.vertices = new ArrayList<vertex>();
        this.edges = new ArrayList<edge>();
        this.numNodes = 0;
        this.numEdges = 0;
    }
    public myGraph2(ArrayList<vertex> v, int n, int ids){
        this.vertices = v;
        this.edges = new ArrayList<edge>();
        this.numNodes = n;
        this.numEdges = 0;
        this.id = ids;
    }

    // constructor
    public myGraph2(ArrayList<vertex> v, ArrayList<edge> e, int n, int aE){
        this.vertices = v;
        this.edges = e;
        this.numNodes = n;
        this.numEdges = aE;
    }

    public void addVertex(vertex v){
        vertices.add(v);
        this.numNodes ++;
    }
    // this acts like union
    public void union(myGraph2 f, edge  e){
        // changed this
        this.edges.add(e);
        numEdges++;
        this.vertices.addAll(f.vertices);
        this.numNodes += f.numNodes;
        this.numEdges +=  f.numEdges;
        this.edges.addAll(f.edges);
    }

}
