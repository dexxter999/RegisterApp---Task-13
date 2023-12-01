package com.example.registerapp.data

import com.google.gson.annotations.SerializedName

data class FieldItemSubListItem(
    @SerializedName("field_id")
    val fieldId: Int,
    @SerializedName("field_type")
    val fieldType: String,
    val hint: String,
    val icon: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    val keyboard: String,
    val required: Boolean
)