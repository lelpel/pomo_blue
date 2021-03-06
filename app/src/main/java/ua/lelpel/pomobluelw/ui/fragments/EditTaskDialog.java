package ua.lelpel.pomobluelw.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import ua.lelpel.pomobluelw.R;
import ua.lelpel.pomobluelw.model.Task;
import ua.lelpel.pomobluelw.model.TaskManager;


public class EditTaskDialog extends DialogFragment {
    private EditText taskNameField;
    private Button deadlineTimeButton;
    private Button deadlineDateButton;

    private Spinner lists;

    private int listId;
    private int taskId;
    private int newListId;

    private static final String ARG_LIST_ID = "ARG_LIST_ID";
    private static final String ARG_TASK_ID = "ARG_ID";

    public void setListId(int listId) {
        this.newListId = listId;
    }

    private final Calendar c = Calendar.getInstance();

    //TODO: перенести формирование времени в модель
    public void setUserChosenTime(int hour, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
    }
    public void setUserChosenDate(int year, int month, int day) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
    }

    private TaskManager taskManager = TaskManager.getInstance();

    public EditTaskDialog() {
        // Required empty public constructor
    }


    public static EditTaskDialog newInstance(int taskId, int listId) {
        EditTaskDialog fragment = new EditTaskDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_LIST_ID, listId);
        args.putInt(ARG_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        taskId = getArguments().getInt(ARG_TASK_ID);
        listId = getArguments().getInt(ARG_LIST_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_add_task_dialog, null);
        taskNameField = (EditText) view.findViewById(R.id.task_name_input);

        Task t = taskManager.getTaskDao().read(taskId, listId);
        taskNameField.setText(t.getName());

        lists = (Spinner) view.findViewById(R.id.list_chooser_spinner);
        lists.setSelection(listId);

        ArrayAdapter boardsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, taskManager.getBoardDao().readAll());
        boardsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lists.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setListId((int)id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setListId(0);
            }
        });

        lists.setAdapter(boardsAdapter);
        
        builder.setView(view).setPositiveButton(R.string.add_task_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taskManager.editTask(taskId, listId, taskNameField.getText().toString(), c, 1, newListId);
                //taskManager.editTask(taskId, listId, taskNameField.getText().toString(), c, newListId);
            }
        }).setNegativeButton(R.string.add_task_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //закрываем диалог, не сохраняем изменения
                getDialog().cancel();
            }
        }).setMessage(R.string.edit_task_header);

        //отображаем время
        final TextView deadlineDateLabel = (TextView) view.findViewById(R.id.deadline_date_label);
        final TextView deadlineTimeLabel = (TextView) view.findViewById(R.id.deadline_time_label);

        deadlineDateLabel.setText(c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR));
        deadlineTimeLabel.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        //tv.setText(c.getTime().toString());

        //ВЫБОР ВРЕМЕНИ
        deadlineDateButton = (Button) view.findViewById(R.id.deadline_date_picker);
        deadlineTimeButton = (Button) view.findViewById(R.id.deadline_time_picker);

        //// TODO: 05.06.2017 контекст
        deadlineTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setUserChosenTime(hourOfDay, minute);
                        deadlineTimeLabel.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

                timePickerDialog.show();
            }
        });

        deadlineDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setUserChosenDate(year, month, dayOfMonth);
                        deadlineDateLabel.setText(c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task_dialog, container, false);
    }

}
