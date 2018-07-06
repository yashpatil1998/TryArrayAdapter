package com.example.yashpatil.tryarrayadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yash Patil on 18-06-2018.
 */

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    public EmployeeAdapter(Context context, ArrayList<Employee> words) {
        super(context, 0, words);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        {
            // Check if an existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }

            // Get the {@link Word} object located at this position in the list
//            Word currentWord = getItem(position);
            Employee currentEmployee = getItem(position);
            // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
            TextView eId = (TextView) listItemView.findViewById(R.id.employee_id);
            // Get the Miwok translation from the currentWord object and set this text on
            // the Miwok TextView.
//            miwokTextView.setText(currentWord.getMiwokTranslation());
            eId.setText(currentEmployee.getId());
            // Find the TextView in the list_item.xml layout with the ID default_text_view.
            TextView eName = (TextView) listItemView.findViewById(R.id.employee_name);
            // Get the default translation from the currentWord object and set this text on
            // the default TextView.
//            defaultTextView.setText(currentWord.getDefaultTranslation());
            eName.setText(currentEmployee.getName());
//            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
//            if(currentWord.hasImage())
//                imageView.setImageResource(currentWord.getImageResourceId());
//            else
//                imageView.setVisibility(View.GONE);

            // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
            // the ListView.
            return listItemView;
        }
    }


}
