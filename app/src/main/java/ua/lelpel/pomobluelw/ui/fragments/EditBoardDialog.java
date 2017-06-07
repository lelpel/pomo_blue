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

import java.util.Calendar;

import ua.lelpel.pomobluelw.R;
import ua.lelpel.pomobluelw.model.TaskManager;

/**
 * A simple {@link DialogFragment} to edit board.
 * Use the {@link EditBoardDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditBoardDialog extends DialogFragment {
    private static final String ARG_LIST_ID = "list_id";
    private TaskManager taskManager = TaskManager.getInstance();
    private final Calendar c = Calendar.getInstance();

    private int listId;

    private String listName;
    private EditText listNameField;

    public EditBoardDialog() {
        // Required empty public constructor
    }

    /**
     * @param listId Номер доски.
     * @return A new instance of fragment EditBoardDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static EditBoardDialog newInstance(int listId) {
        EditBoardDialog fragment = new EditBoardDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_LIST_ID, listId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listId = getArguments().getInt(ARG_LIST_ID);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_board_dialog, null);
        listNameField = (EditText) view.findViewById(R.id.board_name_input);

        builder.setView(view).setPositiveButton(R.string.edit_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listName = listNameField.getText().toString();
                taskManager.editBoard(listName, listId);
            }
        }).setNegativeButton(R.string.add_task_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDialog().cancel();
            }
        }).setMessage(R.string.edit_board_header);

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_board_dialog, container, false);
    }

}
