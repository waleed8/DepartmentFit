package com.mans.fci.departmentfit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;



/**
 * Created by Developer on 05/11/2016.
 */
public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>
 {
    CourseObject[] m_courses;
    DepartmentCalculator m_deptCalculator;

     private LayoutInflater mInflator;
    public CoursesAdapter(Context context, DepartmentCalculator m_deptCalculator) {


        this.m_deptCalculator = m_deptCalculator;
        this.m_courses = m_deptCalculator.m_FCICourses;

        mInflator = LayoutInflater.from(context);
    }


     @Override
     public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View v = mInflator.inflate(R.layout.course_layout_item, parent, false);
         CourseViewHolder pvh = new CourseViewHolder(v, m_deptCalculator);
         return pvh;
     }

     @Override
     public void onBindViewHolder(CourseViewHolder holder, int position) {
         CourseObject currentCourse = m_courses[position];

         holder.setM_Course(currentCourse);
         holder.m_markerSeekBar.setProgress(currentCourse.m_UserFitness);
     }

     @Override
    public long getItemId(int position) {
        //http://stackoverflow.com/questions/36826623/how-to-stop-my-recyclerview-reuse-the-view
        return position;
    }

    @Override
    public int getItemCount() {
        return m_courses.length;
    }
    //_________________________________________
    public class CourseViewHolder extends  RecyclerView.ViewHolder
    {


        CourseObject m_Course;
        //MarkerSeekBar m_markerSeekBar;
        SeekBar m_markerSeekBar;
        DepartmentCalculator m_calculator;
        TextView m_tvCourseName, m_tvCourseLevel;

        public CourseViewHolder(View itemView, DepartmentCalculator calculator) {
            super(itemView);
            m_deptCalculator = calculator;

            m_tvCourseName = (TextView) itemView.findViewById(R.id.tvCourseName);
            m_tvCourseLevel= (TextView) itemView.findViewById(R.id.tvCourseLevel);

            //m_markerSeekBar = (MarkerSeekBar) itemView.findViewById(R.id.seek_CourseLevel);
            m_markerSeekBar = (SeekBar) itemView.findViewById(R.id.seek_CourseLevel);
            m_markerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    m_Course.m_UserFitness = i;
                    if(m_tvCourseLevel!=null)
                         m_tvCourseLevel.setText(""+i+"/10");

                    m_deptCalculator.UpdateRatings();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //NOT NEEDED
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //NOT NEEDED
                }
            });
        }

        public CourseObject getM_Course() {
            return m_Course;
        }

        public void setM_Course(CourseObject m_Course) {
            this.m_Course = m_Course;
            m_tvCourseName.setText(m_Course.CourseName);
        }

    }
}
