package com.example.newsapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class ArticlesResponse(
    var articles: List<Article>?,
    val status: String?,
    val totalResults: Int

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Article),
        parcel.readString(),
        parcel.readInt()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(articles)
        parcel.writeString(status)
        parcel.writeInt(totalResults)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticlesResponse> {
        override fun createFromParcel(parcel: Parcel): ArticlesResponse {
            return ArticlesResponse(parcel)
        }

        override fun newArray(size: Int): Array<ArticlesResponse?> {
            return arrayOfNulls(size)
        }
    }
}