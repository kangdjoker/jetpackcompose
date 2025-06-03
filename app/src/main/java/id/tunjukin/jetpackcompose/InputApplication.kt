package id.tunjukin.jetpackcompose

import android.app.Application
import com.orhanobut.hawk.Hawk

class InputApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }
}