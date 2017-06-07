package ua.lelpel.pomobluelw.ui.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ua.lelpel.pomobluelw.R;
import ua.lelpel.pomobluelw.model.TaskManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBoardDialog extends DialogFragment {
    private TaskManager taskManager = TaskManager.getInstance();

    private String listName;
    private EditText listNameField;

    public AddBoardDialog() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_board, null);
        listNameField = (EditText) view.findViewById(R.id.board_name_input);

        builder.setView(view).setPositiveButton(R.string.add_task_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listName = listNameField.getText().toString();
                taskManager.addBoard(listName);
            }
        }).setNegativeButton(R.string.add_task_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().cancel();
            }
        }).setMessage(R.string.add_task_header);

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_board, container, false);
    }

}
