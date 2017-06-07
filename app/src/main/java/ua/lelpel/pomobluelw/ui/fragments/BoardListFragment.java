package ua.lelpel.pomobluelw.ui.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import ua.lelpel.pomobluelw.R;
import ua.lelpel.pomobluelw.model.Board;
import ua.lelpel.pomobluelw.model.TaskManager;
import ua.lelpel.pomobluelw.ui.fragments.EditBoardDialog;

/**
 * A placeholder fragment containing a simple view.
 */
public class BoardListFragment extends ListFragment {
    ArrayAdapter<Board> a;
    TaskManager taskManager = TaskManager.getInstance();

    public BoardListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        a = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1);
        a.addAll(taskManager.getBoardDao().readAll());
        setListAdapter(a);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_board_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getUserVisibleHint()) {
            switch (item.getItemId()) {
                case R.id.menu_item_edit_board:
                    EditBoardDialog dialog = EditBoardDialog.newInstance((int)getListView().getSelectedItemId());
                    dialog.show(getActivity().getSupportFragmentManager(), "kek");
                    a.clear();
                    a.addAll(taskManager.getBoardDao().readAll());
                    a.notifyDataSetChanged();
                    break;
                case R.id.menu_item_remove_board:
                    taskManager.getBoardDao().delete((int)getListView().getSelectedItemId());
                    a.clear();
                    a.addAll(taskManager.getBoardDao().readAll());
                    a.notifyDataSetChanged();
                    //TODO: removal of boards
                    break;
            }
        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerForContextMenu(getListView());
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_board, container, false);
//    }
}
