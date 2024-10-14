package m1graphs2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UndirectedGraph extends Graph{

    public UndirectedGraph(){
        super();
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
            }
        }

    }

    public List<Node> getAllNodes(){
        List<Node> nodes = new ArrayList<>();
        adjEdList.keySet().forEach(node -> {
            if(usesNode(node)){
                nodes.add(node);
            }
        });
        return nodes;
    }

    public List<Node> getSuccessors(Node n){
        if(!holdsNode(n)){
            return new ArrayList<>();
        }
        List<Node> successors = new ArrayList<>();
        List<Edge> edges = getAllEdges();
        for(Edge e : edges){
            if(e.from().equals(n) && !successors.contains(e.to())){
                successors.add(e.to());
            }else if(e.to().equals(n) && !successors.contains(e.from())){
                successors.add(e.from());
            }
        }
        return successors;
    }

    public List<Node> getSuccessors(int id){
        return getSuccessors(new Node(id, this));
    }

    public List<Node> getSuccessorsMulti(Node n){
        if(!holdsNode(n)){
            return new ArrayList<>();
        }
        List<Node> successors = new ArrayList<>();
        List<Edge> edges = getAllEdges();
        for(Edge e : edges){
            if(e.from().equals(n)){
                successors.add(e.to());
            }else if(e.to().equals(n)){
                successors.add(e.from());
            }
        }
        return successors;
    }

    public List<Node> getSuccesorsMulti(int id){
        return getSuccessorsMulti(new Node(id, this));
    }

    public int degree(Node n){
        if(!holdsNode(n)){
            return 0;
        }
        int res = adjEdList.get(n).size();
        List<Edge> edges = getAllEdges();
        for(Edge e : edges){
            if(e.to().equals(n)){
                res++;
            }
        }
        return res;
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

    public List<Edge> getOutEdges(Node n){
        List<Edge> outEdges = new ArrayList<>();
        List<Edge> edges = getAllEdges();
        for(Edge e : edges){
            if(e.from().equals(n)){
                outEdges.add(e);
            }else if(e.to().equals(n)){
                outEdges.add(new Edge(e.to(), e.from(), this, e.getWeight()));
            }
        }
        return outEdges;
    }

    public List<Edge> getOutEdges(int id){
        return getOutEdges(new Node(id, this));
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

    public int[][] toAdjMatrix(){
        int[][] adjMatrix = new int[nbNodes()][nbNodes()];
        for(Node n : adjEdList.keySet()){
            for(Edge e : adjEdList.get(n)){
                adjMatrix[e.from().getId() - 1][e.to().getId() - 1] += 1;
                Edge sym = e.getSymmetric();
                if(!existsEdge(sym)){
                    adjMatrix[sym.from().getId() - 1][sym.to().getId() - 1] += 1;
                }
            }
        }
        return adjMatrix;
    }

    public UndirectedGraph getReverse(){
        return copy();
    }

    public UndirectedGraph getTransitiveClosure(){
        UndirectedGraph transitiveClosure = new UndirectedGraph();
        for(Node n : adjEdList.keySet()){
            transitiveClosure.addNode(n);
            List<Node> reachable = getReachable(n, new ArrayList<>());
            reachable.remove(0);
            for(Node r : reachable){
                if(!transitiveClosure.existsEdge(n, r) && !transitiveClosure.existsEdge(r, n)){
                    transitiveClosure.addEdge(n, r);
                }
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


    public UndirectedGraph toSimpleGraph(){
        if(!isMultiGraph() && !hasSelfLoops()){
            return this;
        }
        UndirectedGraph simple = new UndirectedGraph();
        for(Node n : adjEdList.keySet()){
            simple.addNode(n);
            for(Edge e : adjEdList.get(n)){
                if(!simple.existsEdge(e.from(), e.to())){
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
        return fromDotFile(filename, "gv");
    }

    public static UndirectedGraph fromDotFile(String filename, String extension){

        UndirectedGraph g = new UndirectedGraph();

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
                if(line.contains("--")){
                    Integer weight = null;
                    String[] nodes = line.split("--");
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
                }else if(!line.contains("graph") && !line.contains("rankdir") && !line.contains("}")){
                    g.adjEdList.put(new Node(Integer.parseInt(line.trim()), g), new ArrayList<>());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return g;
    }

    public String toDotString(){
        String res = "graph {\n\trankdir=LR\n";
        for(Node n : adjEdList.keySet()){
            if(adjEdList.get(n).isEmpty() && !holdsNode(n)){
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
}
