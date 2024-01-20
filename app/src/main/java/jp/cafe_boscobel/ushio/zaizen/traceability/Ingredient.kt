package jp.cafe_boscobel.ushio.zaizen.traceability

import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

open class Ingredient : Material{
    constructor(id: String,
                date: Date,
                name: String,
                amount: Int,
                dimension: String,
                shopname: String,
                comment: String,
                idrel:MutableList<String>
    ): super (id, date, name, amount, dimension, shopname, comment, idrel )
    {
    }


    override  fun readdata() {
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



    override fun savedata() {
        TODO("Not yet implemented")
    }

}