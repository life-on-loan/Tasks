package com.nurlan.loadbalancer.strategy;

import java.net.URI;
import java.util.List;

public interface LoadBalancerStrategy {
    URI get(List<URI> addresses);
}
