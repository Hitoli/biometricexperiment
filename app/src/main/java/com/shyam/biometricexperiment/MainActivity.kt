package com.shyam.biometricexperiment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.shyam.biometricexperiment.biometric_classes.Biometrics
import com.shyam.biometricexperiment.ui.theme.BiometricexperimentTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biometricAuthentication= Biometrics(this)
        setContent {
            BiometricexperimentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current as FragmentActivity
                    Box(Modifier.fillMaxSize()){
                        Button(onClick = { biometricAuthentication.authBiometricPrompt(
                            title = "Biometrics",
                            subTitle = "Authentication with Face and FingerPrint",
                            negativeButtonText = "Cancel",
                            fragmentActivity = context,
                            onSuccess = {
                                Log.e("AUTHResult","SUCCESS")

                            }, onFailed = {
                                Log.e("AUTHResult","FAILURE")

                            }, onError = {_,ONERR->
                                Log.e("AUTHResult",ONERR)

                            }) }, modifier=Modifier.align(Alignment.Center)) {
                            Text(text = "AUTH TEST")
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BiometricexperimentTheme {
        Greeting("Android")
    }
}