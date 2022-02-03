package com.svetlanakuro.pictureoftheday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.svetlanakuro.pictureoftheday.ui.DailyImageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<DailyImageFragment>(R.id.fragment_container)
            }
        }
    }
}