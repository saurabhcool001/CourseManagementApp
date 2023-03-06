package com.example.lma.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.lma.dao.CategoryDAO;
import com.example.lma.dao.CourseDAO;
import com.example.lma.database.CourseDatabase;
import com.example.lma.entity.Category;
import com.example.lma.entity.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseShopRepository {

    // DAO instances
    private CategoryDAO categoryDAO;
    private CourseDAO courseDAO;

    // Store all categories and courses
    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> courses;

    // Constructor
    public CourseShopRepository(Application application) {
        CourseDatabase courseDatabase = CourseDatabase.getInstance(application);
        categoryDAO = courseDatabase.categoryDAO();
        courseDAO = courseDatabase.courseDAO();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<Course>> getCourses(int categoryId) {
        return courseDAO.getCourses(categoryId);
    }

    public void insertCategory(Category category) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Inserting Categories
                categoryDAO.insertCatDB(category);

                // Do after background execution is done - post execution
            }
        });
    }

    public void insertCourse(Course course) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Inserting Courses
                courseDAO.insertCouDB(course);

                // Do after background execution is done - post execution
            }
        });
    }

    public void deleteCategory(Category category) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Deleting Categories
                categoryDAO.deleteCatDB(category);

                // Do after background execution is done - post execution
            }
        });
    }

    public void deleteCourse(Course course) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Deleting Courses
                courseDAO.deleteCouDB(course);

                // Do after background execution is done - post execution
            }
        });
    }

    public void updateCategory(Category category) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Updating Categories
                categoryDAO.updateCatDB(category);

                // Do after background execution is done - post execution
            }
        });
    }

    public void updateCourse(Course course) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Updating Courses
                courseDAO.updateCouDB(course);

                // Do after background execution is done - post execution
            }
        });
    }
}
