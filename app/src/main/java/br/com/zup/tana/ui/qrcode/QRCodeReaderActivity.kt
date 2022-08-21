package br.com.zup.tana.ui.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.zup.tana.databinding.ActivityQrcodeReaderBinding
import br.com.zup.tana.ui.home.view.MainActivity
import com.google.zxing.integration.android.IntentIntegrator

class QRCodeReaderActivity : AppCompatActivity() {
    companion object {
        const val RESULT = "RESULT"
    }

    private lateinit var binding: ActivityQrcodeReaderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bvScan.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("posicione o leitor no qr code")
        integrator.setTorchEnabled(true)
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}