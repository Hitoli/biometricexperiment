package com.shyam.biometricexperiment.biometric_classes

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

class Biometrics(private val context: Context) {

    private lateinit var biometricPormpt:BiometricPrompt
    private lateinit var promptInfo:BiometricPrompt.PromptInfo
    private val biometricManager=BiometricManager.from(context)

    fun isBiometricAvailable(): BiometricStatus {
        return when(biometricManager.canAuthenticate(BIOMETRIC_STRONG)){
            BiometricManager.BIOMETRIC_SUCCESS -> BiometricStatus.Ready
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->BiometricStatus.Available_Not_Invoked
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->BiometricStatus.Not_Available
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->BiometricStatus.Temporary_Not_Available
            else -> BiometricStatus.Not_Available
        }
    }

    fun authBiometricPrompt(
        title:String,
        subTitle:String,
        negativeButtonText:String,
        fragmentActivity:FragmentActivity,
        onSuccess:(Result:BiometricPrompt.AuthenticationResult)->Unit,
        onError:(errorCode:Int,errorString:String)->Unit,
        onFailed:()->Unit
    ){
        when(isBiometricAvailable()){

            BiometricStatus.Ready -> {
                onError(BiometricStatus.Ready.value,"Biometrics Ready")
            }
            BiometricStatus.Not_Available ->{
                onError(BiometricStatus.Not_Available.value,"Biometrics are not available")
            }
            BiometricStatus.Available_Not_Invoked ->{
                onError(BiometricStatus.Available_Not_Invoked.value,"Please enter your faceID or FingerPrint in your device")
            }
            BiometricStatus.Temporary_Not_Available ->{
                onError(BiometricStatus.Temporary_Not_Available.value,"Biometrics are temporarily disabled")
            }
            else -> {
                onError(BiometricStatus.Not_Available.value,"Some Error has occured with Biometrics")
            }
        }

        biometricPormpt = BiometricPrompt(
            fragmentActivity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess(result)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onFailed()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errorCode,errString.toString())
                }
            }
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle(title).setSubtitle(subTitle).setNegativeButtonText("cancel").build()

        biometricPormpt.authenticate(promptInfo)

    }

}