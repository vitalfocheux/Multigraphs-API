package m1graphs2024;

import java.util.*;

public class Node implements Comparable<Node>{

    private int id;
    private String name;
    private Graph graph;

    public Node(int id, Graph graph){
        this(id, "", graph);
    }

    public Node(int id, String name, Graph graph){
        this.id = id;
        this.name = name;
        this.graph = graph;
    }

    public boolean equals(Object obj) {
        if(obj instanceof Node){
            Node n = (Node) obj;
            return n.id == this.id;
        }
        return false;
    }

    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Node o) {
        return this.id - o.id;
    }

    @Override
    public String toString() {
        return ""+getId();
    }

    public int getId() {
        return id;
    }

    public Graph getGraph() {
        return graph;
    }

    public String getName() {
        return name;
    }

    public List<Node> getSuccessors() {
        return graph.getSuccessors(this);
    }

    public List<Node> getSuccessorsMulti(){
        return graph.getSuccessorsMulti(this);
    }

    public boolean adjacent(Node n){
        return graph.adjacent(this, n);
    }

    public boolean ajdacent(int id){
        return graph.adjacent(this, new Node(id, graph));
    }


    public int inDegree(){
        return graph.inDegree(this);
    }

    public int outDegree(){
        return graph.outDegree(this);
    }

    public int degree(){
        return graph.degree(this);
    }

    public List<Edge> getOutEdges(){
        return graph.getOutEdges(this);
    }

    public List<Edge> getInEdges(){
        return graph.getInEdges(this);
    }

    public List<Edge> getIncidentEdges(){
        return graph.getIncidentEdges(this);
    }

    public List<Edge> getEdgesTo(Node n){
        return graph.getEdges(this, n);
    }

    public List<Edge> getEdgesTo(int id){
        return graph.getEdges(this, new Node(id, graph));
    }
}
