package com.github.gwcult.treegen.tree;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NodeList<N extends Node> extends AbstractList<N> implements Node {

    public static class Builder<V, N extends Node> extends AbstractNodeBuilder<List<V>, NodeList<N>> {
        protected AbstractNodeBuilder<V, N> childrenFactory;

        public Builder(AbstractNodeBuilder<V, N> childrenFactory) {
            this.childrenFactory = childrenFactory;
        }

        public NodeList<N> build(List<V> value) {
            return new NodeList<N>(parent, name) {
                @Override
                public N get(int i) {
                    return childrenFactory.asChild(this, String.valueOf(i)).build(value.get(i));
                }

                @Override
                public int size() {
                    return value.size();
                }
            };
        }
    }

    private final Node parent;
    private final String name;

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public String getName() {
        return name;
    }
}
