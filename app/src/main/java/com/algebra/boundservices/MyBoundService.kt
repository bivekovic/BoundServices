package com.algebra.boundservices

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class MyBoundService : Service( ) {

    private val binder = LocalBinder( )

    override fun onBind( intent: Intent ): IBinder {
        return binder
    }

    override fun onCreate( ) {
        super.onCreate( )
        Log.i( "Service", "Kreirano!" )
    }

    fun getCurrentTime( ): String {
        val dateFormat = SimpleDateFormat( "dd.MM.yyyy. HH:mm:ss" )
        return dateFormat.format( Date( ) )
    }


    inner class LocalBinder : Binder( ) {
        fun getService( ): MyBoundService = this@MyBoundService
    }
}
