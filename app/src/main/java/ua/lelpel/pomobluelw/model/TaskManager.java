package ua.lelpel.pomobluelw.model;

import android.util.Log;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ua.lelpel.pomobluelw.R;

/**
 * Created by bruce on 16.04.2017.
 */
public class TaskManager {
    public TaskDao getTaskDao() {
        return taskDao;
    }

    public BoardDao getBoardDao() {
        return boardDao;
    }

    private TaskDao taskDao = new TaskDao();
    private BoardDao boardDao = new BoardDao();

    private static TaskManager ourInstance = new TaskManager(new TaskDao(), new BoardDao());

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

    public void setCurrentTask(int taskIdx, int listId) {
        setCurrentTask(taskDao.read(taskIdx, listId));
        //setCurrentTask(boards.get(listId).getTaskByIdx(taskIdx));
    }

    public static TaskManager getInstance() {
        return ourInstance;
    }



    public TaskManager(TaskDao taskDao, BoardDao boardDao) {
        this.taskDao = taskDao;
        this.boardDao = boardDao;

        //Board.deleteAll(Board.class);
    }

    private TaskManager() {
        boardDao.create(new Board("Blank Board"));
    }

    public void addTask(String name, Calendar c, int listId, int priority) {
        taskDao.create(new Task(name, priority, Task.deadlineToString(c), listId));

    }

    public String getBoardName(int idx) {
        return boardDao.read(idx).toString();
    }

    public int getBoardCount() {
        return boardDao.readAll().size();
    }

    public void deleteTask(int id, int listId) {
        taskDao.delete(id, listId);
    }

    public void editTask(int id, int listId, String name, Calendar c, int priority, int newListId) {
        taskDao.update(id, listId, new Task(name, priority, Task.deadlineToString(c), newListId));
    }

    public void addBoard(String listName) {
        boardDao.create(new Board(listName));
    }

    public List<Task> getTasksInBoard(int boardId) {
        return taskDao.readFromBoard(boardId);
    }

    public void editBoard(String listName, int listId) {
        boardDao.update(listId, new Board(listName));
    }
}
