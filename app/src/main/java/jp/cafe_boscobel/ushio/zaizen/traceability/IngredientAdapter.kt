package jp.cafe_boscobel.ushio.zaizen.traceability
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class IngredientAdapter(context: Context): BaseAdapter() {
    private val mLayoutInflater: LayoutInflater
    var mIngredientList = mutableListOf<Ingredient>()

    init {
        this.mLayoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return mIngredientList.size
    }

    override fun getItem(position: Int): Any {
        return mIngredientList[position]
    }

    override fun getItemId(position: Int): Long {
        return mIngredientList[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view : View = convertView ?: mLayoutInflater.inflate(R.layout.activity_ingredientadapter, null)

        val textView1 = view.findViewById<TextView>(R.id.text1)
        val textView2 = view.findViewById<TextView>(R.id.text2)
        val textView3 = view.findViewById<TextView>(R.id.text3)

        textView1.text = mIngredientList[position].name
        textView2.text = mIngredientList[position].dimension.toString()
        textView3.text = mIngredientList[position].shopname.toString()

        return view
    }
}