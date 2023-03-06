package com.example.lma.database;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lma.dao.CategoryDAO;
import com.example.lma.dao.CourseDAO;
import com.example.lma.entity.Category;
import com.example.lma.entity.Course;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Course.class}, version = 2)
public abstract class CourseDatabase extends RoomDatabase {

    // Abstract methods
    public abstract CategoryDAO categoryDAO();
    public abstract CourseDAO courseDAO();

    // Singleton Pattern Database
    private static CourseDatabase instance;

    public static synchronized CourseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CourseDatabase.class,
                    "courses_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    // Callback
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Insert data when database is created
            InitializeData();
        }
    };

    private static void InitializeData() {
        CategoryDAO categoryDAO = instance.categoryDAO();
        CourseDAO courseDAO = instance.courseDAO();

        Log.i("Test", "inside");
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                // Category add
                Category category1 = new Category();
                category1.setCategoryName("Front End");
                category1.setCategoryDescription("Web Development Interface");

                Category category2 = new Category();
                category2.setCategoryName("Back End");
                category2.setCategoryDescription("Web Development Database");

                categoryDAO.insertCatDB(category1);
                categoryDAO.insertCatDB(category2);

                // Courses add
                Course course1 = new Course();
                course1.setCourseName("HTML");
                course1.setUnitPrice("100$");
                course1.setCategoryId(1);

                Course course2 = new Course();
                course2.setCourseName("CSS");
                course2.setUnitPrice("100$");
                course2.setCategoryId(1);

                Course course3 = new Course();
                course3.setCourseName("PHP");
                course3.setUnitPrice("400$");
                course3.setCategoryId(2);

                Course course4 = new Course();
                course4.setCourseName("AJAX");
                course4.setUnitPrice("200$");
                course4.setCategoryId(2);

                courseDAO.insertCouDB(course1);
                courseDAO.insertCouDB(course2);
                courseDAO.insertCouDB(course3);
                courseDAO.insertCouDB(course4);
            }
        });
    }
}
