package com.sublime.movielist.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sublime.movielist.model.MovieCast.Companion.CAST_TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
@Entity(tableName = CAST_TABLE_NAME)
data class MovieCast (
    @PrimaryKey
    @SerializedName("id") var castId: Int,
    @SerializedName("gender") var castGender: Int,
    @SerializedName("known_for_department") var castDepartment: String,
    @SerializedName("name") var castName: String,
    @SerializedName("profile_path") var castProfilePath: String,

    @Nullable
    @SerializedName("character") var castCharacter: String,
    @Nullable
    @SerializedName("job") var crewJob: String,
): Parcelable{
    companion object {
        const val CAST_TABLE_NAME = "cast_data"
    }
}
