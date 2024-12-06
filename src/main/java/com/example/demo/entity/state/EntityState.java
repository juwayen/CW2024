package com.example.demo.entity.state;

import com.example.demo.entity.Entity;

public interface EntityState {
    void enter(Entity entity);
    void exit(Entity entity);
}
