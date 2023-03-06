package com.example.lma.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lma.entity.Course;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert
    void insertCouDB(Course course);

    @Update
    void updateCouDB(Course course);

    @Delete
    void deleteCouDB(Course course);

    @Query("SELECT * FROM courses_table")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM courses_table WHERE category_id==:categoryId")
    LiveData<List<Course>> getCourses(int categoryId);
}
