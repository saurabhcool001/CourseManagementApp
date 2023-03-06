package com.example.lma;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.lma.appviewmodel.MainActivityViewModel;
import com.example.lma.databinding.ActivityMainBinding;
import com.example.lma.entity.Category;
import com.example.lma.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declaring Views and Variables
    // Static Variables
    private static final int ADD_COURSE_REQUEST_CODE = 1;
    private static final int EDIT_COURSE_REQUEST_CODE = 2;
    private MainActivityClickHandlers clickHandlers;
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel mainActivityViewModel;
    private Category selectedCategory;
    private ArrayList<Category> categoriesList;
    private ArrayList<Course> coursesList;
    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;
    private int selectedCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Views

        // Data binding
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // MainActivityClickHandlers class
        clickHandlers = new MainActivityClickHandlers();
        // Set the click handler
        activityMainBinding.setClickHandlers(clickHandlers);
        // View Model
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Observe the all categories
        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                // Initialize the category list
                categoriesList = (ArrayList<Category>) categories;
                for (Category category : categories) {
                    Log.i("TAG", category.getCategoryName());
                }
                // Show Spinner
                showOnSpinner();
            }
        });

        // Observe the courses of selected category
        mainActivityViewModel.getGetCoursesOfSelectedCategory(1).observe(this,
                new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        for (Course course : courses) {
                            Log.i("TAG", course.getCourseName());
                        }
                    }
                });

        // Functionalities
    }

    private void LoadCoursesArrayList(int categoryId) {
        // Observe the courses of selected category
        mainActivityViewModel.getGetCoursesOfSelectedCategory(categoryId).observe(
                this,
                new Observer<List<Course>>() {
                    @Override
                    public void onChanged(List<Course> courses) {
                        coursesList = (ArrayList<Course>) courses;
                        LoadRecyclerView();
                    }
                });
    }

    private void LoadRecyclerView() {
        // RecyclerView
        courseRecyclerView = activityMainBinding.secondaryLayout.sRecyclerView;
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        courseRecyclerView.setHasFixedSize(true);

        // Course Adapter
        courseAdapter = new CourseAdapter();
        courseAdapter.setCourseArrayList(coursesList);
        courseRecyclerView.setAdapter(courseAdapter);

        // Edit the Course
        courseAdapter.setListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                selectedCourseId = course.getCourseId();

                Intent editActivityIntent = new Intent(MainActivity.this, AddEditActivity.class);
                editActivityIntent.putExtra(AddEditActivity.COURSE_ID, selectedCourseId);
                editActivityIntent.putExtra(AddEditActivity.COURSE_NAME, course.getCourseName());
                editActivityIntent.putExtra(AddEditActivity.UNIT_PRICE, course.getUnitPrice());
                editActivityIntent.putExtra("EDIT", EDIT_COURSE_REQUEST_CODE);
                launcher.launch(editActivityIntent);
            }
        });

        // Delete the Course
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course deleteCourse = coursesList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCourse(deleteCourse);
            }
        }).attachToRecyclerView(courseRecyclerView);
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoriesList
        );

        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int selectCategoryId = selectedCategory.getCategoryId();
                    // Inserting the Course
                    //Toast.makeText(MainActivity.this, ""+result.getResultCode(), Toast.LENGTH_SHORT).show();
                    if(result.getResultCode() == ADD_COURSE_REQUEST_CODE) {
                        Intent data = result.getData();
                        Course course = new Course();
//                        course.setCourseId(0);
                        course.setCategoryId(selectCategoryId);
                        course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
                        course.setUnitPrice(data.getStringExtra(AddEditActivity.UNIT_PRICE));
                        mainActivityViewModel.addNewCourse(course);
                    }
                    // Editing the Course
                    if(result.getResultCode() == EDIT_COURSE_REQUEST_CODE) {
                        Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
                        Intent data = result.getData();
                        Course course = new Course();
                        course.setCategoryId(selectCategoryId);
                        course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
                        course.setUnitPrice(data.getStringExtra(AddEditActivity.UNIT_PRICE));
                        course.setCourseId(selectedCourseId);
                        mainActivityViewModel.updateCourse(course);
                    }
                }
            }
    );

    public class MainActivityClickHandlers {
        public void onFABClicked(View view) {

            // Creating the Course
            Intent addActivityIntent = new Intent(MainActivity.this, AddEditActivity.class);
            addActivityIntent.putExtra("ADD", ADD_COURSE_REQUEST_CODE);
            launcher.launch(addActivityIntent);
        }

        public void onSelectItem(AdapterView<?> parent, View view, int position, long id) {
            // Getting the category items
            selectedCategory = (Category) parent.getItemAtPosition(position);

            String message = "id is: " + selectedCategory.getCategoryId() +
                    " \n name is: " + selectedCategory.getCategoryName();

            Toast.makeText(MainActivity.this, " "+message, Toast.LENGTH_SHORT).show();

            LoadCoursesArrayList(selectedCategory.getCategoryId());
        }
    }
}