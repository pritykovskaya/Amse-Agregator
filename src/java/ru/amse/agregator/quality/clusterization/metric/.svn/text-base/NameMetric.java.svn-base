package ru.amse.agregator.quality.clusterization.metric;

import ru.amse.agregator.storage.DBWrapper;

/**
 *
 * @author pavel
 */
public class NameMetric extends Metric {

    @Override
    public double compute(DBWrapper obj1, DBWrapper obj2) {
        if ((obj1.getName() == null) || (obj2.getName() == null)) {
            return 1.0;
        }
        return getNormalizedDistance(obj1.getName(), obj2.getName());
    }

}
