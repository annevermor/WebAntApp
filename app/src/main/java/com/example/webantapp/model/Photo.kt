package com.example.webantapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SearchedPhoto(
    @SerialName(value = "results")
    val photos: List<Photo>
)

@Serializable
data class Photo(
    val id: String = "0",
    @SerialName(value = "urls")
    val urls: Urls = Urls(),
    val likes: Int = 129,
    @SerialName(value = "alt_description")
    val description: String = "A woman sitting at a table working on a laptop",
    @SerialName(value = "created_at")
    val date: String = "2024-07-18T19:49:40Z",
    val user: User = User()
)

@Serializable
data class Urls(
    @SerialName(value = "raw") val raw: String? = "https://images.unsplash.com/photo-1721332154161-847851ea188b?ixid=M3w2NTA2NTJ8MXwxfGFsbHwxfHx8fHx8Mnx8MTcyNTUzMjMxOXw&ixlib=rb-4.0.3",
    @SerialName(value = "full") val full: String? = "https://images.unsplash.com/photo-1721332154161-847851ea188b?crop=entropy&cs=srgb&fm=jpg&ixid=M3w2NTA2NTJ8MXwxfGFsbHwxfHx8fHx8Mnx8MTcyNTUzMjMxOXw&ixlib=rb-4.0.3&q=85",
    @SerialName(value = "regular") val regular: String? = "https://images.unsplash.com/photo-1721332154161-847851ea188b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NTA2NTJ8MXwxfGFsbHwxfHx8fHx8Mnx8MTcyNTUzMjMxOXw&ixlib=rb-4.0.3&q=80&w=1080",
    @SerialName(value = "small") val small: String? = "https://images.unsplash.com/photo-1721332154161-847851ea188b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NTA2NTJ8MXwxfGFsbHwxfHx8fHx8Mnx8MTcyNTUzMjMxOXw&ixlib=rb-4.0.3&q=80&w=400",
    @SerialName(value = "thumb") val thumb: String? = "https://images.unsplash.com/photo-1721332154161-847851ea188b?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w2NTA2NTJ8MXwxfGFsbHwxfHx8fHx8Mnx8MTcyNTUzMjMxOXw&ixlib=rb-4.0.3&q=80&w=200",
)

@Serializable
data class User(
    val name: String = "Photographer"
)