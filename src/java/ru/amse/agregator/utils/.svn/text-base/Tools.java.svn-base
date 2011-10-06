package ru.amse.agregator.utils;

import java.util.*;

/**
 *
 * @author pavel
 */
public final class Tools {

    //method eliminates all the duplicates in the collection
    static public <E extends Object> void eliminateDuplicates(Collection<E> collection) {

        HashSet<E> hashSet = new HashSet<E>();
        hashSet.addAll(collection);
        collection.clear();
        collection.addAll(hashSet);
    }

    //returns a list of a map's keys sorted by their values
    //source : http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
    public static <K, V extends Comparable<? super V>> List<K> getKeysSortedByValue(Map<K, V> map) {
        final int size = map.size();
        final List<Map.Entry<K, V>> list =
                new ArrayList<Map.Entry<K, V>>(map.entrySet());
        final ValueComparator<V> cmp = new ValueComparator<V>();
        Collections.sort(list, cmp);
        final ArrayList<K> keys = new ArrayList<K>();
        keys.ensureCapacity(size);
        for (Map.Entry<K, V> entry : list) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    private static final class ValueComparator<V extends Comparable<? super V>>
                                   implements Comparator<Map.Entry<?, V>> {
        @Override
        public int compare(Map.Entry<?, V> o1, Map.Entry<?, V> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }


}
