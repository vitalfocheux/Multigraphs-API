package main.m1graphs2024;

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
        return null;
    }

    public List<Node> getSuccessorsMulti(){
        return null;
    }

    public boolean adjacent(Node n){
        return false;
    }

    public int inDegree(Node n){
        return 0;
    }

    public int outDegree(Node n){
        return 0;
    }

    public int degree(Node n){
        return 0;
    }

    public List<Edge> getOutEdges(){
        return null;
    }

    public List<Edge> getInEdges(){
        return null;
    }

    public List<Edge> getIncidentEdges(){
        return null;
    }

    public List<Edge> getEdgesTo(Node n){
        return null;
    }
}
