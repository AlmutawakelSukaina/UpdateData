package com.example.criminalinten

import android.app.Application


class CriminalIntentApllication :Application(){


    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }

}