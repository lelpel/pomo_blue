package ua.lelpel.pomobluelw.ui.preferences;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

/**
 * Created by bruce on 06.06.2017.
 */

public class TimePreference extends DialogPreference {
    private int minutes;
    private NumberPicker minutePicker = null;


    public TimePreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);

        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    @Override
    protected View onCreateDialogView() {
        minutePicker = new NumberPicker(getContext());

        return minutePicker;
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        minutePicker.setMaxValue(45);
        minutePicker.setMinValue(15);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

            if (positiveResult) {
                minutes = minutePicker.getValue();

                String time = String.valueOf(minutes);

                if (callChangeListener(time)) {
                    persistString(time);
                }
        }
    }
//
//    @Override
//    protected Object onGetDefaultValue(TypedArray a, int index) {
//        return (a.getString(index));
//    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String minutes = null;

        if (restoreValue) {
            if (defaultValue == null) {
                minutes = getPersistedString("00:00");
            } else {
                minutes = getPersistedString(defaultValue.toString());
            }
        } else {
            minutes = defaultValue.toString();
        }

        this.minutes = Integer.parseInt(minutes);
    }
}
