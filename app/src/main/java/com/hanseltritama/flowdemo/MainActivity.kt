package com.hanseltritama.flowdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var flowStr: Flow<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFlow()
        setupClicks()
    }

    private fun setupFlow() {

        val intFlow = flowOf(1,2,3)
        val strFlow = flowOf("A","B","C")

        flowStr = intFlow.zip(strFlow) {intValue, stringValue ->
            delay(500)
            "$intValue$stringValue"
        }.flowOn(Dispatchers.IO)

    }

    private fun setupClicks() {
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flowStr.collect {
                    // print the values one by one
                    Log.d("HANSEL", "Emitting $it")
                }
            }
        }
    }
}
