package ua.lelpel.pomobluelw.model;

import android.util.Log;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by bruce on 05.06.2017.
 */

public class TaskDao {
    public void create(Task task) {
        Task.save(task);
    }

    public Task read(int id, int boardId) {
        return Select.from(Task.class).where(Condition.prop("board_Id").eq(boardId)).list().get(id);
    }

    public List<Task> readFromBoard(int boardId) {
        return Select.from(Task.class).where(Condition.prop("board_Id").eq(boardId)).list();
    }

    public void update(int id, int boardId, Task newTask) {
        Task editedTask = read(id, boardId);

        editedTask.setName(newTask.getName());
        editedTask.setDeadline(newTask.getDeadline());
        editedTask.setPriority(newTask.getPriority());
        editedTask.setBoardId(newTask.getBoardId());

        editedTask.update();
    }

    public boolean delete(int id, int boardId) {
        Log.i("DELETED_TASK", id + " " + boardId);
        return read(id, boardId).delete();
    }
}
