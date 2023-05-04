import java.util.*;
public class myGraph {
    ArrayList<vertex> vertices;
    PriorityQueue<edge> edges;
    int numNodes;
    int numEdges;
    int id;

    public myGraph(){
        this.vertices = new ArrayList<vertex>();
        this.edges = new PriorityQueue<edge>(new EdgeComparator());
        this.numNodes = 0;
        this.numEdges = 0;
    }
    public myGraph(ArrayList<vertex> v, int n, int ids){
        this.vertices = v;
        this.edges = new PriorityQueue<edge>(new EdgeComparator());
        this.numNodes = n;
        this.numEdges = 0;
        this.id = ids;
    }

    // constructor
    public myGraph(ArrayList<vertex> v, PriorityQueue<edge> e, int n, int aE){
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
    public void union(myGraph f, edge  e){
        // changed this
        this.edges.add(e);
        numEdges++;
        this.vertices.addAll(f.vertices);
        this.numNodes += f.numNodes;
        this.numEdges +=  f.numEdges;
        this.edges.addAll(f.edges);
    }

}
