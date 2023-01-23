package com.crypto.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.crypto.payment.ui.fragments.FragmentEnterId
import com.crypto.payment.ui.fragments.MainFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val id = getPreferences(MODE_PRIVATE).getString(Constants.sharedId,null)
        id?.let {
            App.id = it
            replaceFragment(MainFragment())
        }?:replaceFragment(FragmentEnterId())
        if (getDate(System.currentTimeMillis()).after(getDate(1673469871*1000L))){
            Log.d("suka","suka")
        }


    }
    fun getDate(time:Long):Date {
        val date:Date = Date(time)
        return date
    }
    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }
}