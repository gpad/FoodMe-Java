package com.foodme.core;

public interface IDomainCommandExecutor {
    <T> void execute(T command);
}
