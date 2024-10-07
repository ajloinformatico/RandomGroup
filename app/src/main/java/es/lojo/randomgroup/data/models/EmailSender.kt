package es.lojo.randomgroup.data.models

data class EmailSenderModel(
    val subject: String,
    val body: String,
    val recipients: String,
    val sender: String?
)
