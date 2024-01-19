package org.example;

class Task {
    String name;
    int value;
    int duration;
    int resourceRequirement;

    public Task(String name, int value, int duration, int resourceRequirement) {
        this.name = name;
        this.value = value;
        this.duration = duration;
        this.resourceRequirement = resourceRequirement;
    }
}
