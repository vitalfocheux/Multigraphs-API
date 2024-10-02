package m1graphs2024;

public class NodeVisitInfo {

    private Node predeccessor;
    private NodeColour colour;
    private int discovery, finished;

    public NodeVisitInfo() {
        this.predeccessor = null;
        this.colour = NodeColour.WHITE;
        this.discovery = 0;
        this.finished = -1;
    }

    public Node getPredeccessor() {
        return predeccessor;
    }

    public int getDiscovery() {
        return discovery;
    }

    public int getFinished() {
        return finished;
    }

    public NodeColour getColour() {
        return colour;
    }

    public void setColour(NodeColour colour){
        this.colour = colour;
    }


}