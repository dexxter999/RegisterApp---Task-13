package com.example.registerapp.screens.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.registerapp.R
import com.example.registerapp.model.RegisterFragmentViewModel
import com.example.registerapp.adapters.MainRecyclerViewAdapter
import com.example.registerapp.adapters.NestedItem
import com.example.registerapp.base.BaseFragment
import com.example.registerapp.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val mainAdapter: MainRecyclerViewAdapter = MainRecyclerViewAdapter()

    private val viewModel: RegisterFragmentViewModel by viewModels()

    private val finalMap = mutableMapOf<Int, List<String?>>()


    override fun init() {
        setUpRecycler()
        viewModel.fieldData.observe(viewLifecycleOwner) { fieldData ->
            mainAdapter.submitList(fieldData)
        }
        viewModel.parseJSON()

    }

    override fun listeners() {
        mainAdapter.onNestedChooserItemClick { item ->
            when (item) {
                is NestedItem.DatePicker -> dateDialog { item.editText.setText(it) }
                is NestedItem.GenderPicker -> genderDialog {
                    item.editText.setText(
                        it
                    )
                }

                else -> Unit
            }
        }

        binding.buttonRegister.setOnClickListener {
            checkEditTexts()
        }
    }

    private fun setUpRecycler() {
        binding.mainRecyclerView.adapter = mainAdapter
    }

    private fun dateDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth}/$selectedYear"
                onDateSelected(selectedDate)
            },
            year, month, day
        ).show()
    }

    private fun genderDialog(onGenderSelected: (String) -> Unit) {
        val genders = arrayOf("Male", "Female")
        AlertDialog.Builder(requireContext())
            .setTitle("Choose Gender")
            .setItems(genders) { dialog, gender ->
                val selectedGender = genders[gender]
                onGenderSelected(selectedGender)
                dialog.dismiss()
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .create().show()
    }

    private fun checkEditTexts() {
        var isFilled = true

        binding.mainRecyclerView.children.forEach {
            it.findViewById<RecyclerView>(R.id.nestedRecyclerView).children.forEach { childrenItem ->
                if (childrenItem is EditText) {
                    if (childrenItem.text.toString().trim().isEmpty()) {
                        isFilled = false

                        Toast.makeText(
                            requireContext(),
                            "Please fill in the ${childrenItem.hint} field.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        if (isFilled) {
            collectData()
            clearAll()
        }
    }

    private fun collectData() {
        binding.mainRecyclerView.children.forEach {
            it.findViewById<RecyclerView>(R.id.nestedRecyclerView).children.forEach { childrenItem ->
                if (childrenItem is EditText) {
                    val key = childrenItem.id
                    val valueText = childrenItem.text.toString().trim()

                    if (valueText.isNotEmpty()) {
                        finalMap[key] = listOf(valueText)
                    } else {
                        finalMap[key] = listOf(null)
                    }
                }
            }
        }
        Toast.makeText(requireContext(), "User added", Toast.LENGTH_LONG).show()
    }

    private fun clearAll() {
        binding.mainRecyclerView.children.forEach {
            it.findViewById<RecyclerView>(R.id.nestedRecyclerView).children.forEach { childrenItem ->
                if (childrenItem is EditText) {
                    childrenItem.text.clear()
                }
            }
        }
    }

}