package com.foodme;

import com.foodme.core.Policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Policies {
    List<Policy> policies = new ArrayList<>();

    public Policies() {
    }

    public void add(Collection<Policy> policies) {
        this.policies.addAll(policies);
    }

    public void start() {
        this.policies.forEach(policy -> policy.start());
    }
}


