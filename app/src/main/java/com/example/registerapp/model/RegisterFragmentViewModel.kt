package com.example.registerapp.model


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registerapp.data.FieldItemSubListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegisterFragmentViewModel : ViewModel() {

    private val _fieldData = MutableLiveData<List<List<FieldItemSubListItem>>>()
    val fieldData: MutableLiveData<List<List<FieldItemSubListItem>>> get() = _fieldData

    fun parseJSON() {
        val jsonString = """
            [
              [
                {
                  "field_id":1,
                  "hint":"UserName",
                  "field_type":"input",
                  "keyboard":"text",
                  "required":false,
                  "is_active":true,
                  "icon":"https://jemala.png"
                },
                {
                  "field_id":2,
                  "hint":"Email",
                  "field_type":"input",
                  "required":true,
                  "keyboard":"text",
                  "is_active":true,
                  "icon":"https://jemala.png"
                },
                {
                  "field_id":3,
                  "hint":"phone",
                  "field_type":"input",
                  "required":true,
                  "keyboard":"number",
                  "is_active":true,
                  "icon":"https://jemala.png"
                }
              ],
              [
                {
                  "field_id":4,
                  "hint":"FullName",
                  "field_type":"input",
                  "keyboard":"text",
                  "required":true,
                  "is_active":true,
                  "icon":"https://jemala.png" },
                {
                  "field_id":14,
                  "hint":"Jemali",
                  "field_type":"input",
                  "keyboard":"text",
                  "required":false,
                  "is_active":true,
                  "icon":"https://jemala.png" },
                {
                  "field_id":89,
                  "hint":"Birthday",
                  "field_type":"chooser",
                  "required":false,
                  "is_active":true,
                  "icon":"https://jemala.png" },
                {
                  "field_id":898,
                  "hint":"Gender",
                  "field_type":"chooser",
                  "required":"false",
                  "is_active":true,
                  "icon":"https://jemala.png" }
              ]
            ]
        """.trimIndent()
        val listType = object : TypeToken<List<List<FieldItemSubListItem>>>() {}.type
        _fieldData.value = Gson().fromJson(jsonString, listType)
    }
}