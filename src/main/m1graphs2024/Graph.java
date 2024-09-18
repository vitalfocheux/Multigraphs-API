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
}
