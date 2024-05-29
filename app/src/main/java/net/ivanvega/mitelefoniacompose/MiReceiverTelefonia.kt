package net.ivanvega.mitelefoniacompose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast


class MiReceiverTelefonia: BroadcastReceiver()
{
    companion object {
        private var isRinging: Boolean = false
    }

    object ScreenViewModelSingleton {
        private val viewModel = ScreenViewModel()

        fun getInstance(): ScreenViewModel {
            return viewModel
        }
    }

    override fun onReceive(p0: Context?, intent: Intent?) {

        val action: String? = intent?.getAction()
        //Uri uri = intent.getData();
        action?.let { Log.d("MiBroadcast", it) }
         var strMensaje: String = ""
        //Uri uri = intent.getData();
        if (action == Intent.ACTION_BOOT_COMPLETED) {
        }
        if (Intent.ACTION_INPUT_METHOD_CHANGED === action) {
        }
        if (action == Intent.ACTION_BOOT_COMPLETED) {
        }
        if (action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bndSMS: Bundle? = intent?.getExtras()
            val pdus = bndSMS?.get("pdus") as Array<Any>?
            val smms: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(pdus!!.size)
            for (i in smms.indices) {
                smms[i] = SmsMessage.createFromPdu(pdus!![i] as ByteArray)
                strMensaje +="${"Mensaje: " + smms[i]?.getOriginatingAddress()}\n" +
                        "${smms[i]?.getMessageBody().toString()}"


            }
            Log.d("MiBroadcast", strMensaje)
        } else if (action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val extras = intent?.extras
            if (extras != null) {
                val state = extras.getString(TelephonyManager.EXTRA_STATE)

                if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                    isRinging = true
                    Log.d("Llamada de telefono", "isRing se puso $isRinging")
                } else if (state == TelephonyManager.EXTRA_STATE_OFFHOOK) {
                    isRinging = false
                    Log.d("Llamada de telefono", "Contesto y se puso $isRinging")
                } else if (state == TelephonyManager.EXTRA_STATE_IDLE && isRinging) {

                        Log.d("Llamada de telefono", "DejoDeLlamar $isRinging")
                    val viewModel = ScreenViewModelSingleton.getInstance()
                    // Llama a la función para enviar el SMS
                    viewModel.sendSMS()
                        isRinging = false

                }
            }
        }


        //Log.d("MiBroadcast", action)
        //Log.d("MiBroadcast", uri.toString());
        //Log.d("MiBroadcast", uri.toString());
        /*Toast.makeText(
            context.getApplicationContext(),
            action, Toast.LENGTH_SHORT
        ).show()

        Toast.makeText(
            context.getApplicationContext(),
            strMensaje, Toast.LENGTH_SHORT
        ).show()*/

        /*Log.d(TAG, log)
        Toast.makeText(context, log, Toast.LENGTH_LONG).show()*/
        val sb = StringBuilder()
        sb.append("Action: " + intent?.action + "\n")
        sb.append("URI: " + intent?.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n")
        val log = sb.toString()
        Log.d("MiBroadcast", log)

    }
}