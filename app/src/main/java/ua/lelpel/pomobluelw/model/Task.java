package ua.lelpel.pomobluelw.model;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.util.Calendar;

/**
 * Created by bruce on 06.02.2017.
 */
public class Task extends SugarRecord implements Comparable<Task> {
    private String name;

    @Ignore
    private Calendar c;

    private int priority;
    private String deadline;
    private int boardId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public Task(String name, int priority, String deadline, int boardId) {
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.boardId = boardId;
    }

    public Task() {
    }

    @Deprecated
    public Task(String name, Calendar deadline, int priority) {
        this.name = name;
        this.c = deadline;
        this.priority = priority;
    }

    public String getDeadline() {
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

    public static String deadlineToString(Calendar d) {
        return d.get(Calendar.HOUR_OF_DAY) + ":" + d.get(Calendar.MINUTE) + " ; " + d.get(Calendar.DAY_OF_MONTH) + "." + d.get(Calendar.MONTH) + "." + d.get(Calendar.YEAR);
    }

    public String getDeadlineTime() {
        return deadline.split(";")[0];
    }


    public String getDeadlineDate() {
        return deadline.split(";")[1];
    }
}
