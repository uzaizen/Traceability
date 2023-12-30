package jp.cafe_boscobel.ushio.zaizen.traceability

// https://qiita.com/Kaito-Dogi/items/b2da06db10a95fef84b3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SnapshotMetadata
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.DiskLruCache
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.coroutines.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

private var snapshotListener : ListenerRegistration? = null
lateinit var db:FirebaseFirestore
lateinit var mIngredientAdapter:IngredientAdapter
class MainActivity : AppCompatActivity() {

//    private lateinit var mTaskAdapter: TaskAdapter
//    private lateinit var mIngredientAdapter: IngredientAdapter
//    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fab.setOnClickListener { view ->
            reloadListView1()
        }




        // Firestoreをインスタンス化
        db = FirebaseFirestore.getInstance()

//        mTaskAdapter = TaskAdapter(this)
        mIngredientAdapter = IngredientAdapter(this)

        listView1.setOnItemClickListener { parent, _, position, _ ->
            Log.d("uztest", "position=${position.toString()}")
            val ingredient = parent.adapter.getItem(position) as Ingredient
            Log.d("uztest", "tapped " + ingredient.name.toString())
        }


/*
        var ingredient = Ingredient("005", Date(), "name5", 5, "g", "amazon", "", mutableListOf("001","002","004"))

        Log.d("uztest","write start")
        db.collection("ingredient")
                .document(ingredient.id)
                .set(ingredient)
                .addOnSuccessListener { documentReference ->
//                    Log.d("uztest", "DocumentSnapshot added with ID: ${documentReference.toString()}")
                    Log.d("uztest","write done")
                }
                .addOnFailureListener { e ->
//                    Log.d("uztest", "Error adding document", e)
                    Log.d("uztest","write failed")
                }
        Log.d("uztest","write process done")

 */


//        loaddata1()
        val ingredient=Ingredient("000",Date(),"name0",0,"g","dummy","", mutableListOf(""))
        ingredient.readdata()
        Thread.sleep(5000)
        reloadListView1()

        /*
        /* 指定したidでデータベースに書き込み */
         Log.d("uztest", "DB Write")

        var task = Task()
        task.id  = "111"
        task.name = "name"
        task.date = Date()
        task.amount = 0
        task.comment = ""
        task.dimension = "g"
        task.shopname = "marunaka"

        db.collection("task")
            .document(task.id)
                .set(task)
                .addOnSuccessListener { documentReference ->
                    Log.d("uztest", "DocumentSnapshot added with ID: ${documentReference.toString()}")
                }
                .addOnFailureListener { e ->
                    Log.d("uztest", "Error adding document", e)
                }

        Log.d("uztest", "Done DB write")

        /* 指定したデータの読み出し */
        var key:String = "905"
        var GData0: String //id:key
        var GData1: String //name
        var GData2: String //amount

        db.collection("task")
                .whereEqualTo("id", key)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result){
                        Log.d("uztest", "${document.id} => ${document.data}")
                        GData0 = document.data["id"].toString()
                        GData1= document.data["name"].toString()
                        GData2 = document.data["amount"].toString()
                        Log.d("uztest", GData0+" "+GData1+" "+GData2)


/*                        val map = document.data as Map<String, String>
                        val date = map["date"]?: ""
                        val name = map["name"]?:""
                        val amount = map["amount"]?: ""
                        val shopname = map["shopname"]?:""
                        val comment = map["comment"]?:""
                        Log.d("uztest", "date=${date.toString()} name=${name.toString()} amount=${amount.toString()} shopname=${shopname.toString()} comment=${comment.toString()}")

 */
                    }
                }
        key="111"
        task.id = key
        task.date = Date()
        task.amount = 1234
        task.comment = ""
        task.dimension = "g"
        task.shopname = "updated905shopname"
        task.name = "updated905name"

        /* 指定したidのデータを上書き */
        db.collection("task").document( key)
            .set(task)
            .addOnSuccessListener { documentReference ->
                Log.d("uztest", "DocumentSnapshot updated with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.d("uztest", "Error updating document", e)
            }

            .addOnFailureListener { exception ->
                    Log.d("uztest", "get failed with ", exception)


                }


 */

    }

    /*
    private fun loaddata0(){
        db.collection("task")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        var task = Task()

                        task.id = document.data["id"].toString()
                        task.name = document.data["name"].toString()
                        task.date = document.data["date"] as? Date
                        task.amount = document.data["amount"] as? Int
                        task.comment = document.data["comment"].toString()
                        task.dimension = document.data["dimension"].toString()
                        task.shopname = document.data["shopname"].toString()

                        Log.d("uztest", "*")

                        mTaskAdapter.mTaskList.add(task)
                    }
                }
    }

     */

    private fun loaddata1() {
        db.collection("ingredient")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val ingredient = Ingredient("id_dummy",
                                                    Date(),
                                                    "name_dummy",
                                                    0,
                                                    "g_dummy",
                                                    "shopname_dummy",
                                                    "comment_dummy",
                                                    mutableListOf())

                        ingredient.id = document.data["id"].toString()
                        val firestoreTimestamp = document.data["date"] as com.google.firebase.Timestamp
                        ingredient.date = firestoreTimestamp.toDate()
                        ingredient.name = document.data["name"].toString()
                        ingredient.amount = document.data["amount"].toString().toInt()
                        ingredient.dimension = document.data["dimension"].toString()
                        ingredient.shopname = document.data["shopname"].toString()
                        ingredient.comment = document.data["comment"].toString()
                        if (document.data["idrel"]!=null){
                            ingredient.idrel = document.data["idrel"] as MutableList<String>
                            if(ingredient.idrel.size != 0){
                            Log.d("uztest", "idrel="+ingredient.idrel)
                            var idrel1 = ingredient.idrel[0]
                            Log.d("uztest", "idrel = ${idrel1}")}
                        }

                        mIngredientAdapter.mIngredientList.add(ingredient)
                    }
                }
                .addOnFailureListener { Log.d("uztest","read failed")

                }
    }


/*    private fun reloadListView0() {
            listView1.adapter = mTaskAdapter
            mTaskAdapter.notifyDataSetChanged()
//                    mTaskAdapter.mTaskList.clear()
    }

 */

    private fun reloadListView1() {
        listView1.adapter = mIngredientAdapter
        mIngredientAdapter.notifyDataSetChanged()
//        mIngredientAdapter.mIngredientList.clear()
    }


}


