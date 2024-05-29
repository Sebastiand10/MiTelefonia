    package net.ivanvega.mitelefoniacompose

    import android.telephony.SmsManager
    import android.util.Log
    import androidx.lifecycle.ViewModel

    class ScreenViewModel: ViewModel() {

        fun sendSMS(){
            Log.d("Mensaje de respuesta","Se envio")
            val smsManage = SmsManager.getDefault()
            smsManage.sendTextMessage("4451418013",
                null,
                "No puedo contestar en este momento",null,null
                )

        }

    }