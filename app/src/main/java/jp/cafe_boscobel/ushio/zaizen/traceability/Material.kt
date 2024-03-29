package jp.cafe_boscobel.ushio.zaizen.traceability

import java.util.*

abstract class Material
{
    var id: String
    var date: Date
    var name: String
    var amount: Int
    var dimension: String
    var shopname: String
    var expiration : Date
    var comment: String
    var idrel:MutableList<String>


    constructor(
        id: String,
        date: Date,
        name: String,
        amount: Int,
        dimension: String,
        shopname : String,
        expiration : Date,
        comment: String,
        idrel:MutableList<String>
    )
    {   this.id = id
        this.date = date
        this.name = name
        this.amount = amount
        this.dimension = dimension
        this.shopname = shopname
        this.expiration = expiration
        this.comment = comment
        this.idrel = idrel
    }

    abstract suspend fun readdata()
    abstract suspend fun adddata()
    abstract suspend fun savedata()
}