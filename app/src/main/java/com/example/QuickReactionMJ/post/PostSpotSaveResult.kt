package com.example.QuickReactionMJ.post

data class PostSpotSaveResult(

        val lat: String,
        val lng : String,
        val name : String,
        val address : Address

)
data class Address(
        val city: String,
        val detail : String,
        val gunGu: String,
        val zipcode : String
)
