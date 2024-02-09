package jp.cafe_boscobel.ushio.zaizen.traceability
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_ingredienceinput.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class Fragment_InputIngredience  : Fragment() {
    private var selectedDate: Date = Date()
    private var selectedExpiration: Date = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredienceinput, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatePicker(ingredientDateEditText) { date ->
            selectedDate = date
        }

        setupDatePicker(ingredientExpirationEditText) { expiration ->
            selectedExpiration = expiration
        }

        addButton.setOnClickListener {
            addIngredient()
        }
    }

    private fun setupDatePicker(editText: EditText, onDateSet: (Date) -> Unit) {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                val selectedDate = calendar.time
                editText.setText(SimpleDateFormat("yy-MM-dd", Locale.JAPAN).format(selectedDate))
                onDateSet(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        editText.setOnClickListener {
            datePickerDialog.show()
        }
    }

    private fun addIngredient() {
        val id = "123" // あなたのIDを設定
        val name = ingredientNameEditText.text.toString()
        val amount = ingredientAmountEditText.text.toString().toInt()
        val dimension = ingredientDimensionEditText.text.toString()
        val shopName = ingredientShopNameEditText.text.toString()
        val comment = ingredientCommentEditText.text.toString()
        val idRelList = mutableListOf<String>()

        CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.IO) {
                AddIngredience = Ingredient(
                id, selectedDate, name, amount, dimension, shopName, selectedExpiration, comment, idRelList
            )
                AddIngredience.adddata()
                // バックグラウンドでの非同期処理

            }

        }
    }

}