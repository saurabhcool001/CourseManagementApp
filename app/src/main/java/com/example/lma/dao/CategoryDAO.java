package com.example.lma.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lma.entity.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void insertCatDB(Category category);

    @Update
    void updateCatDB(Category category);

    @Delete
    void deleteCatDB(Category category);

    @Query("SELECT * FROM categories_table")
    LiveData<List<Category>> getAllCategories();
}
