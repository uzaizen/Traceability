package jp.cafe_boscobel.ushio.zaizen.traceability
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_ingredience.*
import kotlinx.coroutines.*
import java.util.*

var IngredientData:MutableList<Material> = mutableListOf()

class Fragment_Ingredience : Fragment() {

    lateinit var mIngredientAdapter:IngredientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragmentのレイアウトをインフレート
        val view = inflater.inflate(R.layout.fragment_ingredience, container, false)

        // ListViewの初期化
        val listView1 = view.findViewById<ListView>(R.id.listView1)


        mIngredientAdapter = IngredientAdapter(requireContext())
        listView1.adapter = mIngredientAdapter

        listView1.setOnItemClickListener { parent, _, position, _ ->
            Log.d("uztest", "position=${position.toString()}")
            val ingredient = parent.adapter.getItem(position) as Ingredient
            Log.d("uztest", "tapped " + ingredient.name.toString())
        }

//        fab.setOnClickListener { View -> Log.d("uztest", "clicked") }

        Log.d("uztest", "Fragment onCreateView done")
        return view
    }

    // https://qiita.com/takasshii/items/8f7e1cb224acfacee528　参照する

    override fun onStart() {
        super.onStart()

        //Copy IngredientData to Adapter.List
        mIngredientAdapter.mIngredientList = IngredientData.toMutableList()

        mIngredientAdapter.notifyDataSetChanged()

        Log.d("uztest", "Fragment on Start Done")
        return

    }

}
