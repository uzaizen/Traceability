package jp.cafe_boscobel.ushio.zaizen.traceability

// https://qiita.com/Kaito-Dogi/items/b2da06db10a95fef84b3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SnapshotMetadata
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.DiskLruCache
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

private var snapshotListener : ListenerRegistration? = null

class MainActivity : AppCompatActivity() {

    private lateinit var mTaskAdapter:TaskAdapter
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Firestoreをインスタンス化
        db = FirebaseFirestore.getInstance()


        var task = Task()
//        task.uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        task.id  = "111"
        task.name = "name"
        task.date = Date()
        task.amount = 0
        task.comment = ""
        task.dimension = "g"
        task.shopname = "marunaka"

        Log.d("uztest", "DB Write")

/*  下記モジュールは何故か　"id"="003"以外のケースでも起動されているようである。
        db.collection("task").whereEqualTo("id", "003")
                .addSnapshotListener{querySnapshot, firebaseFirestoreException ->
                    if(firebaseFirestoreException != null){return@addSnapshotListener
                }
                    Log.d("uztest", "Snapshot Listener worked")}

 */

        mTaskAdapter = TaskAdapter(this)

        listView1.setOnItemClickListener {parent, _,position,_ ->
            val task = parent.adapter.getItem(position) as Task
            Log.d("uzaizen", "tapped "+task.id.toString())
        }

/*
        /* 指定したidでデータベースに書き込み */
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
        reloadListView()

    }

    private fun reloadListView(){
    var task1=Task()
        //Firestoreからすべてのデータを取得しmTaskAdapter.mTaskListに渡し、それをlistView1.adapterに渡す必要がある
//        val taskResult = db.collection("task").get()
//        listView1.adapter=mTaskAdapter

        //mTaskListにtaskResultの内容をコピーするにはどうするか
//        mTaskAdapter.mTaskList = taskResult
        db.collection("task")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result){
                        Log.d("uztest", "${document.id} => ${document.data}")
                        task1.id=document.data["id"].toString()
                        Log.d("uztest","id set")
                        task1.name=document.data["name"].toString()
                        Log.d("uztest","name set")
                        task1.date=document.data["date"] as? Date
                        Log.d("uztest","date set")
                        task1.amount=document.data["amount"] as? Int
                        Log.d("uztest","amount set")
                        task1.comment=document.data["comment"].toString()
                        task1.dimension=document.data["dimension"].toString()
                        task1.shopname=document.data["shopname"].toString()

                        Log.d("uztest","data set ${document.id}")
                        mTaskAdapter.mTaskList.add(task1)
                    }
                }
        listView1.adapter=mTaskAdapter
        mTaskAdapter.notifyDataSetChanged()
        Log.d("uztest","reload is done")

    }

}
