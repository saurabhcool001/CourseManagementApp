package com.example.lma;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lma.databinding.CourseItemListBinding;
import com.example.lma.entity.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private OnItemClickListener listener;
    private ArrayList<Course> oldCourseArrayList = new ArrayList<>();

    // Abstract methods
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CourseItemListBinding itemListBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.course_item_list,
                parent,
                false);
        CourseViewHolder viewHolder = new CourseViewHolder(itemListBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = oldCourseArrayList.get(position);
        holder.courseItemListBinding.setCourse(course);
    }

    @Override
    public int getItemCount() {
        return oldCourseArrayList == null ? 0 : oldCourseArrayList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private CourseItemListBinding courseItemListBinding;

        public CourseViewHolder(CourseItemListBinding courseItemListBinding) {
            super(courseItemListBinding.getRoot());
            this.courseItemListBinding = courseItemListBinding;

            courseItemListBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();

                    if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(oldCourseArrayList.get(clickedPosition));
                    }
                }
            });
        }
    }

    // Handle the item click events
    public interface OnItemClickListener {
        public void onItemClick(Course course);
    }

    // Set the listener
    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCourseArrayList(ArrayList<Course> newCourseArrayList) {

        // notifyDataSetChanged(); /** This is not useful when many data are in list */

        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new CourseDiffCallback(oldCourseArrayList, newCourseArrayList));
        oldCourseArrayList = newCourseArrayList;
        result.dispatchUpdatesTo(CourseAdapter.this);
    }
}