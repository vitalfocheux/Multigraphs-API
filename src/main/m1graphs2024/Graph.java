package main.m1graphs2024;

import java.util.*;

public class Graph {

    private Map<Node, List<Edge>> adjEdList;

    public Graph(){
        adjEdList = new HashMap<>();
    }

    public Graph(int ... nodes){
        //TODO Constructeur decrit avec le Successor Array
        adjEdList = new HashMap<>();

    }

    //TODO Faire un constructeur qui lit un fichier .dot

    public int nbNodes(){
        return 0;
    }

    public boolean usesNode(Node n){
        return false;
    }

    public boolean holdsNode(Node n){
        return false;
    }

    public Node getNode(int id){
        return null;
    }

    public boolean addNode(Node n){
        return false;
    }

    public boolean removeNode(Node n){
        return false;
    }

    public List<Node> getAllNodes(){
        return null;
    }

    public int largestNodeId(){
        return 0;
    }

    public int smallestNodeId(){
        return 0;
    }

    public List<Node> getSuccessors(Node n){
        return null;
    }

    public List<Node> getSuccessorsMulti(Node n){
        return null;
    }

    public boolean adjacent(Node u, Node v){
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

    public int nbEdges(){
        return 0;
    }

    public boolean existsEdge(Node u, Node v){
        return false;
    }

    public void addEdge(Node from, Node to){

    }

    public boolean removeEdge(Node from, Node to){
        return false;
    }

    public List<Edge> getOutEdges(Node n){
        return null;
    }

    public List<Edge> getInEdges(Node n){
        return null;
    }

    public List<Edge> getIncidentEdges(Node n){
        return null;
    }

    public List<Edge> getEdges(Node u, Node v){
        return null;
    }

    public List<Edge> getAllEdges(){
        return null;
    }


    public int[] toSuccessorArray(){
        return null;
    }

    public int[][] toAdjMatrix(){
        return null;
    }

    public Graph getReverse(){
        return null;
    }

    public Graph getTransitiveClosure(){
        return null;
    }

    public boolean isMultiGraph(){
        return false;
    }

    public boolean hasSelfLoop(){
        return false;
    }

    public Graph toSimpleGraph(){
        return null;
    }

    public Graph copy(){
        return null;
    }
}
