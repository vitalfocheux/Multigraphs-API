package m1graphs2024;

import java.util.*;

public class UndirectedGraph extends Graph{

    //private Map<Node, List<Edge>> adjEdList;

    public UndirectedGraph(){
        adjEdList = new HashMap<>();
    }

    public UndirectedGraph(int... nodes){
        adjEdList = new HashMap<>();
        int i = 1;
        for(int n : nodes){
            if(n == 0){
                if(!adjEdList.containsKey(new Node(i, this))) {
                    adjEdList.put(new Node(i, this), new ArrayList<>());
                }
                i++;
            }else{
                if(!adjEdList.containsKey(new Node(i, this))){
                    adjEdList.put(new Node(i, this), new ArrayList<>());
                }
                if(!adjEdList.containsKey(new Node(n, this))){
                    adjEdList.put(new Node(n, this), new ArrayList<>());
                }
                adjEdList.get(new Node(i, this)).add(new Edge(new Node(i, this), new Node(n, this), this, null));
                adjEdList.get(new Node(n, this)).add(new Edge(new Node(n, this), new Node(i, this), this, null));
            }
        }

    }


    //TODO faire le removeNode
    public boolean removeNode(Node n){
        return false;
    }

    public boolean removeNode(int id){
        return removeNode(new Node(id, this));
    }

    public int degree(Node n){
        if(!holdsNode(n)){
            return 0;
        }
        return adjEdList.get(n).size();
    }

    public int degree(int id){
        return degree(new Node(id, this));
    }

    public int outDegree(Node n){
        return degree(n);
    }

    public int outDegree(int id){
        return degree(new Node(id, this));
    }

    public int inDegree(Node n){
        return degree(n);
    }

    public int inDegree(int id){
        return degree(new Node(id, this));
    }

    public int nbEdges(){
        return adjEdList.values().stream().mapToInt(List::size).sum() / 2;
    }

    public boolean existsEdge(Node u, Node v){
        if(!holdsNode(u) || !holdsNode(v)){
            return false;
        }
        return adjEdList.get(u).stream().anyMatch(e -> e.to().equals(v)) || adjEdList.get(v).stream().anyMatch(e -> e.to().equals(u));
    }

    public boolean existsEdge(int u, int v){
        return existsEdge(new Node(u, this), new Node(v, this));
    }

    public void addEdge(Node from, Node to){
        if(!holdsNode(from)){
            addNode(from);
        }
        if(!holdsNode(to)){
            addNode(to);
        }
        adjEdList.get(from).add(new Edge(from, to, this));
        adjEdList.get(to).add(new Edge(to, from, this));
        Collections.sort(adjEdList.get(from));
    }

    public void addEdge(int from, int to){
        addEdge(new Node(from, this), new Node(to, this));
    }

    public boolean removeEdge(Node from, Node to){
        if(!holdsNode(from) || !holdsNode(to)){
            return false;
        }
        if(!adjEdList.get(from).stream().anyMatch(e -> e.to().equals(to))){
            return false;
        }
        adjEdList.get(from).removeIf(e -> e.to().equals(to));
        adjEdList.get(to).removeIf(e -> e.to().equals(from));
        return true;
    }

    public boolean removeEdge(int from, int to){
        return removeEdge(new Node(from, this), new Node(to, this));
    }

    public List<Edge> getInEdges(Node n){
        return getOutEdges(n);
    }

    public List<Edge> getInEdges(int id){
        return getOutEdges(new Node(id, this));
    }

    public List<Edge> getIncidentEdges(Node n){
        return getOutEdges(n);
    }

    public List<Edge> getIncidentEdges(int id){
        return getOutEdges(new Node(id, this));
    }

    public List<Edge> getEdges(Node u, Node v){
        if(!holdsNode(u) || !holdsNode(v)){
            return new ArrayList<>();
        }
        List<Edge> edges = new ArrayList<>();
        adjEdList.get(u).forEach(e -> {
            if(e.to().equals(v)){
                edges.add(e);
            }
        });
        return edges;
    }

    public List<Edge> getEdges(int u, int v){
        return getEdges(new Node(u, this), new Node(v, this));
    }

    public List<Edge> getAllEdges(){
        List<Edge> edges = new ArrayList<>();
        adjEdList.values().forEach(edges::addAll);
        return edges;
    }

    public UndirectedGraph getReverse(){
        return copy();
    }

    public UndirectedGraph getTransitiveClosure(){
        UndirectedGraph transitive = new UndirectedGraph();
        for(Node n : adjEdList.keySet()){
            transitive.addNode(n);
            List<Node> reachable = getDFS(n);
            for(Node r : reachable){
                transitive.addEdge(n, r);
            }
        }
        return transitive;
    }


    public UndirectedGraph toSimpleGraph(){
        if(!isMultiGraph() && !hasSelfLoops()){
            return this;
        }
        UndirectedGraph simple = new UndirectedGraph();
        for(Node n : adjEdList.keySet()){
            simple.addNode(n);
            for(Edge e : adjEdList.get(n)){
                if(!simple.existsEdge(e.from(), e.to()) && !e.isSelfLoop()){
                    simple.addEdge(e);
                }
            }
        }
        return simple;
    }

    public UndirectedGraph copy(){
        UndirectedGraph copy = new UndirectedGraph();
        for(Node n : adjEdList.keySet()){
            copy.addNode(n);
            for(Edge e : adjEdList.get(n)){
                copy.addEdge(e);
            }
        }
        return copy;
    }

    public static UndirectedGraph fromDotFile(String filename){
        return new UndirectedGraph();
    }

    public static UndirectedGraph fromDotFile(String filename, String extension){
        return new UndirectedGraph();
    }

    public String toDotString(){
        String res = "graph {\n\trankdir=LR\n";
        for(Node n : adjEdList.keySet()){
            if(adjEdList.get(n).isEmpty() && !usesNode(n)){
                res += "\t"+n.getId()+"\n";
            }
            for(Edge e : adjEdList.get(n)){
                res += "\t"+n.getId()+" -- "+e.to().getId();
                if(e.isWeighted()){
                    res += " [label="+e.getWeight()+", len="+e.getWeight()+"]";
                }
                res += "\n";
            }
        }
        res += "}\n";
        return res;
    }

    public void toDotFile(String filename){

    }

    public void toDotFile(String filename, String extension){

    }
}
