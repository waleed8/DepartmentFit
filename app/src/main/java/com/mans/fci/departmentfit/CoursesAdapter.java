package com.mans.fci.departmentfit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.bq.markerseekbar.MarkerSeekBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 05/11/2016.
 */
public class CoursesAdapter extends ExpandableRecyclerAdapter<CoursesAdapter.CourseViewHolder,CourseDeptPairViewHolder>//RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>
 {
    CourseObject[] m_courses;
    DepartmentCalculator m_deptCalculator;

     private LayoutInflater mInflator;
    public CoursesAdapter(Context context, DepartmentCalculator m_deptCalculator) {

        super(new ArrayList<ParentListItem>( m_deptCalculator.getCourseAsParentList()));
        this.m_deptCalculator = m_deptCalculator;
        this.m_courses = m_deptCalculator.m_FCICourses;

        mInflator = LayoutInflater.from(context);
    }

    @Override
    public CourseViewHolder  onCreateParentViewHolder(ViewGroup parent) {
        View v = mInflator.inflate(R.layout.course_layout_item, parent, false);
        CourseViewHolder pvh = new CourseViewHolder(v, m_deptCalculator);
        return pvh;
    }
     @Override
     public CourseDeptPairViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
         View ingredientView = mInflator.inflate(R.layout.child_layout_course_details, childViewGroup, false);
         return new CourseDeptPairViewHolder(ingredientView);
     }
    @Override
    public void onBindParentViewHolder(CourseViewHolder holder, int position, ParentListItem parentListItem) {
        CourseObject currentCourse = m_courses[position];

        holder.setM_Course(currentCourse);
        holder.m_markerSeekBar.setProgress(currentCourse.m_UserFitness);

    }
     @Override
     public void onBindChildViewHolder(CourseDeptPairViewHolder ingredientViewHolder, int position, Object childListItem) {
         CourseMinLevelForDeptChild ingredient = (CourseMinLevelForDeptChild) childListItem;
         ingredientViewHolder.bind(ingredient);
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
    public class CourseViewHolder extends ParentViewHolder//extends RecyclerView.ViewHolder
    {


        CourseObject m_Course;
        //MarkerSeekBar m_markerSeekBar;
        SeekBar m_markerSeekBar;
        DepartmentCalculator m_calculator;
        TextView m_tvCourseName, m_tvCourseLevel;
        Button m_expandButton ;
        public CourseViewHolder(View itemView, DepartmentCalculator calculator) {
            super(itemView);
            m_deptCalculator = calculator;

            m_tvCourseName = (TextView) itemView.findViewById(R.id.tvCourseName);
            m_tvCourseLevel= (TextView) itemView.findViewById(R.id.tvCourseLevel);
            m_expandButton = (Button) itemView.findViewById(R.id.buttonExpandCollapse);

            //expand collapse
            m_expandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                    }
                }
            });
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
        @Override
        public boolean shouldItemViewClickToggleExpansion() {
            return false;
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
