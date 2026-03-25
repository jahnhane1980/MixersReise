package com.deinname.mixersreise.data

data class TalkOption(
    val id: Int,
    val question: String,
    val answer: String
)

val mixerTalkOptions = listOf(
    TalkOption(1, "Wie geht es dir?", "Mir geht es blendend, danke der Nachfrage!"),
    TalkOption(2, "Hast du Hunger?", "Ein kleiner Snack wäre jetzt echt super..."),
    TalkOption(3, "Vermisst du mich?", "Jede Sekunde, in der die App zu ist! ❤️")
)