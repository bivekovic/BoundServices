package com.algebra.boundservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ComponentName
import android.content.Context
import android.os.IBinder
import android.content.ServiceConnection
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity( ) {

    private lateinit var bShowTime : Button
    private lateinit var tvTime    : TextView

    private lateinit var myService : MyBoundService
    private var          mBound    : Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected( className: ComponentName, service: IBinder ) {
            myService = ( service as MyBoundService.LocalBinder ).getService( )
            mBound    = true
            Log.i( "ServiceConnection", "Spojen!!" )
        }

        override fun onServiceDisconnected( cn: ComponentName ) {
            mBound = false
            Log.i( "ServiceConnection", "Odspojen!!" )
        }
    }

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        initWidgets( )
        setupListeners( )
    }

    private fun initWidgets() {
        bShowTime = findViewById( R.id.bTime )
        tvTime =    findViewById( R.id.tvTime )
    }

    private fun setupListeners( ) {
        bShowTime.setOnClickListener { showTime( ) }
    }

    override fun onStart( ) {
        super.onStart( )
        Intent( this, MyBoundService::class.java ).also { intent ->
            bindService( intent, connection, Context.BIND_AUTO_CREATE )
        }
    }

    override fun onStop( ) {
        unbindService( connection )
        super.onStop( )
        mBound = false
    }

    private fun showTime( ) {
        val currentTime = myService.getCurrentTime( )
        tvTime.text = currentTime
    }

}
