package ru.amse.agregator.quality.clusterization.simgraph;

import java.util.ArrayList;
import java.util.Iterator;

import ru.amse.agregator.quality.clusterization.metric.Metric;
import ru.amse.agregator.storage.DBWrapper;
import ru.amse.agregator.storage.UniqueId;

/**
 *
 * @author pavel
 */
final public class ArrayGraph extends Graph {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	
	@SuppressWarnings("rawtypes")
	private Iterator iterator;

    public ArrayGraph(Metric metric, double threshold) {
        super(metric, threshold);
    }

    public ArrayGraph(Metric metric, double threshold, ArrayList<DBWrapper> objects) {
        super(metric, threshold, objects);
    }

    @Override
	protected void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
	protected void addEdges(ArrayList<Edge> edges) {
        this.edges.addAll(edges);
    }

    @Override
	public void startIterating() {
        iterator = edges.iterator();
    }

    @Override
	public Edge getNextEdge() {
        return (Edge)iterator.next();
    }

    @Override
	public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
	public int getEdgeCount() {
        return edges.size();
    }
}
