package m1graphs2024;

import java.io.*;
import java.util.*;

public class Graph {

    private Map<Node, List<Edge>> adjEdList;

    public Graph(){
        adjEdList = new HashMap<>();
    }

    public Graph(int ... nodes){
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
                adjEdList.get(new Node(i, this)).add(new Edge(new Node(i, this), new Node(n, this), this));
            }
        }
    }

    //TODO Faire un constructeur qui lit un fichier .dot
    public Graph(String dotFile) {
        adjEdList = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(dotFile))){
            String line;
            while((line = br.readLine()) != null){
                if(line.contains("->")){
                    String[] nodes = line.split("->");
                    Node from = new Node(Integer.parseInt(nodes[0].trim()), this);
                    Node to = new Node(Integer.parseInt(nodes[1].trim()), this);
                    if(!adjEdList.containsKey(from)){
                        adjEdList.put(from, new ArrayList<>());
                    }
                    adjEdList.get(from).add(new Edge(from, to, this));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String res = "";
        for(Node n : adjEdList.keySet()){
            res += n.getId() + " -> ";
            for(Edge e : adjEdList.get(n)){
                res += "("+n.getId()+", "+e.to().getId()+"), ";
            }
            res += "\n";
        }
        return res;
    }

    public String toDotString(){
        return toString();
    }

    public int nbNodes(){
        return adjEdList.size();
    }

    public boolean usesNode(Node n){
        return adjEdList.get(n) != null;
    }

    public boolean holdsNode(Node n){
        return adjEdList.containsKey(n);
    }

    public Node getNode(int id){
        return adjEdList.keySet().stream().filter(n -> n.getId() == id).findFirst().orElse(null);
    }

    public boolean addNode(Node n){
        if(adjEdList.containsKey(n)){
            return false;
        }
        adjEdList.put(n, new ArrayList<>());
        return true;
    }

    public boolean removeNode(Node n){
        return false;
    }

    public boolean removeNode(int n){
        return false;
    }

    public List<Node> getAllNodes(){
        return new ArrayList<>(adjEdList.keySet());
    }

    public int largestNodeId(){
        return adjEdList.keySet().stream().mapToInt(Node::getId).max().orElse(-1);
    }

    public int smallestNodeId(){
        return adjEdList.keySet().stream().mapToInt(Node::getId).min().orElse(-1);
    }

    public List<Node> getSuccessors(Node n){
        List<Node> successors = new ArrayList<>();
        if(!holdsNode(n)){
            return successors;
        }
        List<Edge> edges = adjEdList.get(n);
        for(Edge e : edges){
            if(!successors.contains(e.to())) {
                successors.add(e.to());
            }
        }
        return successors;
    }

    public List<Node> getSuccessorsMulti(Node n){
        List<Node> successors = new ArrayList<>();
        if(!holdsNode(n)){
            return successors;
        }
        adjEdList.get(n).forEach(e -> successors.add(e.to()));
        return successors;
    }

    public boolean adjacent(Node u, Node v){
        if(!holdsNode(u) || !holdsNode(v)){
            return false;
        }
        return adjEdList.get(u).stream().anyMatch(e -> e.to().equals(v)) || adjEdList.get(v).stream().anyMatch(e -> e.to().equals(u));
    }

    public int inDegree(Node n){
        return 0;
    }

    public int inDegree(int n){
        return 0;
    }

    public int outDegree(Node n){
        return 0;
    }

    public int outDegree(int n){
        return 0;
    }

    public int degree(Node n){
        return 0;
    }

    public int degree(int n){
        return 0;
    }

    public int nbEdges(){
        return adjEdList.values().stream().mapToInt(List::size).sum();
    }

    public boolean existsEdge(Node u, Node v){
        if(!holdsNode(u) || !holdsNode(v)){
            return false;
        }
        //TODO vérifier la diff entre adjacent et existsEdge
        return true;
    }

    public boolean existsEdge(int u, int v){
        return existsEdge(new Node(u, null), new Node(v, null));
    }

    public boolean addEdge(Node from, Node to){
        if(!holdsNode(from) || !holdsNode(to)){
            return false;
        }
        return adjEdList.get(from).add(new Edge(from, to, this));
    }

    //TODO voir cette surcharge
    public boolean addEdge(int from, int to){
        return addEdge(new Node(from, null), new Node(to, null));
    }

    public boolean addEdge(Edge e){
        return addEdge(e.from(), e.to());
    }

    public boolean removeEdge(Node from, Node to){
        if(!holdsNode(from) || !holdsNode(to)){
            return false;
        }
        return adjEdList.get(from).removeIf(e -> e.to().equals(to));
    }

    public boolean removeEdge(int from, int to){
        return removeEdge(new Node(from, null), new Node(to, null));
    }

    public List<Edge> getOutEdges(Node n){
        List<Edge> outEdges = new ArrayList<>();
        if(!holdsNode(n)){
            return outEdges;
        }
        return adjEdList.get(n);
    }

    public List<Edge> getInEdges(Node n){
        List<Edge> inEdges = new ArrayList<>();
        if(!holdsNode(n)){
            return inEdges;
        }
        for(Node node : adjEdList.keySet()){
            for(Edge e : adjEdList.get(node)){
                if(e.to().equals(n)){
                    inEdges.add(e);
                }
            }
        }
        return inEdges;
    }

    public List<Edge> getIncidentEdges(Node n){
        return null;
    }

    public List<Edge> getEdges(Node u, Node v){
        List<Edge> edges = new ArrayList<>();
        if(!holdsNode(u) || !holdsNode(v)){
            return edges;
        }
        for(Edge e : adjEdList.get(u)){
            if(e.to().equals(v)){
                edges.add(e);
            }
        }
        for(Edge e : adjEdList.get(v)){
            if(e.to().equals(u)){
                edges.add(e);
            }
        }
        return edges;
    }

    public List<Edge> getAllEdges(){
        List<Edge> edges = new ArrayList<>();
        for(Node n : adjEdList.keySet()){
            edges.addAll(adjEdList.get(n));
        }
        return edges;
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
