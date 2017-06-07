package ua.lelpel.pomobluelw.model;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bruce on 06.02.2017.
 */
public class Board extends SugarRecord {
    public String getBoardName() {
        return boardName;
    }

    public Board() {
    }

    public Board(String boardName) {
        this.boardName = boardName;
    }


    private String boardName;

    @Override
    public String toString() {
        return boardName;
    }


    public void setName(String name) {
        this.boardName = name;
    }

}
