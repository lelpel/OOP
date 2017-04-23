package ua.lelpel.pomobluelw.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bruce on 06.02.2017.
 */
public class Board {
    public String getBoardName() {
        return boardName;
    }
    private List<Task> tasks = new ArrayList<>();

    public Board(String boardName) {
        this.boardName = boardName;
        addTask(new Task("KEK", Calendar.getInstance(), 0));
    }

    private String boardName;

    public void addTask(Task t) {
        tasks.add(t);
    }
    public void addTask(Task t, int id) {
        tasks.add(id, t);
    }
    @Override
    public String toString() {
        return boardName;
    }


    public String[] getTaskNames() {
        String[] names = new String[tasks.size()];

        for (int i = 0; i < tasks.size(); i++) {
            names[i] = tasks.get(i).toString();
        }
        return names;
    }

    public Task getTaskByIdx(int idx) {
        return tasks.get(idx);
    }

    public void remove(int idx) {
        tasks.remove(idx);
    }

}
