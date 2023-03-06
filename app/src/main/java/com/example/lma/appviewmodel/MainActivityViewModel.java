package com.example.lma.appviewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.lma.entity.Category;
import com.example.lma.entity.Course;
import com.example.lma.repository.CourseShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    // Repository
    private CourseShopRepository repository;

    private LiveData<List<Category>> allCategories;
    private LiveData<List<Course>> getCoursesOfSelectedCategory;

    // Constructor
    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        // Call the CourseShopRepository constructor
        repository = new CourseShopRepository(application);
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = repository.getCategories();
        return allCategories;
    }

    public LiveData<List<Course>> getGetCoursesOfSelectedCategory(int categoryId) {
        getCoursesOfSelectedCategory = repository.getCourses(categoryId);
        return getCoursesOfSelectedCategory;
    }

    public void addNewCourse(Course course) {
        repository.insertCourse(course);
    }

    public void updateCourse(Course course) {
        repository.updateCourse(course);
    }

    public void deleteCourse(Course course) {
        repository.deleteCourse(course);
    }
}
