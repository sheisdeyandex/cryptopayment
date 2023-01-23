package com.crypto.payment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.payment.models.CryptoModel
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    fun getUsdtTrc20ExchangeRate() = channelFlow {
        database.child("usdt_trc20_exchange_rate").addListenerForSingleValueEvent(  object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.value.toString().toDouble()
                viewModelScope.launch {
                    send(post)
                    close()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        }
        )
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun getUsdtErc20ExchangeRate() = channelFlow {
        database.child("usdt_erc_20_exchange_rate").addListenerForSingleValueEvent(  object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.value.toString().toDouble()
                viewModelScope.launch {
                    send(post)
                    close()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        }
        )
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun getUsdt(id:String)= channelFlow {
        database.child(id).child("usdt").addListenerForSingleValueEvent(  object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(CryptoModel::class.java)
                viewModelScope.launch {
                    send(post)
                    close()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        }

        )
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun getUsdtErc20(id:String)= channelFlow {
        database.child(id).child("usdterc").addListenerForSingleValueEvent(  object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(CryptoModel::class.java)
                viewModelScope.launch {
                    send(post)
                    close()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        }

        )
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun getEthExchangeRate() = channelFlow {
        database.child("eth_exchange_rate").addListenerForSingleValueEvent(  object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.value.toString().toDouble()
                viewModelScope.launch {
                    send(post)
                    close()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        }
        )
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun getEth(id:String)= channelFlow {
        database.child(id).child("eth").addListenerForSingleValueEvent(  object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue(CryptoModel::class.java)
                viewModelScope.launch {
                    send(post)
                    close()
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        }

        )
        awaitClose()
    }.flowOn(Dispatchers.IO)
}