package com.nurlan.loadbalancer;

import com.nurlan.loadbalancer.strategy.LoadBalancerStrategy;
import com.nurlan.loadbalancer.strategy.RandomStrategy;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class LoadBalancer {
    private final LoadBalancerStrategy loadBalancerStrategy;
    private final static int ADDRESSES_LIMIT_SIZE = 10;

    private final List<URI> addresses = new LinkedList<>();

    public LoadBalancer() {
        this.loadBalancerStrategy = new RandomStrategy();
    }

    public LoadBalancer(LoadBalancerStrategy strategy) {
        this.loadBalancerStrategy = strategy;
    }

    public synchronized void register(URI uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri in null");
        }

        if (addresses.size() >= ADDRESSES_LIMIT_SIZE) {
            throw new IllegalStateException("Already reached storage limit");
        }

        if (addresses.contains(uri)) {
            return;
        }
        addresses.add(uri);
    }

    public int getSize() {
        return addresses.size();
    }

    public URI get() {
        if (addresses.isEmpty()) {
            throw new IllegalStateException("Empty storage");
        }
        return loadBalancerStrategy.get(addresses);
    }
}
