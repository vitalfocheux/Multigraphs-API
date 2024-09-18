package main.m1graphs2024;

import java.util.*;

public class Node implements Comparable<Node>{

    private int id;
    private String name;
    private Graph graph;


    public boolean equals(Object obj) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    @Override
    public int compareTo(Node o) {
        return 0;
    }

    public int getId() {
        return id;
    }

    public int getGraph() {
        return 0;
    }

    public int getName() {
        return 0;
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
