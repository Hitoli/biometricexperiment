package com.shyam.biometricexperiment.biometric_classes

enum class BiometricStatus(val value:Int) {
    Ready(0),
    Not_Available(1),
    Available_Not_Invoked(2),
    Temporary_Not_Available(3)
}