package com.hanseltritama.flowdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    lateinit var flow: Flow<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFlow()
        setupClicks()
    }

    private fun setupFlow() {

        flow = (1..5).asFlow().onEach { delay(300) }.flowOn(Dispatchers.IO)

    }

    private fun setupClicks() {
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flow.collect {
                    // print the values one by one
                    Log.d("HANSEL", "Emitting $it")
                }
            }
        }
    }
}
