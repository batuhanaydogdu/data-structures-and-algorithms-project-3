/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsaproje3;

import java.util.LinkedList;

/**
 *
 * @author win7
 */
public class Hash<Key, Value> {

    private int n;
    private int m;
    private Key[] keys;
    private Value[][] vals;
    

    public Hash(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[][]) new Object[m][m];
    }

    public Value[][] getVals() {
        return vals;
    }
    

    public int size() {

        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) {
            return false;
        }
        return get(key) != null;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    private void resize(int capacity) {
        Hash<Key, Value> temp = new Hash(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i][0]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m = temp.m;
    }

    public void put(Key key, Value val) {
        if (key == null) {
            return;
        }

        if (val == null) {
            delete(key);
            return;
        }

        if (n >= m / 2) {
            resize(2 * m);
        }

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                addToVals(i, val);
                return;
            }
        }
        keys[i] = key;
        addToVals(i, val);
        n++;
    }

    private void addToVals(int i, Value val) {
        int b = 0;
        while (true) {
            if (vals[i][b] == null) {
                vals[i][b] = val;
                break;
            }
            b++;

        }
    }

    public Value get(Key key) {
        if (key == null) {
            return null;
        }
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                return vals[i][0];
            }
        }
        return null;
    }
    public Value get2(Key key){
        if (key == null) {
            return null;
        }
        return vals[hash(key)][0];
        
    }
    public Value[] getAsArray(Key key){
       
        if (key == null) {
            return null;
        }
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                
                Value[] valreturn=(Value[])new Object[vals[i].length];
                for(int b=0;b<vals[i].length;b++){
                    valreturn[b]=vals[i][b];
                    
                }
                return valreturn;
                
            }
        }
        return null;
        
    }

    @Override
    public String toString() {
        String a=" ";
        for(int i=0;i<this.vals.length;i++){
            if(this.vals[i][0]!=null)
            a=a+this.vals[i][0]+"\n";
        }
        return a;
    }
    

    public void delete(Key key) {
        if (key == null) {
            return;
        }

        if (!contains(key)) {
            return;
        }

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key keyToRehash = keys[i];
            Value valToRehash = vals[i][0];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m / 8) {
            resize(m / 2);
        }

    }

}
