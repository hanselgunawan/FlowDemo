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

    @ExperimentalCoroutinesApi
    fun setupFlow() {

        // 1. flowOf
//        flow = flowOf(1,3,2,4,6,5).onEach {
//            Log.d("HANSEL", "Emitting $it")
//            delay(400)
//        }.flowOn(Dispatchers.Default)

        // 2. asFlow
//        flow = (1..5).asFlow().onEach { delay(300) }.flowOn(Dispatchers.Default)

        // 3. flow
//        flow = flow {
//            Log.d("HANSEL", "Start Flow")
//            (0..10).forEach {
//                // Emit items with 500 milliseconds delay
//                delay(500)
//                Log.d("HANSEL", "Emitting $it")
//                emit(it)
//            }
//        }.map {
//            it * it
//        }.flowOn(Dispatchers.Default)

        // 4. channelFlow
        flow = channelFlow {
            (0..10).forEach {
                delay(300)
                send(it)
            }
        }.flowOn(Dispatchers.Default)


    }

    private fun setupClicks() {
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flow.collect {
                    // print the values one by one
                    Log.d("HANSEL", it.toString())
                }
            }
        }
    }
}
