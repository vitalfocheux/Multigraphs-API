package main.m1graphs2024;

public class Edge implements Comparable<Edge> {

    private Node from, to;
    private Graph graph;
    private int weight;

    public int hashCode() {
        return 0;
    }

    public boolean equals(Object obj) {
        return false;
    }


    @Override
    public int compareTo(Edge o) {
        return 0;
    }

    public Node from(){
        return from;
    }

    public Node to(){
        return to;
    }

    public Edge getSymmetric(){
        return null;
    }

    public boolean isSelfLoop(){
        return false;
    }

    public boolean isWeighted(){
        return false;
    }

    public int getWeight(){
        return weight;
    }
}
