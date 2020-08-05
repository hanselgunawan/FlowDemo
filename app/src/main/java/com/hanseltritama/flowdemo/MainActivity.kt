package com.hanseltritama.flowdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

//    lateinit var flow: Flow<Int>
    lateinit var flowStr: Flow<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupFlow()
        setupClicks()
    }

    @ExperimentalCoroutinesApi
    fun setupFlow() {

        // ========= 1. flowOf ==========
//        flow = flowOf(1,3,2,4,6,5).onEach {
//            Log.d("HANSEL", "Emitting $it")
//            delay(400)
//        }.flowOn(Dispatchers.Default)

        // ========= 2. asFlow ===========
//        flow = (1..5).asFlow().onEach { delay(300) }.flowOn(Dispatchers.Default)

        // ========== 3. flow ==========
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

        // ========== 4. channelFlow ==========
//        flow = channelFlow {
//            (0..10).forEach {
//                delay(300)
//                send(it)
//            }
//        }.flowOn(Dispatchers.Default)

        // ========== 5. Exception Handling ==========
//        flow = channelFlow {
//            (0..10).forEach {
//                delay(300)
//                check(it != 3)
//                send(it)
//            }
//        }.onCompletion {
//            Log.d("Hansel", "Complete!")
//        }.catch { e ->
//            Log.d("Hansel", "Caught $e")
//        }.flowOn(Dispatchers.Default)

        // ========== 6. Zip Operator ==========
        val intFlow = flowOf(1,2,3)
        val strFlow = flowOf("A","B","C")

        flowStr = intFlow.zip(strFlow) {intValue, stringValue ->
            "$intValue$stringValue"

        // ========== 6. Retry Operator ==========
        }.retryWhen { cause, attempt ->
            if (cause is IOException && attempt <= 3) {
                delay(2000)
                return@retryWhen true
            } else {
                return@retryWhen false
            }
        }.flowOn(Dispatchers.Default)


    }

    private fun setupClicks() {
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flowStr.collect {
                    // print the values one by one
                    Log.d("HANSEL", it)
                }
            }
        }
    }
}
