package com.mans.fci.departmentfit;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

/**
 * Created by Developer on 28/11/2016.
 */
public class CourseMinLevelForDeptChild  {
    public String DepartmentName;
    public float MinDegree;

    public CourseMinLevelForDeptChild(String departmentName, float minDegree) {

        DepartmentName = departmentName;
        MinDegree = minDegree;
    }
}

class CourseDeptPairViewHolder extends ChildViewHolder
{
    private TextView mIngredientTextView;
    public CourseDeptPairViewHolder(View itemView) {
        super(itemView);
        mIngredientTextView = (TextView)itemView.findViewById(R.id.courseDataDetails);
    }

    public void bind(CourseMinLevelForDeptChild courseDeptPair) {
        String message = String.format("(%s) Fit Level: %s",courseDeptPair.DepartmentName, courseDeptPair.MinDegree);
        mIngredientTextView.setText(message);
    }
}
