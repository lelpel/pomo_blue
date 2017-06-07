package ua.lelpel.pomobluelw.model;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by bruce on 05.06.2017.
 */

public class BoardDao {

    public void create(Board board) {
        Board.save(board);
    }

    public Board read(int id) {
        return Board.findById(Board.class, id + 1);
    }

    public List<Board> readAll() {
        return Board.listAll(Board.class);
    }

    public void update(int id, Board newBoard) {
        Board editedBoard = read(id);

        editedBoard.setName(newBoard.getBoardName());

        editedBoard.update();
    }

    public boolean delete(int id) {
        Board b = read(id);

        if (b == null) {
            return false;
        } else {
            return b.delete();
        }
    }

    public int size() {
        return Board.listAll(Board.class).size();
    }
}
