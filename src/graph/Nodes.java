package graph;

/**
 * Represents a node in the min heap array
 * Each node has an associated id and priority value
 */
public class Nodes {

    private int id;
    private int priority;

    public Nodes(int id, int priority) {

        this.id = id;
        this.priority = priority;

    }

    /**
     * Gets the priority value
     * @return
     */
    public int getPriority() {
        return this.priority;
    }


    /**
     * Gets the id value
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets a new priority for the node
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }



}
