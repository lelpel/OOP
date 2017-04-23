package ua.lelpel.pomobluelw.model;

import android.util.Log;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bruce on 16.04.2017.
 */
public class TaskManager {
    private static TaskManager ourInstance = new TaskManager();
    private List<Board> boards;

    private Task currentTask = new Task("No task selected", Calendar.getInstance(), 0);

    public Task getCurrentTask() {
        return currentTask;
    }

    public String getCurrentTaskName() {
        return currentTask.toString();
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public void setCurrentTaskByIndexes(int taskIdx, int listId) {
        setCurrentTask(boards.get(listId).getTaskByIdx(taskIdx));
    }

    public static TaskManager getInstance() {
        return ourInstance;
    }

    private TaskManager() {
        boards = new ArrayList<>();
        boards.add(new Board("Start Board"));
        boards.add(new Board("Second board"));
    }


    public void addTask(String name, Calendar c, int listId) {
        Log.i("ta", "NAME" + name);

        boards.get(listId).addTask(new Task(name, c, 0));
    }

    public void addTask(String name, Calendar c, int id, int listId) {
        Log.i("ta", "NAME" + name);

        boards.get(listId).addTask(new Task(name, c, 0), id);
    }

    public String[] getBoardNames() {
        String[] names = new String[boards.size()];

        for (int i = 0; i < boards.size(); i++) {
            names[i] = boards.get(i).toString();
        }
        return names;
    }

    public String[] getTaskNamesInBoard(int listId) {
        return boards.get(listId).getTaskNames();
    }

    public String getTaskNameByIndexes(int taskIdx, int listId) {
        return boards.get(listId).getTaskByIdx(taskIdx).toString();
    }

    public int[] getTaskDeadlineByIndexes(int taskIdx, int listId) {
        return boards.get(listId).getTaskByIdx(taskIdx).getDeadline();
    }
    public int getBoardCount() {
        return boards.size();
    }

    public void deleteTask(int id, int listId) {
        boards.get(listId).remove(id);
    }

    public void editTask(int id, int listId, String name, Calendar c, int newListId) {
        deleteTask(id, listId);
        addTask(name, c, id, newListId);
        //если список не меняли - кладем в то же место
//        if (listId == newListId) {
//            addTask(name, c, id, newListId);
//        } else {
//            //иначе - в конец нового списка
//            addTask(name, c, newListId);
//        }

    }
}
