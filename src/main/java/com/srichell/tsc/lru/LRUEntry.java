package com.srichell.tsc.lru;

/**
 * Created by sridhar on 6/8/17.
 */
public class LRUEntry extends AbstractKeyValueEntry<String, String> {
    private String key;
    private String value;

    public LRUEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setKey(String key) {
        this.key = key;

    }

    @Override
    public void setValue(String value) {
        this.value = value;

    }
}
