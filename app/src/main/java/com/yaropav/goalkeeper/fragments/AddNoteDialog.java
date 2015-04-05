package com.yaropav.goalkeeper.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.yaropav.goalkeeper.R;
import com.yaropav.goalkeeper.data.Day;

/**
 * Created by Ярик on 05.04.2015.
 */
public class AddNoteDialog extends AbstractDialog implements View.OnClickListener{

    private boolean mInEditText;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_note;
    }

    @Override
    protected void configureLayout(View v) {
        ((EditText) v.findViewById(R.id.note_edittext)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mInEditText) return;
                mInEditText = true;
                if (!s.toString().equals("")) mDay.setNote(s.toString());
                mInEditText = false;
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        v.findViewById(R.id.ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
