package com.srichell.tsc.lru;

/**
 * Created by sridhar on 6/8/17.
 */
public abstract class AbstractKeyValueEntry<K,V> {
    public abstract K getKey();
    public abstract V getValue();
    public abstract void setKey(K key);
    public abstract void setValue(V value);
}
