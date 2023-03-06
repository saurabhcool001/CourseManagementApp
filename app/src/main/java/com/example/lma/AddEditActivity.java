package com.example.lma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lma.databinding.ActivityAddEditBinding;
import com.example.lma.entity.Course;

public class AddEditActivity extends AppCompatActivity {

    // Declaring Views and Variables
    // static final variables
    public static final String COURSE_ID = "courseId";
    public static final String COURSE_NAME = "courseName";
    public static final String UNIT_PRICE = "unitPrice";
    private int REQUEST_CODE;

    private AddEditClickHandlers addEditClickHandlers;
    private Course course;
    private CourseAdapter courseAdapter;
    private ActivityAddEditBinding activityAddEditBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // Initializing the Views and Variables
        course = new Course();
        // Activity binding
        activityAddEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit);
        activityAddEditBinding.setCourse(course);
        // AddEditClickHandlers
        addEditClickHandlers = new AddEditClickHandlers(this);
        activityAddEditBinding.setClickHandler(addEditClickHandlers);

        Intent data = getIntent();
        if (data.hasExtra(COURSE_ID)) {
            // RecyclerView is clicked
            setTitle("Edit Course");
            course.setCourseName(data.getStringExtra(COURSE_NAME));
            course.setUnitPrice(data.getStringExtra(UNIT_PRICE));
            REQUEST_CODE = 2;
        } else {
            // FAB is clicked
            REQUEST_CODE = 1;
            setTitle("Create New Course");
        }
    }

    public class AddEditClickHandlers {
        private Context context;

        // Constructor
        public AddEditClickHandlers(Context context) {
            this.context = context;
        }

        // Submit button
        public void onSubmitBtnClicked(View view) {
            if (course.getCourseName() == null) {
                Toast.makeText(context, "Course name is empty!", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent();
                i.putExtra(COURSE_NAME, course.getCourseName());
                i.putExtra(UNIT_PRICE, course.getUnitPrice());
                setResult(REQUEST_CODE, i);
                finish();
            }
        }
    }
}