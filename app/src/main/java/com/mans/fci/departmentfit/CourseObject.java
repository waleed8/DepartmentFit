package com.mans.fci.departmentfit;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 28/10/2016.
 */

public class CourseObject implements ParentListItem{
    String CourseName;
    int[] MinLevelsPerDepartment;
    public int m_UserFitness;

    //https://bignerdranch.github.io/expandable-recycler-view/
    List<CourseMinLevelForDeptChild> m_ExpandableChildrend;
    public CourseObject(String courseName, int[] minLevelsPerDepartment) {
        CourseName = courseName;
        MinLevelsPerDepartment = minLevelsPerDepartment;

        m_ExpandableChildrend = new ArrayList<>();
        m_ExpandableChildrend.add(new CourseMinLevelForDeptChild("Computer Science",minLevelsPerDepartment[0]));
        m_ExpandableChildrend.add(new CourseMinLevelForDeptChild("Information Technology",minLevelsPerDepartment[1]));
        m_ExpandableChildrend.add(new CourseMinLevelForDeptChild("Information Systems",minLevelsPerDepartment[2]));
    }

    @Override
    public List<CourseMinLevelForDeptChild> getChildItemList() {
        return m_ExpandableChildrend;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
