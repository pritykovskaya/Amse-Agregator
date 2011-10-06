package ru.amse.agregator.quality.clusterization.simgraph;

import ru.amse.agregator.quality.clusterization.metric.Metric;
import ru.amse.agregator.storage.DBWrapper;

import java.io.Serializable;
import java.util.ArrayList;

import ru.amse.agregator.storage.UniqueId;


/**
 *
 * @author pavel
 */
abstract public class Graph extends Object implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Metric metric;
    private double threshold;

    public Graph(Metric met, double thresh) {
        metric = met;
        threshold = thresh;
    }

    public Graph(Metric met, double thresh, ArrayList<DBWrapper> objs) {
        this(met, thresh);
        build(objs);
    }

    // inner class representing an edge of a graph
    static public class Edge extends Object implements Serializable {

		private static final long serialVersionUID = 1L;
		
		final public UniqueId obj1, obj2;
        final public double weight;

        public Edge(UniqueId objId1, UniqueId objId2, double edgeWeight) {
            assert(objId1 != null);
            assert(objId2 != null);
            obj1 = objId1;
            obj2 = objId2;
            weight = edgeWeight;
        }
    }

    final public void build(final ArrayList<DBWrapper> objects) {
        //compare all pairs of objects and put in the graph those under the threshold

        //@todo rewrite
        int i = 0;
        for (DBWrapper obj1 : objects) {
            int j = 0;
            for (DBWrapper obj2 : objects) {
                
                if (j <= i) {
                    ++j;
                    continue;
                }
                double distance = metric.compute(obj1, obj2);
                if (distance <= threshold) {
                    addEdge(new Edge(obj1.getUniqueId(), obj2.getUniqueId(), distance));
                }
                ++j;
            }
            ++i;
            if (i % 100 == 0) {
                System.out.println(i);
            }
        }
    }

    abstract protected void addEdge(Edge edge);

    abstract protected void addEdges(ArrayList<Edge> edges);

    abstract public void startIterating();

    abstract public boolean hasNext();

    abstract public Edge getNextEdge();

    abstract public int getEdgeCount();
    
}
