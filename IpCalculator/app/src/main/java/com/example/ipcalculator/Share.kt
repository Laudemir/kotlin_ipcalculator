package com.example.ipcalculator

import android.content.Context
import android.content.Intent
import android.widget.Toast

class Share(private val context: Context) {

    fun whatsappShare(text: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.setPackage("com.whatsapp")
            intent.putExtra(Intent.EXTRA_TEXT, text)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp não instalado", Toast.LENGTH_SHORT).show()
        }
    }


    fun generalShare(text: String, subject: String) {
        try {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(intent, "Compartilhar via"))

        } catch (e: Exception) {
            Toast.makeText(context, "Nenhum aplicativo encontrado", Toast.LENGTH_SHORT).show()
        }

    }
}