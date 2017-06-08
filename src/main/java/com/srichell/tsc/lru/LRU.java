package com.srichell.tsc.lru;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by sridhar on 6/8/17.
 */

/*
 * Threadsafe implementation of LRU. The bottom of the LRU (index 0), is supposed to be the latest entry and
 * the top of the LRU (index lru.length - 1) is supposed to be the oldest(ie, waiting-to-be-evicted) entry.
 */
public class LRU <T extends AbstractKeyValueEntry<K,V>, K,V > {
    private final CopyOnWriteArrayList<T> lruEntries; // for eviction
    private final ConcurrentHashMap<K,V> hashMap; // for lookups
    private final long maxEntries; // for lookups


    public LRU(long maxEntries) {
        this.maxEntries = maxEntries;
        this.lruEntries = new CopyOnWriteArrayList<T>();
        this.hashMap = new ConcurrentHashMap<K, V>();
    }

    public CopyOnWriteArrayList<T> getLruEntries() {
        return lruEntries;
    }

    public ConcurrentHashMap<K, V> getHashMap() {
        return hashMap;
    }

    public long getMaxEntries() {
        return maxEntries;
    }

    public void push(T lruEntry) {
        // LRU push is always at the Bottom

        getLruEntries().add(getBottomIndex(), lruEntry);
        getHashMap().put(lruEntry.getKey(), lruEntry.getValue());
        pruneLruIfNecessary();
    }

    private void pruneLruIfNecessary() {
        if(getLruEntries().size() > maxEntries) {
            evictTopEntry();
        }
    }

    // Eviction always happens at the TOP of LRU
    private void evictTopEntry() {
        T lruEntry = getLruEntries().remove(getTopIndex());
        getHashMap().remove(lruEntry.getKey());
    }

    private int getBottomIndex() {
        return 0;
    }

    private int getTopIndex() {
        return getLruEntries().size() - 1;
    }

    // TO DO: Actually, a better thing to do here is to push the enty to
    //        the bottom of LRU because the entry with this KEY is the
    //        most recently used.
    public V lookup(K key) {
        return getHashMap().get(key);
    }

}
