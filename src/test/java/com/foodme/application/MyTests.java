package com.foodme.application;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MyTests {
    @Test
    public void blah() {
        assertThat(1, is(2));
    }

}
