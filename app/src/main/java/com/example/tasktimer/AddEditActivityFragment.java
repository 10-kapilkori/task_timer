package com.example.tasktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */

public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";

    private enum FragmentEditMode {Edit, Add}

    private FragmentEditMode mMode;

    private EditText mNameTextView;
    private EditText mDescriptionTextView;
    private EditText mSortOrderTextView;
    private Button mSaveButton;


    public AddEditActivityFragment() {
        Log.d(TAG, "AddEditActivityFragment: constructor called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);

        mNameTextView = (EditText) view.findViewById(R.id.addedit_name);
        mDescriptionTextView = (EditText) view.findViewById(R.id.addedit_description);
        mSortOrderTextView = (EditText) view.findViewById(R.id.addedit_sortOrder);
        mSaveButton = (Button) view.findViewById(R.id.addedit_save);

//        final Bundle arguments = getActivity().getIntent().getExtras(); //the line we'll be changing
        final Bundle arguments = getArguments();
        final Task task;

        if (arguments != null) {
            Log.d(TAG, "onCreateView: retreiving the data");

            task = (Task) arguments.getSerializable(Task.class.getSimpleName());
            if (task != null) {
                Log.d(TAG, "onCreateView: task details found, editing.......");
                mNameTextView.setText(task.getName());
                mDescriptionTextView.setText(task.getDescription());
                mSortOrderTextView.setText(Integer.toString(task.getSortOrder()));
                mMode = FragmentEditMode.Edit;
            } else {
                //no task, we must be adding a new task, and not editing an existing one.
                mMode = FragmentEditMode.Add;
            }
        } else {
            task = null;
            Log.d(TAG, "onCreateView: no arguments, adding new records");
            mMode = FragmentEditMode.Add;
        }
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update the database if atleast one field has changed.
                // there's no reason to hit the database unless this has happened.
                int so; // to save repeated conversions to int
                if (mSortOrderTextView.length() > 0) {
                    so = Integer.parseInt(mSortOrderTextView.getText().toString());
                } else {
                    so = 0;
                }
                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();

                switch (mMode) {
                    case Edit:
                        if (!mNameTextView.getText().toString().equals(task.getName())) {
                            values.put(TasksContract.Columns.Tasks_Name, mNameTextView.getText().toString());
                        }
                        if (!mDescriptionTextView.getText().toString().equals(task.getDescription())) {
                            values.put(TasksContract.Columns.Tasks_Description, mDescriptionTextView.getText().toString());
                        }
                        if (so != task.getSortOrder()) {
                            values.put(TasksContract.Columns.Tasks_SortOrder, so);
                        }
                        if (values.size() != 0) {
                            Log.d(TAG, "onClick: updating task");
                            contentResolver.update(TasksContract.buildTaskUri(task.getID()), values, null, null);
                        }
                        break;
                    case Add:
                        if (mNameTextView.length() > 0) {
                            Log.d(TAG, "onClick: adding new task");
                            values.put(TasksContract.Columns.Tasks_Name, mNameTextView.getText().toString());
                            values.put(TasksContract.Columns.Tasks_Description, mDescriptionTextView.getText().toString());
                            values.put(TasksContract.Columns.Tasks_SortOrder, so);
                            contentResolver.insert(TasksContract.Content_URI, values);
                        }
                        break;
                }
                Log.d(TAG, "onClick: done editing");
            }
        });
        Log.d(TAG, "onCreateView: exiting");

        return view;
    }
}
