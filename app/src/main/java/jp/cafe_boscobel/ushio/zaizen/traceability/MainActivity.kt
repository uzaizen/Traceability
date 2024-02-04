package jp.cafe_boscobel.ushio.zaizen.traceability

// https://qiita.com/Kaito-Dogi/items/b2da06db10a95fef84b3

import android.content.Intent
import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.net.wifi.WifiConfiguration.KeyMgmt.strings
import android.net.wifi.WifiConfiguration.Protocol.strings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.common.config.GservicesValue.value
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SnapshotMetadata
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.DiskLruCache
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_ingredience.*
import kotlinx.coroutines.*
//import kotlinx.android.synthetic.main.activity_main.fab
//import kotlinx.android.synthetic.main.app_bar_main.*
import kotlin.coroutines.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// private var snapshotListener : ListenerRegistration? = null
lateinit var db:FirebaseFirestore


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawer_layout:DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)



        if (supportActionBar == null) {
            setSupportActionBar(findViewById(R.id.toolbar))  // ここで適切なToolbarのIDを指定する必要があります
        }


        //           fab.setOnClickListener { View -> Log.d("uztest", "114") }

        drawer_layout = findViewById(R.id.drawer_layout)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()



        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item1 -> {
                    showToast(getString(R.string.ingredient))

                    CoroutineScope(Dispatchers.Main).launch {

                        withContext(Dispatchers.IO) {
                            // バックグラウンドでの非同期処理
                            val ingredient =
                                Ingredient(
                                    "000",
                                    Date(),
                                    "name0",
                                    0,
                                    "g",
                                    "dummy",
                                    "",
                                    mutableListOf("")
                                )
                            ingredient.readdata()
                        }

                        withContext(Dispatchers.Main){
                            showFragmentIngredience()
                        }

                        }
                    }

                R.id.nav_item2 -> showToast(getString(R.string.midproduct))
                R.id.nav_item3 -> showToast(getString(R.string.finalproduct))
                // Add more cases as needed
            }
            drawer_layout.closeDrawers()
            true
        }

        /*
        fab.setOnClickListener { view ->
            reloadListView1()
        }

         */






        // Firestoreをインスタンス化
        db = FirebaseFirestore.getInstance()

//        showFragmentIngredience()

//        mTaskAdapter = TaskAdapter(this)
//        mIngredientAdapter = IngredientAdapter(this)


/*        listView1.setOnItemClickListener { parent, _, position, _ ->
            Log.d("uztest", "position=${position.toString()}")
            val task = parent.adapter.getItem(position) as Task
            Log.d("uztest", "tapped " + task.name.toString())
        }

 */
/*
        listView1.setOnItemClickListener { parent, _, position, _ ->
            Log.d("uztest", "position=${position.toString()}")
            val ingredient = parent.adapter.getItem(position) as Ingredient
            Log.d("uztest", "tapped " + ingredient.name.toString())
        }

 */




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

/*
        val ingredient =
            Ingredient("000", Date(), "name0", 0, "g", "dummy", "", mutableListOf(""))
        ingredient.readdata()
        Thread.sleep(5000)
        //    reloadListView1()

 */




/*

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

 */

        /*

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


/*
    private fun reloadListView0() {
            listView1.adapter = mTaskAdapter
            mTaskAdapter.notifyDataSetChanged()
//                    mTaskAdapter.mTaskList.clear()
    }

 */
    }


/*    private fun reloadListView1() {
        listView1.adapter = mIngredientAdapter
        mIngredientAdapter.notifyDataSetChanged()
//        mIngredientAdapter.mIngredientList.clear()
    }

 */



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        // Handle other action bar items...

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_item1) {
            toolbar.title = getString(R.string.menu_ingredient_label)
        } else if (id == R.id.nav_item2) {
            toolbar.title = getString(R.string.menu_midproduct_label)
        } else if (id == R.id.nav_item3) {
            toolbar.title = getString(R.string.menu_finalproduct_label)
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        if (message == getString(R.string.ingredient)) {

        }

        if (message == getString(R.string.midproduct)) showFragmentMidproduct()
        if (message == "完成品") showFragmentFinalproduct()
    }



    private fun showFragmentIngredience() {
        val fragmentA = Fragment_Ingredience()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentA)
            .addToBackStack(null)
            .commit()
    }


    private fun showFragmentMidproduct() {
        val fragmentB = Fragment_Midproduct()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentB)
            .addToBackStack(null)
            .commit()
    }

    private fun showFragmentFinalproduct() {
        val fragmentC = Fragment_Finalproduct()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragmentC)
            .addToBackStack(null)
            .commit()
    }



}


