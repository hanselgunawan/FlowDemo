package com.hanseltritama.flowdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var flow: Flow<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFlow()
        setupClicks()
    }

    private fun setupFlow() {

        flow = flow {
            (0..10).forEach {
                delay(300)
                check(it != 3)
                emit(it)
            }
        }.onCompletion {
            Log.d("Hansel", "Complete!")
        }.catch { e ->
            Log.d("Hansel", "Caught $e")
        }.flowOn(Dispatchers.Default)

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
