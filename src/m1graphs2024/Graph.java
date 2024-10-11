package m1graphs2024;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph {

    protected Map<Node, List<Edge>> adjEdList;

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
                adjEdList.get(new Node(i, this)).add(new Edge(new Node(i, this), new Node(n, this), this, null));
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

    public static Graph fromDotFile(String filename){
        return Graph.fromDotFile(filename, "gv");
    }

    public static Graph fromDotFile(String filename, String extension){
        Graph g = new Graph();


        Path startPath = Paths.get(System.getProperty("user.dir"));
        String pattern = filename+"."+extension;
        final String[] src = {""};

        try {
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.getFileName().toString().equals(pattern)) {
                        if(!file.toAbsolutePath().toString().contains("out")){
                            src[0] = file.toAbsolutePath().toString();
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader br = new BufferedReader(new FileReader(src[0]))){
            String line;
            while((line = br.readLine()) != null){
                if(line.contains("->")){
                    Integer weight = null;
                    String[] nodes = line.split("->");
                    Node from = new Node(Integer.parseInt(nodes[0].trim()), g);
                    if(nodes[1].contains("[")){

                        Pattern pattern_ = Pattern.compile("label=(\\d+)");
                        Matcher matcher = pattern_.matcher(nodes[1]);
                        if(matcher.find()){
                            weight = Integer.parseInt(matcher.group(1));
                        }
                        nodes[1] = nodes[1].substring(0, nodes[1].indexOf("["));
                    }
                    Node to = new Node(Integer.parseInt(nodes[1].trim()), g);

                    if(!g.adjEdList.containsKey(from)){
                        g.adjEdList.put(from, new ArrayList<>());
                    }

                    Edge e = new Edge(from, to, g, weight);
                    g.addEdge(e);
                }else if(!line.contains("digraph") && !line.contains("rankdir") && !line.contains("}")){
                    g.adjEdList.put(new Node(Integer.parseInt(line.trim()), g), new ArrayList<>());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return g;


    }


    public String toDotString(){
        String res = "digraph {\n\trankdir=LR\n";
        for(Node n : adjEdList.keySet()){
            if(adjEdList.get(n).isEmpty() && !usesNode(n)){
                res += "\t"+n.getId()+"\n";
            }
            for(Edge e : adjEdList.get(n)){
                res += "\t"+n.getId()+" -> "+e.to().getId();
                if(e.isWeighted()){
                    res += " [label="+e.getWeight()+", len="+e.getWeight()+"]";
                }
                res += "\n";
            }
        }
        return res+"}\n";
    }

    public int nbNodes(){
        return adjEdList.size();
    }

    public boolean usesNode(Node n){
        if(!adjEdList.containsKey(n)){
            return false;
        }
        for(Node node : adjEdList.keySet()){
            for(Edge e : adjEdList.get(node)){
                if(e.to().equals(n) || e.from().equals(n)){
                    return true;
                }
            }
        }
        return false;
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

    public boolean addNode(int id){
        return addNode(new Node(id, this));
    }

    //TODO faire le removeNode
    public boolean removeNode(Node n){
        if(!holdsNode(n)){
            return false;
        }
        adjEdList.remove(n);
        for(Node node : adjEdList.keySet()){
            adjEdList.get(node).removeIf(e -> e.to().equals(n));
            adjEdList.get(node).removeIf(e -> e.from().equals(n));
        }
        return true;
    }

    public boolean removeNode(int n){
        return removeNode(new Node(n, this));
    }

    public List<Node> getAllNodes(){
        return new ArrayList<>(adjEdList.keySet());
    }

    public int largestNodeId(){
        return adjEdList.keySet().stream().mapToInt(Node::getId).max().orElse(0);
    }

    public int smallestNodeId(){
        return adjEdList.keySet().stream().mapToInt(Node::getId).min().orElse(0);
    }

    public List<Node> getSuccessors(Node n){
        List<Node> successors = new ArrayList<>();
        if(!holdsNode(n)) {
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

    public int inDegree(Node n){
        int inDegree = 0;
        if(!holdsNode(n)){
            return inDegree;
        }
        for(Node node : adjEdList.keySet()){
            for(Edge e : adjEdList.get(node)){
                if(e.to().equals(n)){
                    inDegree++;
                }
            }
        }
        return inDegree;
    }

    public int inDegree(int id){
        return inDegree(new Node(id, this));
    }

    public int outDegree(Node n){
        if(!holdsNode(n)){
            return 0;
        }
        return adjEdList.get(n).size();
    }

    public int outDegree(int id){
        return outDegree(new Node(id, this));
    }

    public int degree(Node n){
        return inDegree(n) + outDegree(n);
    }

    public int degree(int id){
        return degree(new Node(id, this));
    }

    public int nbEdges(){
        return adjEdList.values().stream().mapToInt(List::size).sum();
    }

    public boolean existsEdge(Node u, Node v){
        if(!holdsNode(u) || !holdsNode(v)){
            return false;
        }
        for(Edge e : adjEdList.get(u)){
            if(e.to().equals(v)){
                return true;
            }
        }
        return false;
    }

    public boolean existsEdge(int u, int v){
        return existsEdge(new Node(u, this), new Node(v, this));
    }

    public void addEdge(Node from, Node to){
        addEdge(from, to, null);
    }

    public void addEdge(Node from, Node to, Integer weight){
        if(!holdsNode(from)){
            addNode(from);
        }
        if(!holdsNode(to)){
            addNode(to);
        }
        adjEdList.get(from).add(new Edge(from, to, this, weight));
        Collections.sort(adjEdList.get(from));
    }

    public void addEdge(int from, int to){
        addEdge(new Node(from, this), new Node(to, this), null);
    }

    public void addEdge(Edge e){
        addEdge(e.from(), e.to(), e.getWeight());
    }

    public boolean removeEdge(Node from, Node to){
        if(!holdsNode(from) || !holdsNode(to)){
            return false;
        }
        return adjEdList.get(from).removeIf(e -> e.to().equals(to));
    }

    public boolean removeEdge(int from, int to){
        return removeEdge(new Node(from, this), new Node(to, this));
    }

    public List<Edge> getOutEdges(Node n){
        if(!holdsNode(n)){
            return new ArrayList<>();
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
        List<Edge> edges = new ArrayList<>();
        if(!holdsNode(n)){
            return edges;
        }
        edges.addAll(getInEdges(n));
        edges.addAll(getOutEdges(n));
        return edges;
    }

    public List<Edge> getEdges(Node u, Node v){
        if(!holdsNode(u) || !holdsNode(v)){
            return new ArrayList<>();
        }
        List<Edge> edges = new ArrayList<>();
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

    public List<Edge> getEdges(int u, int v){
        return getEdges(new Node(u, this), new Node(v, this));
    }

    public List<Edge> getAllEdges(){
        List<Edge> edges = new ArrayList<>();
        for(Node n : adjEdList.keySet()){
            edges.addAll(adjEdList.get(n));
        }
        return edges;
    }


    public int[] toSuccessorArray(){
        int[] succArray = new int[nbNodes()+nbEdges()];
        int i = 0;
        for(Node n : adjEdList.keySet()){
            for(Edge e : adjEdList.get(n)){
                succArray[i] = e.to().getId();
                i++;
            }
            succArray[i] = 0;
            i++;
        }
        return succArray;
    }

    public int[][] toAdjMatrix(){
        int[][] adjMatrix = new int[nbNodes()][nbNodes()];
        for(Node n : adjEdList.keySet()){
            for(Edge e : adjEdList.get(n)){
                adjMatrix[n.getId()-1][e.to().getId()-1] = 1;
            }
        }
        return adjMatrix;
    }

    public Graph getReverse(){
        Graph reverse = new Graph();
        for(Node n : adjEdList.keySet()){
            reverse.addNode(n);
            for(Edge e : adjEdList.get(n)){
                reverse.addEdge(e.getSymmetric());
            }
        }
        return reverse;
    }

    public Graph getTransitiveClosure(){
        Graph transitiveClosure = new Graph();
        for(Node n : adjEdList.keySet()){
            transitiveClosure.addNode(n);
            List<Node> reachable = getReachable(n, new ArrayList<>());
            reachable.remove(0);
            for(Node r : reachable){
                transitiveClosure.addEdge(n, r);
            }
        }
        return transitiveClosure;
    }

    private List<Node> getReachable(Node n, List<Node> reachable){
        if(!reachable.contains(n)){
            reachable.add(n);
            for(Node s : getSuccessors(n)){
                getReachable(s, reachable);
            }
        }
        return reachable;

    }


    public boolean isMultiGraph(){
        for(Node n : adjEdList.keySet()){
            List<Edge> edges = adjEdList.get(n);
            for(int i = 0; i < edges.size(); i++){
                for(int j = i + 1; j < edges.size(); j++){
                    if(edges.get(i).to().equals(edges.get(j).to())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasSelfLoops(){
        for(Node n : adjEdList.keySet()){
            for(Edge e : adjEdList.get(n)){
                if(e.isSelfLoop()){
                    return true;
                }
            }
        }
        return false;
    }

    public Graph toSimpleGraph(){
        if(!isMultiGraph() && !hasSelfLoops()){
            return this;
        }
        Graph simpleGraph = new Graph();
        for(Node n : adjEdList.keySet()){
            simpleGraph.addNode(n);
            for(Edge e : adjEdList.get(n)){
                if(!simpleGraph.existsEdge(e.from(), e.to()) && !e.isSelfLoop()){
                    simpleGraph.addEdge(e);
                }
            }
        }

        return simpleGraph;
    }

    public Graph copy(){
        Graph copy = new Graph();
        for(Node n : adjEdList.keySet()){
            copy.addNode(n);
            for(Edge e : adjEdList.get(n)){
                copy.addEdge(e);
            }
        }
        return copy;
    }

    public List<Node> getDFS() {
        Node start = new Node(smallestNodeId(), this);
        return getDFS(start);
    }

    public List<Node> getDFS(Node n) {
        List<Node> dfs = new ArrayList<>();
        return getDFS(n, dfs);
    }

    public List<Node> getDFS(int id){
        return getDFS(new Node(id, this));
    }

    private List<Node> getDFS(Node n, List<Node> dfs){
        if(!dfs.contains(n)){
            dfs.add(n);
            for(Node s : getSuccessors(n)){
                getDFS(s, dfs);
            }
        }
        if(dfs.size() < nbNodes()){
            for(Node node : adjEdList.keySet()){
                if(!dfs.contains(node)){
                    getDFS(node, dfs);
                }
            }
        }
        return dfs;
    }

    public List<Node> getBFS() {
        Node start = new Node(smallestNodeId(), this);
        return getBFS(start);
    }

    public List<Node> getBFS(Node n) {
        List<Node> bfs = new ArrayList<>();
        return getBFS(n, bfs);
    }

    public List<Node> getBFS(int id){
        return getBFS(new Node(id, this));
    }

    private List<Node> getBFS(Node n, List<Node> bfs){
        Queue<Node> queue = new LinkedList<>();
        queue.add(n);
        while(!queue.isEmpty()){
            Node node = queue.poll();
            if(!bfs.contains(node)){
                bfs.add(node);
                for(Node s : getSuccessors(node)){
                    if(!bfs.contains(s)){
                        queue.add(s);
                    }
                }
            }
        }
        if(bfs.size() < nbNodes()){
            for(Node node : adjEdList.keySet()){
                if(!bfs.contains(node)){
                    getDFS(node, bfs);
                }
            }
        }
        return bfs;
    }

    public List<Node> getDFSWithVisitInfo(Map<Node, NodeVisitInfo> nodeVisit, Map<Edge, EdgeVisitType> edgeVisit){
        Node start = new Node(smallestNodeId(), this);
        return getDFSWithVisitInfo(start, nodeVisit, edgeVisit);
    }

    public List<Node> getDFSWithVisitInfo(Node n, Map<Node, NodeVisitInfo> nodeVisit, Map<Edge, EdgeVisitType> edgeVisit){
        List<Node> dfs = new ArrayList<>();

        return dfs;
    }
}
