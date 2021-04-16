package com.dmitrysukhov.loginapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * from user WHERE login LIKE :sLogin AND password LIKE :sPassword LIMIT 1")
    User getUserByCredentials(String sLogin, String sPassword);

    @Query("SELECT * from user WHERE id LIKE :sId")
    List<User> getDataOfCurrentUser(int sId);

    @Query("SELECT * from user")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateSomething(User user);

    @Delete
    void deleteUser(User oldUser);
}
