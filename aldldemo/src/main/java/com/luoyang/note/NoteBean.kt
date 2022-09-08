package com.luoyang.note

import android.os.Parcel
import android.os.Parcelable

/**
 * 笔记
 *
 * @author luoyang
 * @date 2022/7/9
 */
data class NoteBean(var id: Int, var number: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(number)
    }
    /**
     * 当使用inout时,必须重写readFromParcel
     */
    fun readFromParcel(parcel: Parcel) {
        this.id = parcel.readInt()
        this.number = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteBean> {
        override fun createFromParcel(parcel: Parcel): NoteBean {
            return NoteBean(parcel)
        }

        override fun newArray(size: Int): Array<NoteBean?> {
            return arrayOfNulls(size)
        }
    }

}
