package jp.cafe_boscobel.ushio.zaizen.traceability
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat


class IngredientAdapter(context: Context): BaseAdapter() {
    private val mLayoutInflater: LayoutInflater
    var mIngredientList = mutableListOf<Material>()

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
        val textView4 = view.findViewById<TextView>(R.id.text4)
        val textView5 = view.findViewById<TextView>(R.id.text5)
        val textView6 = view.findViewById<TextView>(R.id.text6)
        val textView7 = view.findViewById<TextView>(R.id.text7)
        val textView8 = view.findViewById<TextView>(R.id.text8)

        textView1.text = mIngredientList[position].id

        val dateFormat = SimpleDateFormat("yy-MM-dd")
        var formattedDate = dateFormat.format(mIngredientList[position].date)
        textView2.text = formattedDate

        textView3.text = mIngredientList[position].name
        textView4.text = mIngredientList[position].amount.toString()
        textView5.text = mIngredientList[position].dimension
        textView6.text = mIngredientList[position].shopname

        formattedDate = dateFormat.format((mIngredientList[position].expiration))
        textView7.text = formattedDate

        textView8.text = mIngredientList[position].comment

        return view
    }
}