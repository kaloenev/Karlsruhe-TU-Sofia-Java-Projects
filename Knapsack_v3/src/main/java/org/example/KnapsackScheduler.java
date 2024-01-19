package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KnapsackScheduler {
    static int maxGenerations = 20;
    static int populationSize = 100;
    static double crossoverRate = 0.5;
    static double mutationRate = 0.05;
    static int maxTime = 60;
    static int maxResources = 40;

    static int numberOfTasksPerSchedule = 8;

    static long time = System.currentTimeMillis();

    static double taskSelectionRate = 0.01;

    //line 98, 141 and 144

    static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        initializeTasks();
        List<Schedule> population = initializePopulation();

        for (int generation = 0; generation < maxGenerations; generation++) {
            List<Schedule> newPopulation = new ArrayList<>();

            while (newPopulation.size() < populationSize) {
                Schedule parent1 = selectParentTournament(population);
                Schedule parent2 = selectParentTournament(population);

                Schedule child = crossover(parent1, parent2);
                mutate(child);

                newPopulation.add(child);
            }

            population = newPopulation;
        }

        Schedule bestSchedule = getBestSchedule(population);
        System.out.println("Best Schedule:");
        printSchedule(bestSchedule);
    }

    private static void initializeTasks() {
        tasks.add(new Task("Task1", 7, 1, 1));
        tasks.add(new Task("Task2", 8, 3, 2));
        tasks.add(new Task("Task3", 15, 7, 4));
        tasks.add(new Task("Task4", 3, 5, 4));
        tasks.add(new Task("Task5", 12, 4, 3));
        tasks.add(new Task("Task6", 20, 10, 6));
        tasks.add(new Task("Task7", 10, 5, 3));
        tasks.add(new Task("Task8", 6, 8, 8));
        tasks.add(new Task("Task9", 21, 12, 4));
        tasks.add(new Task("Task10", 4, 9, 7));
        tasks.add(new Task("Task11", 12, 5, 3));
        tasks.add(new Task("Task12", 16, 7, 5));
        tasks.add(new Task("Task13", 20, 10, 6));
        tasks.add(new Task("Task14", 10, 10, 7));
        tasks.add(new Task("Task15", 6, 4, 3));
        tasks.add(new Task("Task16", 21, 9, 5));
        tasks.add(new Task("Task17", 26, 14, 9));
        tasks.add(new Task("Task18", 14, 3, 4));
        tasks.add(new Task("Task19", 16, 6, 5));
        tasks.add(new Task("Task20", 15, 14, 12));
        tasks.add(new Task("Task21", 24, 20, 15));
        tasks.add(new Task("Task22", 4, 15, 10));
        tasks.add(new Task("Task23", 13, 12, 12));
        tasks.add(new Task("Task24", 14, 11, 9));
        tasks.add(new Task("Task25", 2, 6, 5));
        tasks.add(new Task("Task26", 4, 8, 6));
        tasks.add(new Task("Task27", 5, 7, 5));
        tasks.add(new Task("Task28", 6, 2, 2));
    }

    private static List<Schedule> initializePopulation() {
        List<Schedule> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Task> randomTasks = generateRandomSchedule();
            population.add(new Schedule(randomTasks));
        }

        return population;
    }

    private static List<Task> generateRandomSchedule() {
        List<Task> randomTasks = new ArrayList<>();

        while (randomTasks.size() < numberOfTasksPerSchedule) {
            for (Task task : tasks) {
                if (Math.random() < taskSelectionRate) {
                    if (!randomTasks.contains(task)) randomTasks.add(task);
                }
                if (randomTasks.size() >= numberOfTasksPerSchedule) break;
            }
        }

        return randomTasks;
    }

    private static Schedule selectParentTournament(List<Schedule> population) {
        int index1 = new Random().nextInt(population.size());
        int index2 = new Random().nextInt(population.size());

        return (population.get(index1).fitness > population.get(index2).fitness) ?
                population.get(index1) : population.get(index2);
    }

    private static Schedule selectParentRoulette(List<Schedule> population) {
        int totalFitness = 0;
        for (Schedule schedule : population) {
            totalFitness += schedule.fitness;
        }

        double randomValue = Math.random() * totalFitness;

        double cumulativeFitness = 0;
        for (Schedule schedule : population) {
            cumulativeFitness += schedule.fitness;
            if (cumulativeFitness >= randomValue) {
                return schedule;
            }
        }

        return population.get(population.size() - 1);
    }

    private static Schedule crossover(Schedule parent1, Schedule parent2) {
        List<Task> childTasks = new ArrayList<>();

        for (int i = 0; i < parent1.tasks.size() && i < parent2.tasks.size(); i++) {
            if (Math.random() < crossoverRate && !childTasks.contains(parent1.tasks.get(i))) {
                childTasks.add(parent1.tasks.get(i));
            } else {
                if (!childTasks.contains(parent2.tasks.get(i))) {
                    childTasks.add(parent2.tasks.get(i));
                }
                else childTasks.add(parent1.tasks.get(i));
            }
        }

        return new Schedule(childTasks);
    }

    private static void mutate(Schedule schedule) {
        for (int i = 0; i < tasks.size(); i++) {
            if (Math.random() < mutationRate) {
                Task task = tasks.get(i);
                if (schedule.tasks.contains(task)) {
                    schedule.tasks.remove(task);
                } else {
                    if (!schedule.tasks.contains(task)) {
                        schedule.tasks.add(task);
                    }
                    if (schedule.calculateFitness() == 0) {
                        schedule.tasks.remove(task);
                    }
                }
            }
        }
    }

    private static Schedule getBestSchedule(List<Schedule> population) {
        Schedule bestSchedule = population.get(0);
        for (Schedule schedule : population) {
            if (schedule.fitness > bestSchedule.fitness) {
                bestSchedule = schedule;
            }
        }
        return bestSchedule;
    }

    private static void printSchedule(Schedule schedule) {
        for (Task task : schedule.tasks) {
            System.out.println("Task: " + task.name +
                    ", Value: " + task.value +
                    ", Duration: " + task.duration +
                    ", Resource Requirement: " + task.resourceRequirement);
        }
        System.out.println("Total Value: " + schedule.fitness);
        System.out.println("It took " + (System.currentTimeMillis() - time) + "ms to calculate");
    }
}
