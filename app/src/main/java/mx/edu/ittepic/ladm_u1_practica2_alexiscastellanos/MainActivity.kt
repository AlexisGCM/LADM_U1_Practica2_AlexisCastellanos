package mx.edu.ittepic.ladm_u1_practica2_alexiscastellanos

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        } else {
            mensaje("Permisos YA Otorgados")
        }

        BtnGuardar.setOnClickListener {
            if (Int.isChecked == true) {
                AlmacenaArchInt()
            }
            if (SD.isChecked == true) {
                AlmacenaEnSD()
            }
        }

        BtnAbrir.setOnClickListener {
            if (Int.isChecked == true) {
                leerArchivoInt()
            }
            if (SD.isChecked == true) {
                leerArchivoSD()
            }
        }
    }

    fun AlmacenaArchInt() {
        try {
            var Salida = OutputStreamWriter(openFileOutput(TextArchivo.text.toString(), Context.MODE_PRIVATE))
            var data = TextFrase.text.toString()
            Salida.write(data)
            Salida.flush()
            Salida.close()
            mensaje("Archivo Guardado")
            Text("")
        } catch (error: IOException) {
            mensaje(error.message.toString())
        }
    }

    fun leerArchivoInt() {
        try {
            var flujoEn =
                BufferedReader(InputStreamReader(openFileInput(TextArchivo.text.toString())))
            var data = flujoEn.readLine()
            var vector = data.split("&")
            Text(vector[0])
        } catch (error: IOException) {
            mensaje(error.message.toString())
        }
    }

    fun AlmacenaEnSD() {
        if (NoSD()) {
            mensaje("No hay memoria")
            return
        }

        try {
            var ruSD = Environment.getExternalStorageDirectory()
            var Archivo = File(ruSD.absolutePath, TextArchivo.text.toString())
            var Salida = OutputStreamWriter(FileOutputStream(Archivo))
            var data = TextFrase.text.toString()
            Salida.write(data)
            Salida.flush()
            Salida.close()
            mensaje("Archivo Guardado")
            Text("")
        } catch (error: IOException) {
            mensaje(error.message.toString())
        }
    }


    fun leerArchivoSD() {
        if (NoSD()) {
            return
        }
        try {
            var ruSD = Environment.getExternalStorageDirectory()
            var Archivo = File(ruSD.absolutePath, TextArchivo.text.toString())
            var flujoEn = BufferedReader(InputStreamReader(FileInputStream(Archivo)))
            var data = flujoEn.readLine()
            var vector = data.split("&")
            Text(vector[0])
        } catch (error: IOException) {
            mensaje(error.message.toString())
        }
    }

    fun NoSD(): Boolean {
        var est = Environment.getExternalStorageState()
        if (est != Environment.MEDIA_MOUNTED) {
            return true
        } else {
            return false
        }
    }

    fun mensaje(m: String) {
        AlertDialog.Builder(this).setTitle("ATENCION").setMessage(m).setPositiveButton("ACEPTAR") { d, i -> }.show()
    }

    fun Text(text1: String) {
        TextFrase.setText(text1)
    }

}
