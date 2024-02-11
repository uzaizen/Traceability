package jp.cafe_boscobel.ushio.zaizen.traceability

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*

var IngredientData:MutableList<Material> = mutableListOf()
lateinit var AddIngredience:Material

open class Ingredient : Material{
    constructor(id: String,
                date: Date,
                name: String,
                amount: Int,
                dimension: String,
                shopname: String,
                expiration : Date,
                comment: String,
                idrel:MutableList<String>
    ): super (id, date, name, amount, dimension, shopname, expiration, comment, idrel )
    {
    }


    override suspend fun readdata() {

        IngredientData.clear()

        try {
            val result = Tasks.await(db.collection("ingredient").get())
            for (document in result) {
                val ingredient = Ingredient(
                    "id_dummy",
                    Date(),
                    "name_dummy",
                    0,
                    "g_dummy",
                    "shopname_dummy",
                    Date(),
                    "comment_dummy",
                    mutableListOf()
                )

                ingredient.id = document.data["id"].toString()
                var firestoreTimestamp = document.data["date"] as com.google.firebase.Timestamp
                ingredient.date = firestoreTimestamp.toDate()
                ingredient.name = document.data["name"].toString()
                ingredient.amount = document.data["amount"].toString().toInt()
                ingredient.dimension = document.data["dimension"].toString()
                ingredient.shopname = document.data["shopname"].toString()
                firestoreTimestamp = document.data["expiration"] as com.google.firebase.Timestamp
                ingredient.expiration = firestoreTimestamp.toDate()
                ingredient.comment = document.data["comment"].toString()
                if (document.data["idrel"] != null) {
                    ingredient.idrel = document.data["idrel"] as MutableList<String>
                    if (ingredient.idrel.size != 0) {
//                            Log.d("uztest", "idrel=" + ingredient.idrel)
                        var idrel1 = ingredient.idrel[0]
//                            Log.d("uztest", "idrel = ${idrel1}")
                    }
                }
                IngredientData.add(ingredient)
//                    Log.d("uztest", "DataSize on readdata=${IngredientData.size}")
//                    mIngredientAdapter.mIngredientList.add(ingredient)
            }

        Log.d("uztest", "readdata done")
    } catch (e:Exception){
        Log.d("uztest", "readdata failed:${e.message}")
    }

        return
    }

    override suspend fun adddata() {
        var NextID: String = "0001"
        val result = Tasks.await(
            db.collection("ingredient").orderBy("id", Query.Direction.DESCENDING).limit(1).get())
        for (document in result) {
            val ingredient = Ingredient(
                "id_dummy",
                Date(),
                "name_dummy",
                0,
                "g_dummy",
                "shopname_dummy",
                Date(),
                "comment_dummy",
                mutableListOf()
            )
            ingredient.id = document.data["id"].toString()
            NextID = (ingredient.id.toLong()+1).toString()
            AddIngredience.id = NextID

        db.collection("ingredient")
            .document(AddIngredience.id)
            .set(AddIngredience)
            .addOnSuccessListener { documentReference ->
//                    Log.d("uztest", "DocumentSnapshot added with ID: ${documentReference.toString()}")
                Log.d("uztest","add data write done")
            }
            .addOnFailureListener { e ->
//                    Log.d("uztest", "Error adding document", e)
                Log.d("uztest","add data write failed")
            }


        }

    }

    override suspend fun savedata() {
        TODO("Not yet implemented")
    }

}