package org.example;

import java.util.List;

import static org.example.KnapsackScheduler.maxResources;
import static org.example.KnapsackScheduler.maxTime;

class Schedule {
    List<Task> tasks;
    int fitness;

    public Schedule(List<Task> tasks) {
        this.tasks = tasks;
        this.fitness = calculateFitness();
    }

    public int calculateFitness() {
        int totalValue = 0;
        int totalTime = 0;
        int totalResources = 0;

        for (Task task : tasks) {
            totalValue += task.value;
            totalTime += task.duration;
            totalResources += task.resourceRequirement;
        }

        if (totalTime > maxTime || totalResources > maxResources) {
            totalValue = 0;
        }

        return totalValue;
    }
}