package com.example.ipcalculator

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var cidrResult: TextView
    private lateinit var enderecoIp: EditText
    private lateinit var bitMask: TextView
    private lateinit var ip: TextView
    private lateinit var mascara: TextView
    private lateinit var rede: TextView
    private lateinit var primeiro: TextView
    private lateinit var ultimo: TextView
    private lateinit var broadcast: TextView
    private lateinit var hosts: TextView
    private lateinit var enviar: Button
    private lateinit var enviarZap: Button


    private lateinit var lanIP: String
    private lateinit var gwIP: String


    private var a = 255
    private var b = 255
    private var c = 255
    private var d = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Definir a orientação para Portrait programaticamente
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        seekBar    = findViewById(R.id.seekBar)
        cidrResult = findViewById(R.id.textResult)
        enderecoIp = findViewById(R.id.editIP)
        ip         = findViewById(R.id.textIP)
        mascara    = findViewById(R.id.textMask)
        rede       = findViewById(R.id.textNetwork)
        primeiro   = findViewById(R.id.textFirst)
        ultimo     = findViewById(R.id.textLast)
        broadcast  = findViewById(R.id.textBroadcast)
        hosts      = findViewById(R.id.textHosts)
        enviar     = findViewById(R.id.btnCompartilhar)
        enviarZap  = findViewById(R.id.btnWhats)
        bitMask    = findViewById(R.id.bitProgress)

        enderecoIp.setText("192.168.0.1")
        bitMask.setText("24")

        cidrResult.text = "255.255.255.0 / 24"
        seekBar.progress = 24
        seekBar.min = 8
        seekBar.max = 30
        calcular(a, b, c, d, 24)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                atualizarMascara(progress)
                calcular(a, b, c, d, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Enviar por qualquer aplicativo que permita
        enviar.setOnClickListener {
            compartilharTexto(this)
        }

        // Enviar via whatsapp
        enviarZap.setOnClickListener {
            compartilharWhatsapp(this)
        }
    }

    private fun atualizarMascara(progress: Int) {
        val mascaraMap = mapOf(
            8 to "255.0.0.0", 9 to "255.128.0.0", 10 to "255.192.0.0",
            11 to "255.224.0.0", 12 to "255.240.0.0", 13 to "255.248.0.0",
            14 to "255.252.0.0", 15 to "255.254.0.0", 16 to "255.255.0.0",
            17 to "255.255.128.0", 18 to "255.255.192.0", 19 to "255.255.224.0",
            20 to "255.255.240.0", 21 to "255.255.248.0", 22 to "255.255.252.0",
            23 to "255.255.254.0", 24 to "255.255.255.0", 25 to "255.255.255.128",
            26 to "255.255.255.192", 27 to "255.255.255.224", 28 to "255.255.255.240",
            29 to "255.255.255.248", 30 to "255.255.255.252"
        )
        mascaraMap[progress]?.let {
            bitMask.text = "$progress"
            cidrResult.text = "$it / $progress"
            val partes = it.split(".").map { it.toInt() }
            a = partes[0]; b = partes[1]; c = partes[2]; d = partes[3]
        }
    }

    private fun calcular(a: Int, b: Int, c: Int, d: Int, hostsPerSubnet: Int) {
        val expBits = 2.0.pow((32 - hostsPerSubnet).toDouble()).toInt() - 2

        if (enderecoIp.text.isNotEmpty()) {
            try {
                val ips = enderecoIp.text.toString().split(".").map { it.toInt() }
                val (dIP1, dIP2, dIP3, dIP4) = ips

                ip.text = enderecoIp.text
                mascara.text = "$a.$b.$c.$d"
                hosts.text = expBits.toString()

                val net1 = dIP1 and a
                val net2 = dIP2 and b
                val net3 = dIP3 and c
                val net4 = dIP4 and d

                rede.text = "$net1.$net2.$net3.$net4"

                val bd1 = net1 or (255 xor a)
                val bd2 = net2 or (255 xor b)
                val bd3 = net3 or (255 xor c)
                val bd4 = net4 or (255 xor d)

                broadcast.text = "$bd1.$bd2.$bd3.$bd4"
                primeiro.text  = "$net1.$net2.$net3.${net4 + 1}"
                ultimo.text    = "$bd1.$bd2.$bd3.${bd4 - 1}"

                lanIP = "$net1.$net2.$net3.${net4 + 2}"
                gwIP  = "$net1.$net2.$net3.${net4 + 1}"


            } catch (e: Exception) {
                //exibirMensagem("Entrada de IP inválida!")
            }
        }
    }

    private fun exibirMensagem(mensagem: String) {
        Toast.makeText(applicationContext, mensagem, Toast.LENGTH_SHORT).show()
    }

    // Compartilhamento geral sem formatação de textos
    fun compartilharTexto(context: Context) {

        val assunto = "Dados IP de Rede"

        val texto = """
        Segue os dados dos IPs da rede informada:
        
        IP: ${ip.text}
        Máscara: ${mascara.text}

        Rede: ${rede.text}/${bitMask.text}
        Broadcast: ${broadcast.text}
        
        Primeiro IP utilizável: ${primeiro.text}
        Último IP utilizável: ${ultimo.text}
    """.trimIndent()

        // chama a classe()
        // método generalShare(text, subject)
        val share = Share(this)
        share.generalShare(texto, assunto)
    }

    // Compartilhar
    fun compartilharWhatsapp(context: Context) {
        val text = """
        *Dados IPs de rede*
        
        *_IP:_* ${ip.text}
        *_Máscara:_* ${mascara.text}
        
        *_Rede:_* ${rede.text}/${bitMask.text}
        *_Primeiro IP:_* *${primeiro.text}*
        *_Último IP:_* *${ultimo.text}*
        *_Broadcast:_* ${broadcast.text}
    """.trimIndent()

        // chama a classe Share(),
        // étodo whatsappShare()
        val whatsShare = Share(this)
        whatsShare.whatsappShare(text)
    }
}