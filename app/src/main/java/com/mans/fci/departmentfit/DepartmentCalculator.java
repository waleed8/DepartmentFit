package com.mans.fci.departmentfit;



/**
 * Created by Developer on 28/10/2016.
 */
public class DepartmentCalculator {
    public final CourseObject[] m_FCICourses = {
            new CourseObject(  "Programming",new int[] {8,6,4}) ,//programming
            new CourseObject(  "Math"    ,new int[] {7,7,4}) ,
            new CourseObject(  "Physics"    ,new int[] {6,7,2}),
            new CourseObject(  "Digital Circuits"    ,new int[] {6,8,2}),
            new CourseObject(  "Statistics and Probability"    ,new int[] {5,6,7}),
            new CourseObject(  "Operating Systems"    ,new int[] {8,5,3}),
            new CourseObject(  "Data Strucutres and Algorithms"    ,new int[] {8,6,5}),
            new CourseObject(  "Computer Architecture"    ,new int[] {8,6,3}),
            new CourseObject(  "Web Programming"    ,new int[] {5,7,6}),
            new CourseObject(  "Database"    ,new int[] {4,7,8}),
            new CourseObject(  "Communications"    ,new int[] {6,8,4}),
            new CourseObject(  "Graphics (Programming)"    ,new int[] {7,7,4}),
            new CourseObject(  "Graphics + Web Design (Art)"    ,new int[] {4,6,7}),
            new CourseObject(  "Software Life Cycle, UML, analysis"    ,new int[] {6,7,8})
//            new CourseObject("Programming",new int[] {8,8,4}) ,//programming
//            new CourseObject(  "Math"    ,new int[] {7,8,4}) ,
//            new CourseObject(  "Physics"    ,new int[] {6,8,2}),
//            new CourseObject(  "Digital Circuits"    ,new int[] {6,8,2}),
//            new CourseObject(  "Statistics and Probability"    ,new int[] {5,7,7}),
//            new CourseObject(  "Operating Systems"    ,new int[] {8,6,3}),
//            new CourseObject(  "Data Strucutres and Algorithms"    ,new int[] {8,8,5}),
//            new CourseObject(  "Computer Architecture"    ,new int[] {8,8,3}),
//            new CourseObject(  "Web Programming"    ,new int[] {5,8,7}),
//            new CourseObject(  "Database"    ,new int[] {4,8,8}),
//            new CourseObject(  "Communications"    ,new int[] {7,8,4}),
//            new CourseObject(  "Graphics (Programming)"    ,new int[] {7,8,4}),
//            new CourseObject(  "Graphics + Web Design (Art)"    ,new int[] {4,8,8}),
//            new CourseObject(  "Software Life Cycle, UML, analysis"    ,new int[] {6,7,8})
    };

    public final DepartmentFitness[] m_departments = new DepartmentFitness[]{
            new DepartmentFitness("Computer Science"),
            new DepartmentFitness("Information Technology"),
            new DepartmentFitness("Information Systems")
    };
    private MainActivity m_mainActivity;
    public DepartmentCalculator(MainActivity mainActivity) {
        DepartmentFitness tempDept;
        m_mainActivity = mainActivity;

        for (int deptIndex = 0; deptIndex< m_departments.length; deptIndex++)
        {
            tempDept = m_departments[deptIndex];
            tempDept.m_CountOfMainCourses =0;
            for (CourseObject course: m_FCICourses)
            {
                if(course.MinLevelsPerDepartment[deptIndex]>=DepartmentFitness.MAIN_COURSE_THRESHOLD)
                {
                    tempDept.m_CountOfMainCourses++;
                }
            }
        }
    }

    public void UpdateRatings() {
        DepartmentFitness tempDept;
        int tempDeptThreshold;

        //initialize depts measures
        for (DepartmentFitness dept: m_departments) {
            dept.m_SatisfiedCoursesCount = 0;
            dept.m_departmentFitness=0;
        }

        //collect measures

       for (int deptIndex = 0; deptIndex< m_departments.length; deptIndex++)
       {
           tempDept = m_departments[deptIndex];
           for (CourseObject course: m_FCICourses)
           {
               tempDeptThreshold  = course.MinLevelsPerDepartment[deptIndex];

               if(course.m_UserFitness>= tempDeptThreshold && tempDeptThreshold>= DepartmentFitness.MAIN_COURSE_THRESHOLD)
               //if(course.m_UserFitness>= tempDeptThreshold )
               {
                   tempDept.m_SatisfiedCoursesCount++;

               }
               else
               {
                   final float maxWeightEffect = .5f;
                   float weightFactor = Math.min( tempDeptThreshold/DepartmentFitness.MAIN_COURSE_THRESHOLD, maxWeightEffect);
                   float maxDividerThreshold = tempDeptThreshold;
                   float deltaImpact = course.m_UserFitness/maxDividerThreshold;
                   if(deltaImpact> maxWeightEffect)//clip max impact
                       deltaImpact = maxWeightEffect;
                   tempDept.m_SatisfiedCoursesCount+= weightFactor * deltaImpact;
               }
               //tempDept.m_departmentFitness+= (course.m_UserFitness-tempDeptThreshold)/(float)tempDeptThreshold;


           }
           //int totalRatings = m_FCICourses.length * 10;
          // tempDept.m_departmentFitness/= totalRatings;
           tempDept.UpdateFitness();
        }
        //TODO Update UI
        m_mainActivity.UpdateCirclarProgressBars();
    }
    //____________________________________

}
