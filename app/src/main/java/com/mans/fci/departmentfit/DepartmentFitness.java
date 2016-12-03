package com.mans.fci.departmentfit;

/**
 * Created by Developer on 05/11/2016.
 */
public class DepartmentFitness {
    public String m_DepartmentName;
    public float m_SatisfiedCoursesCount;
    public float m_departmentFitness;
    public int m_CountOfMainCourses;
    static  final int MAIN_COURSE_THRESHOLD =6;

    public DepartmentFitness(String m_DepartmentName ) {
        this.m_DepartmentName = m_DepartmentName;
    }

    public void UpdateFitness() {
        if(m_CountOfMainCourses==0)//no division by zero
            m_departmentFitness = 0;
       else
            m_departmentFitness =100* m_SatisfiedCoursesCount / (float) m_CountOfMainCourses;
    }
}
