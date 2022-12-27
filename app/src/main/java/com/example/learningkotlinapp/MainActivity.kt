package com.example.learningkotlinapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.learningkotlinapp.databinding.ActivityMainBinding
//FOR kotlin
import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var _navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //here , ActivityMainBinding is auto generated
        // and its been inflated by the layoutInflater of App Compact Activity
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        //Set contentView is the root of setting view
        setContentView(binding.root)
        //now binding nav view to work with it , from parent binding
        _navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        _navView.setupWithNavController(navController)

        GlobalScope.launch {
            // i am launching , means creating a couro
        }

    }

    //-------------TESTING OUT WITH COROUTINE ----------------------//

    suspend fun printlnDelayed(message: String) {
        // Complex calculation
        delay(1000)
        println(message)
    }

    suspend fun calculateHardThings(startNum: Int): Int {
        delay(1000)
        return startNum * 10
    }

    fun exampleBlocking() = runBlocking {
        println("one")
        printlnDelayed("two")
        println("three")
    }

    suspend fun sayedTrying (argName : Int) :String {
        println("Sayed is calculating these ints")
        delay(1000)
        return argName.toString()
    }

    // Running on another thread but still blocking the main thread
    fun exampleBlockingDispatcher(){
        runBlocking(Dispatchers.Default) {
            println("one - from thread ${Thread.currentThread().name}")
            printlnDelayed("two - from thread ${Thread.currentThread().name}")
        }
        // Outside of runBlocking to show that it's running in the blocked main thread
        println("three - from thread ${Thread.currentThread().name}")
        // It still runs only after the runBlocking is fully executed.
    }

    fun exampleLaunchGlobal() = runBlocking {
        println("one - from thread ${Thread.currentThread().name}")

        GlobalScope.launch {
            printlnDelayed("two - from thread ${Thread.currentThread().name}")
        }

        println("three - from thread ${Thread.currentThread().name}")
        delay(3000)
    }

    fun exampleLaunchGlobalWaiting() = runBlocking {
        println("one - from thread ${Thread.currentThread().name}")

        val job = GlobalScope.launch {
            printlnDelayed("two - from thread ${Thread.currentThread().name}")
        }

        println("three - from thread ${Thread.currentThread().name}")
        job.join()
    }

    fun exampleLaunchCoroutineScope() = runBlocking {
        println("one - from thread ${Thread.currentThread().name}")

        val customDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

        launch(customDispatcher) {
            printlnDelayed("two - from thread ${Thread.currentThread().name}")
        }

        println("three - from thread ${Thread.currentThread().name}")

        (customDispatcher.executor as ExecutorService).shutdown()
    }

    fun exampleAsyncAwait() = runBlocking {
        val startTime = System.currentTimeMillis()

        val deferred1 = async { calculateHardThings(10) }
        val deferred2 = async { calculateHardThings(20) }
        val deferred3 = async { calculateHardThings(30) }

        val sum = deferred1.await() + deferred2.await() + deferred3.await()
        println("async/await result = $sum")

        val endTime = System.currentTimeMillis()
        println("Time taken: ${endTime - startTime}")
    }

    fun exampleWithContext() = runBlocking {
        val startTime = System.currentTimeMillis()

        val result1 = withContext(Dispatchers.Default) { calculateHardThings(10) }
        val result2 = withContext(Dispatchers.Default) { calculateHardThings(20) }
        val result3 = withContext(Dispatchers.Default) { calculateHardThings(30) }

        val sum = result1 + result2 + result3
        println("async/await result = $sum")

        val endTime = System.currentTimeMillis()
        println("Time taken: ${endTime - startTime}")
    }

    fun printLineSayed ( ):Unit {
        println("this is first message")
        printLineDelayedSayed("this is 2nd message")
        println("this is 3rd message")
    }

    fun printLineDelayedSayed (message: String){
        //Making the thread sleep
        Thread.sleep(1000)
        println(message)
    }

    suspend fun doNetworkCall ():String {
        //this is also a suspend function
        delay(3000L)
        return "The answere is 3 Sec"
    }
}