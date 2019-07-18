package com.dragovorn.ircbot.api.util;

public class Pair<F, S> {

    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pair)) {
            return false;
        }

        Pair pair = (Pair) object;

        return pair.first.equals(this.first) && pair.second.equals(this.second);
    }

    @Override
    public int hashCode() {
        return this.first.hashCode() + this.second.hashCode();
    }
}
