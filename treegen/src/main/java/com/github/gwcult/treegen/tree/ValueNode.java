package com.github.gwcult.treegen.tree;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ValueNode<V> implements Node {
    private final Node parent;
    private final String name;
    private final V value;

    public static class Builder<V> extends AbstractNodeBuilder<V, ValueNode<V>> {
        public ValueNode<V> build(V value) {
            return new ValueNode<>(parent, name, value);
        }
    }

    public String getName() {
        return name;
    }

    public Node getParent() {
        return parent;
    }

    public V getValue() {
        return value;
    }
}
