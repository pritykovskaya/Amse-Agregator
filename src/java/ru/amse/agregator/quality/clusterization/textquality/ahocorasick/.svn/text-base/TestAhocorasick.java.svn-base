package ru.amse.agregator.quality.clusterization.textquality.ahocorasick;

import java.util.Iterator;
import java.util.Arrays;

public class TestAhocorasick{
  static AhoCorasick buildTree(String... args){
    AhoCorasick tree = new AhoCorasick();
    for(int i = 0; i < args.length; i++)
      tree.add(args[i].getBytes(), 1);
    tree.prepare();
    return tree;
  }

  public static void main(String[] args){
    AhoCorasick tree = buildTree("foo", "bar");

    Iterator<SearchResult> searcher = tree.search("foo bar baz".getBytes());

    assert searcher.hasNext();
    
    SearchResult result = searcher.next();
    assert (result.getOutputs().length == 1);
    assert (result.getOutputs()[0] == 1);

    result = searcher.next();
    assert (result.getOutputs().length == 1);
    assert (result.getOutputs()[0] == 1);
    
    assert !searcher.hasNext();

    tree = buildTree("foo", "boofoo");
    searcher = tree.search("boofoo".getBytes());

    int[] results = searcher.next().getOutputs();
    Arrays.sort(results);
    
    assert results.length == 2;
    assert results[0] == 1;
    assert results[1] == 2;

    assert !searcher.hasNext();

    searcher = tree.search("boofoboofoo".getBytes());
    results = searcher.next().getOutputs();
    Arrays.sort(results);
    
    assert results.length == 2;
    assert results[0] == 1;
    assert results[1] == 2;

    assert !searcher.hasNext();

    tree = buildTree("foo", "boo");
    searcher = tree.search("foo foo boo".getBytes());

    while (searcher.hasNext()) {
        results = searcher.next().getOutputs();
        for (int i = 0; i < results.length; ++i) {
            System.out.println(results[i]);
        }
    }

    
  }
}
