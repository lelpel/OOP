package ua.lelpel.pomobluelw.model;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by bruce on 06.02.2017.
 */
public class Task implements Comparable<Task> {
    private String name;
    private Calendar c;
    private int priority;

    public Task(String name, Calendar deadline, int priority) {
        this.name = name;
        this.c = deadline;
        this.priority = priority;
    }

    public int[] getDeadline() {
        int[] deadline = new int[5];
        deadline[0] = c.get(Calendar.DAY_OF_MONTH);
        deadline[1] = c.get(Calendar.MONTH);
        deadline[2] = c.get(Calendar.YEAR);
        deadline[3] = c.get(Calendar.HOUR_OF_DAY);
        deadline[4] = c.get(Calendar.MINUTE);

        return deadline;
    }

    public Task(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Task[] getTestTasks() {
        Task[] t = new Task[4];
        t[0] = new Task("Придумать мем");
        t[1] = new Task("Допилить навдроуэр");
        t[2] = new Task("Тополиный пух");
        t[3] = new Task("Админ петух");
        return t;
    }

    public static Task getTaskFromTest(int id) {
        Task[] t = getTestTasks();
        return t[id];
    }

    @Override
    public int compareTo(@NonNull Task o) {
        if (priority != o.priority) {
            return Integer.compare(priority, o.priority);
        } else {
            return name.compareTo(o.name);
        }
    }
}
