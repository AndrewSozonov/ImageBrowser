package ru.andreysozonov.imagebrowser.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import ru.andreysozonov.imagebrowser.model.entity.Hit;

@Dao
public interface HitDao {

    @Query("SELECT * FROM table_hits")
    Single<List<Hit>> getAll();

    @Query("SELECT * FROM table_hits WHERE id = :id")
    Single<List<Hit>> getAllById(int id);

    @Query("SELECT * FROM table_hits WHERE id = :id")
    Single<Hit> getImageById(int id);

    @Insert
    Single<Long> insert(Hit hit);

    @Insert
    Single<List<Long>> insertList(List<Hit> hits);

    @Delete
    Single<Integer> delete(Hit hit);

    @Update
    Single<Integer> update(Hit hit);

    @Update
    Single<Integer> updateList(List<Hit> fruits);


}