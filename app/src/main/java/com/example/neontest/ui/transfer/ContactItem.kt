package com.example.neontest.ui.transfer

import android.os.Parcelable
import android.view.View
import com.example.neontest.data.model.Contact
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ContactItem(
    val id: Long,
    val name: String,
    val phone: String,
    val avatarUrl: String,
    val transferValue: Float = 0f,
    @IgnoredOnParcel var click: @RawValue View.OnClickListener? = null
) : Parcelable

fun Contact.toContactItem(
    transferValue: Float = 0f
) =
    ContactItem(id, name, phone, avatarUrl, transferValue)