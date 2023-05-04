
/**
 * CS4102 Spring 2022 - Unit B Programming
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
 * Your Computing ID: Nem2pq
 * Collaborators: 
 * Sources: Introduction to Algorithms, Cormen
 **************************************/
import java.util.*;

public class Supply {
    // Initial steps:
    // Make a vertex class, with a "property" that defines it as a port,
    // rail hub, Distribution Center, or store
    // think of best way to store/ weighted edges
    // complete parse function so it works

    // solving the problem
    // use of kruskals:
    // differences that we need to account for
    // - when do we need to do "property" checks?
    // - when is the best time to check for these? Within our kruskal implementation?

    // Parse- creates a graph with a list of vertices, min heap of edges,
    // and eliminates any edges that do not meet the constraints of the problem
    public myGraph parse(List<String> fileData){
        // getting first file line
        String a = fileData.get(0);
        String[] b = a.split(" ");
        // number of nodes and edges
        int nodes = Integer.parseInt(b[0]);
        int links = Integer.parseInt(b[1]);
        // list of vertices
        ArrayList<vertex> v = new ArrayList<vertex>();
        // use for min heap (see comparator in edge class)
        PriorityQueue<edge> e = new PriorityQueue<edge>(links, new EdgeComparator());
        // loop to parse nodes/ vertices
        String curr_dist = null;
        int ids = 1;
        for (int i = 1; i < nodes+1; i++){ // check these loops
            String c = fileData.get(i);
            String[] d = c.split(" ");
            String name = d[0]; // grabs node name
            String g = d[1];
            int type = 0;
            // label node with type
            if (g.equals("port")){
                type = 4;
            }
            if (g.equals("rail-hub")){
                type =3;
            }
            if (g.equals("dist-center")){
                type = 2;
                curr_dist = name;
            }
            // two different constructors for point based on if store or not
            if (g.equals("store")){
                type = 1;
                vertex v1 = new vertex(type, name, curr_dist, ids);
                v.add(v1);
                ids++;
            } else{
                vertex v1 = new vertex(type, name, ids);
                v.add(v1);
                ids++;
            }
        };
        // loop to parse edges, add to priority queue
        int actEdge = 0;
        for(int i = nodes+1; i< (nodes + links+1); i++){
            String c = fileData.get(i);
            String[] d = c.split(" ", 3);
            int l = Integer.parseInt(d[2]);
            // d[0] and d[1] are names of vertices
            String name1 = d[0];
            String name2 = d[1];
            vertex cur1 = new vertex();
            vertex cur2 = new vertex();

            // var to count number of valid edges
            for (int j = 0; j < v.size(); j++){
                if(v.get(j).getName().equals(name1) || v.get(j).getName().equals(name2)){
                    if (cur1.getType() == 0){
                        cur1 = v.get(j);
                    } else{
                        cur2 = v.get(j);
                        break;
                    }
                } 
            }
            // add check to see if edge is valid, only care about valid edges
            int t1 = cur1.type;
            int t2 = cur2.type;
            String dist1 = null;
            String dist2 = null;
            if(t1 == 1){
                dist1 = cur1.dist_name;
            }
            if(t2 == 1){
                dist2 = cur2.dist_name;
            }
            // no port to port
            //if(t1 == 4 && t2 ==4){
            //    continue;
            //}
            // no port to stores
            if((t1 ==  4  && t2 == 1) || (t1 == 1 && t2 == 4)){
                continue;
            // no rail hubs to stores
            }else if((t1 ==  3  && t2 == 1) || (t1 == 1 && t2 == 3)){
                continue;
            // two distribution centers
            }else if(t1 == 2 && t2 ==2){
                continue;
            // two stores w/ diff distribution centers
            //}else if((t1 == 1 && t2 ==1) && !dist1.equals(dist2)){
            //    continue;
            // store with wrong distribution center
            }else if((t1 == 1 && t2 == 2 && !dist1.equals(cur2.name))||(t1 == 2 && t2 == 1 && !dist2.equals(cur1.name))){
               continue;
            // then if not either of these, we can add edge to priority queue
            } else if(t1 != 0 && t2 !=  0){
                edge v1 = new edge(cur1, cur2, l); 
                actEdge++;
                //System.out.println(t1 + " " + t2);
                e.add(v1);
            }
        }

        // return graph with list of vertices and edges
        myGraph g = new myGraph(v, e, nodes, actEdge);
        return g;
    }


    // need some implementation of kruskals

    public int kruskals(myGraph g){
        // g.edges is all the edges sorted in order
        // this is our V (we need to have V -1 edges)
        int e = 0;
        int nodes = g.numNodes;

        int edgeWeight = 0;
        myGraph2 f = new myGraph2(); // to store final graph

        //use myGraph as a subset class for disjoint set
        // array of sets
        ArrayList<myGraph2> disjointsets = new ArrayList<myGraph2>(g.numNodes);
        //use myGraph as a subset class for disjoint set
        // makeset()
        int gId = 1;
        for(int i =0; i< g.numNodes; i++) {
            vertex vS = g.vertices.get(i);
            ArrayList<vertex> vS2 = new ArrayList<vertex>();
            vS2.add(vS);
            myGraph2 g1 = new myGraph2(vS2, 1, gId);
            disjointsets.add(g1);
            gId++;
        }
        // kruskals, using above
        while(e <  nodes -1){
            edge newE = g.edges.poll();
            int id1 = newE.p1.getId();
            int id2 = newE.p2.getId();
            // find() implementation, search for Ids in graphs in array
            myGraph2 f1 = new myGraph2();
            myGraph2 f2 = new myGraph2();
            // for indexes of graphs in disjointsets array
            int ind1 = 0;
            int ind2 = 0;
            for(int i = 0; i<  disjointsets.size(); i++){
                for(int j = 0; j < disjointsets.get(i).numNodes; j++){
                    if(disjointsets.get(i).vertices.get(j).getId() == id1){
                        f1 = disjointsets.get(i);
                        ind1 = i;
                    }
                    if(disjointsets.get(i).vertices.get(j).getId() == id2){
                        f2 = disjointsets.get(i);
                        ind2 = i;
                    }
                }
            }
            // check to make sure not the same graph, if not, union with edge
            if(f1.id == f2.id){
                continue;
            }
            //union()- see myGraph.java for majority of implementation
            f1.union(f2, newE);
            disjointsets.remove(ind2);
            e ++;
            if(disjointsets.size() == 1){
                f = disjointsets.get(0);
            }
        }
        // disjointsets.get(1).vertices.get(1).getId();
        for(int i = 0; i < f.edges.size(); i++){
            edgeWeight +=  f.edges.get(i).length;
        }
        return edgeWeight;

    }

    /**
     # This is the method that should set off the computation
     # of the supply chain problem.  It takes as input a list containing lines of input
     # as strings.  You should parse that input and then call a
     # subroutine that you write to compute the total edge-weight sum
     # and return that values from this method
     #
     # @return the total edge-weight sum of a tree that connects nodes as described
     # in the problem statement
     */
    public int compute(List<String> fileData) {
        myGraph g = parse(fileData);
        /**for(int i = 0; i< g.vertices.size() ; i++){
            System.out.println(g.vertices.get(i).name);
            if(g.vertices.get(i).type == 1){
                System.out.println(g.vertices.get(i).dist_name);
            }
        }*/
        int edgeWeightSum = 0;
        //while(!g.edges.isEmpty()){
        //   System.out.println(g.edges.poll().length);
        //}
        edgeWeightSum = kruskals(g);
        // your function to compute the result should be called here
        return edgeWeightSum;
    }
}
