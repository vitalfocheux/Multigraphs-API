package m1graphs2024;

import java.util.Objects;

public class Edge implements Comparable<Edge> {

    private Node from, to;
    private Graph graph;
    private Integer weight;

    public Edge(Node from, Node to, Graph graph){
        this(from, to, graph, 0);
    }

    public Edge(int from, int to, Graph graph){
        this(new Node(from, graph), new Node(to, graph), graph, null);
    }

    public Edge(Node from, Node to, Graph graph, Integer weight){
        this.from = from;
        this.to = to;
        this.graph = graph;
        this.weight = weight;
    }

    public Edge(int from, int to){
        this(new Node(from, null), new Node(to, null), null, null);
    }

    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof Edge){
            Edge e = (Edge) obj;
            return e.from.equals(this.from) && e.to.equals(this.to) && Objects.equals(e.weight, this.weight);
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

    @Override
    public String toString() {
        return from+"->"+to;
    }

    public Node from(){
        return from;
    }

    public Node to(){
        return to;
    }

    public Edge getSymmetric(){
        return new Edge(to, from, graph, weight);
    }

    public boolean isSelfLoop(){
        return from.equals(to);
    }

    public boolean isMultiEdge(){
        return graph.getEdges(from, to).size() > 1;
    }

    public boolean isWeighted(){
        return Objects.nonNull(weight);
    }

    public Integer getWeight(){
        return weight;
    }
}
