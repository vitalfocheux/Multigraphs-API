package main.m1graphs2024;

public class Edge implements Comparable<Edge> {

    private Node from, to;
    private Graph graph;
    private int weight;

    public Edge(Node from, Node to, Graph graph){
        this(from, to, graph, 0);
    }

    public Edge(Node from, Node to, Graph graph, int weight){
        this.from = from;
        this.to = to;
        this.graph = graph;
        this.weight = weight;
    }

    public int hashCode() {
        return 0;
    }

    public boolean equals(Object obj) {
        if(obj instanceof Edge){
            Edge e = (Edge) obj;
            return e.from.equals(this.from) && e.to.equals(this.to);
        }
        return false;
    }


    @Override
    public int compareTo(Edge o) {
        if(this.from.equals(o.from)){
            return this.to.compareTo(o.to);
        }
        return this.from.compareTo(o.from);
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
