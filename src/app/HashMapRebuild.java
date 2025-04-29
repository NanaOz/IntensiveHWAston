package app;

import java.util.Objects;

public class HashMapRebuild {
        private String key;

    public HashMapRebuild(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return key;
    }
}
