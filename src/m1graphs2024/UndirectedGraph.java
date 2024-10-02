package m1graphs2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UndirectedGraph extends Graph{

    private Map<Node, List<Edge>> adjEdList;

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
//                if(!adjEdList.containsKey(new Node(n, this))){
//                    adjEdList.put(new Node(n, this), new ArrayList<>());
//                }
                i++;
            }else{
                if(!adjEdList.containsKey(new Node(i, this))){
                    adjEdList.put(new Node(i, this), new ArrayList<>());
                }
                if(!adjEdList.containsKey(new Node(n, this))){
                    adjEdList.put(new Node(n, this), new ArrayList<>());
                }
                adjEdList.get(new Node(i, this)).add(new Edge(new Node(i, this), new Node(n, this), this));
                adjEdList.get(new Node(n, this)).add(new Edge(new Node(n, this), new Node(i, this), this));
            }
        }

    }

    @Override
    public String toString() {
        String res = "";
        for(Node n : adjEdList.keySet()){
            res += n.getId() + " -> ";
            int size = adjEdList.get(n).size();
            for(int i = 0; i < size; i++){
                res += "("+n.getId()+", "+adjEdList.get(n).get(i).to().getId()+")";
                if(i < size - 1){
                    res += ", ";
                }
            }
            res += "\n";
        }
        return res;
    }

    public int nbNodes(){
        return adjEdList.size();
    }

    public boolean usesNode(Node n){
        return adjEdList.get(n) != null;
    }

    public boolean usesNode(int id){
        return usesNode(new Node(id, this));
    }

    public boolean holdsNode(Node n){
        return adjEdList.containsKey(n);
    }

    public boolean holdsNode(int id){
        return holdsNode(new Node(id, this));
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

    public boolean addNoded(int id){
        return addNode(new Node(id, this));
    }

    //TODO faire le removeNode
    public boolean removeNode(Node n){
        return false;
    }

    public boolean removeNode(int id){
        return removeNode(new Node(id, this));
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

    public List<Node> getSuccessors(int id){
        return getSuccessors(new Node(id, this));
    }

    public List<Node> getSuccessorsMulti(Node n){
        List<Node> successors = new ArrayList<>();
        if(!holdsNode(n)){
            return successors;
        }
        adjEdList.get(n).forEach(e -> successors.add(e.to()));
        return successors;
    }

    public List<Node> getSuccessorsMulti(int id){
        return getSuccessorsMulti(new Node(id, this));
    }

    public boolean adjacent(Node u, Node v){
        if(!holdsNode(u) || !holdsNode(v)){
            return false;
        }
        return adjEdList.get(u).stream().anyMatch(e -> e.to().equals(v)) || adjEdList.get(v).stream().anyMatch(e -> e.to().equals(u));
    }

    public boolean adjacent(int u, int v){
        return adjacent(new Node(u, this), new Node(v, this));
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
}
