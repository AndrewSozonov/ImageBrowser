package ru.andreysozonov.imagebrowser.model.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "table_hits")
public class Hit {

    @PrimaryKey(autoGenerate = false)
    public int id;

    @Expose
    @SerializedName("webformatURL")
    public String webformatURL;

    @Expose
    @SerializedName("largeImageURL")
    public String largeImageURL;
}
