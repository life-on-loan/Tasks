package com.nurlan.loadbalancer.strategy;

import java.net.URI;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements LoadBalancerStrategy {
    @Override
    public URI get(List<URI> addresses) {
        var random = new Random();
        var index = random.nextInt(addresses.size());
        return addresses.get(index);
    }
}
