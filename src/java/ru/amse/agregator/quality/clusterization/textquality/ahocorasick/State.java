package ru.amse.agregator.quality.clusterization.textquality.ahocorasick;

/**
   A state represents an element in the Aho-Corasick tree.
 */


class State {
    private State fail;

    public State extend(byte b) {
        if (get(b) != null) {
            return get(b);
        }
        State nextState = new State();
        this.put(b, nextState);
        return nextState;
    }


    public State extendAll(byte[] bytes) {
        State state = this;
        for (int i = 0; i < bytes.length; i++) {
            if (state.get(bytes[i]) != null) {
                state = state.get(bytes[i]);
            } else {
                state = state.extend(bytes[i]);
            }
        }
        return state;
    }


    /**
       Returns the size of the tree rooted at this State.  Note: do
       not call this if there are loops in the edgelist graph, such as
       those introduced by AhoCorasick.prepare().
     */
    public int size() {
        byte[] keys = keys();
        int result = 1;
        for (int i = 0; i < keys.length; i++) {
            result += get(keys[i]).size();
        }
        return result;
    }


    public State getFail() {
        return this.fail;
    }


    public void setFail(State f) {
        this.fail = f;
    }


    // BEGIN STATE MAP
    // This is basically an inlined map of bytes to states.
    // It is very hacky because it is designed to use absolutely
    // as little space as possible. There be great evil here.

    static final byte[] EMPTY_BYTES = new byte[0];

    // Oh what a hack
    // if the map has size 0 keys is null, states is null
    // if the map has size 1 keys is a Byte, states is a T
    // else keys is byte[] and states is an Object[] of the same size
    // carrying the bytes in parallel
    private Object keys;
    private Object states;

    public State get(byte key) {
        if(keys == null) {
            return null;
        }
        if(keys instanceof Byte) {
            return ((Byte)keys).byteValue() == key ? (State)states : null;
        }
        byte[] keys = (byte[])this.keys;
        for(int i = 0; i < keys.length; i++) {
            if(keys[i] == key) return (State)((Object[])states)[i];
        }
        return null;
    }

  public void put(byte key, State value) {
    if(keys == null) {
      keys = key;
      states = value;
      return;
    }

    if(keys instanceof Byte){
      if(((Byte)keys).byteValue() == key){
        states = value;
      } else {
        keys = new byte[]{((Byte)keys).byteValue(), key};
        states = new Object[]{states, value};
      }
      return;
    }

    byte[] keys = (byte[])this.keys;
    Object[] states = (Object[])this.states;

    for(int i = 0; i < keys.length; i++){
      if(keys[i] == key){
        states[i] = value;
        return;
      }
    }

    byte[] newkeys = new byte[keys.length + 1];
    for(int i = 0; i < keys.length; i++){
      newkeys[i] = keys[i]; 
    }
    newkeys[newkeys.length - 1] = key;
   
    Object[] newstates = new Object[states.length + 1];
    for(int i = 0; i < states.length; i++){
      newstates[i] = states[i]; 
    }
    newstates[newstates.length - 1] = value;

    this.keys = newkeys;
    this.states = newstates;
  }

  public byte[] keys(){
    if(keys == null) return EMPTY_BYTES; 
    if(keys instanceof Byte) return new byte[]{((Byte)keys).byteValue()};
    return ((byte[])keys);
  }
  // END STATE MAP


  // BEGIN OUTPUT SET
  
  // Same story. here's an inlined set of ints backed by an array of
  // ints.

  private static int[] EMPTY_INTS = new int[0];

  // null when empty
  // an Integer when size 1
  // an int[] when size > 1
  private Object outputs;

  public void addAllOutputs(State that){
    for(int j : that.getOutputs()) addOutput(j);
  }

  public void addOutput(int i){
    if(outputs == null) outputs = i;  
    else if(outputs instanceof Integer){
      int j = ((Integer)outputs).intValue();
      if(i != j){
        outputs = new int[]{j, i};
      }
    } else {
      int[] outputs = (int[])this.outputs;
      for(int v : outputs){
        if(v == i) return;
      }
      int[] newoutputs = new int[outputs.length + 1];
      System.arraycopy(outputs, 0, newoutputs, 0, outputs.length);
      newoutputs[newoutputs.length - 1] = i;
      this.outputs = newoutputs;
    }
  }

  public int[] getOutputs(){
    if(outputs == null) return EMPTY_INTS;
    else if(outputs instanceof Integer) return new int[]{((Integer)outputs).intValue()};
    else return (int[])outputs;
  } 


  // END OUTPUT SET


}
