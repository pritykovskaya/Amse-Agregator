package ru.amse.agregator.test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

import ru.amse.agregator.utils.Tools;

/**
 *
 * @author pavel
 */
public class ToolsTest {

    @Test
    public void getKeysSortedByValueTest1() {
        Map<String, Integer> map = new TreeMap<String, Integer>();
        map.put("a", 23);
        map.put("b", 5);
        map.put("cc", 1);
        List<String> sortedKeys = Tools.getKeysSortedByValue(map);
        assertEquals(sortedKeys.size(), 3);
        assertEquals(sortedKeys.get(0), "cc");
        assertEquals(sortedKeys.get(1), "b");
        assertEquals(sortedKeys.get(2), "a");
    }

    public static void main(String[] args) {
        ToolsTest test = new ToolsTest();
        test.getKeysSortedByValueTest1();
    }

}
