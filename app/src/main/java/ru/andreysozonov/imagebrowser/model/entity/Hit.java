package ru.andreysozonov.imagebrowser.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hit {

    @Expose
    @SerializedName("webformatURL")
    public String webformatURL;

    @Expose
    @SerializedName("largeImageURL")
    public String largeImageURL;
}
