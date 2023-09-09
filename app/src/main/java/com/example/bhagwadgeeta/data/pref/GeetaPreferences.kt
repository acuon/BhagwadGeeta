package com.example.bhagwadgeeta.data.pref

class GeetaPreferences : Preferences() {

    var demo by booleanPref(GeetaPreferencesKey.DEMO.value, false)
}