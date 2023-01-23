package com.crypto.payment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.payment.App
import com.crypto.payment.models.EtheriumResultModel
import com.crypto.payment.models.HistoryModel
import com.crypto.payment.models.TronScanObjectModel
import com.crypto.payment.repository.ApiEtheriumInterface
import com.crypto.payment.repository.ApiInterface
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PaymentViewModel : ViewModel() {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    var historyChildId = "0"
    suspend fun insertHistory(id:String,historyModel: HistoryModel){
        getHistoryLastChild(id).collect{
            historyChildId = it
            database.child(id).child("history").child(it).setValue(historyModel)
        }
    }
    fun checkTrc20Transaction(startTimeStamp:Long) = channelFlow{
        ApiInterface.create().getTronInfo(
            App.cryptoModel.address,
            "0",
            "1",
            startTimeStamp,
            System.currentTimeMillis()+3600000
        ).enqueue(object :Callback<TronScanObjectModel>{
            override fun onResponse(
                call: Call<TronScanObjectModel>,
                response: Response<TronScanObjectModel>,
            ) {
                response.body()?.let {
                    viewModelScope.launch {
                        send(it.data)
                        close()
                    }
                }

            }
            override fun onFailure(call: Call<TronScanObjectModel>, t: Throwable) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        })
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun getDate(time:Long): Date {
        val date: Date = Date(time)
        return date
    }
    fun checkErc20Transaction() = channelFlow{
        ApiEtheriumInterface.create().getErc20Info(
            "account",
            "tokentx",
            "0xdac17f958d2ee523a2206206994597c13d831ec7",
            App.cryptoModel.address,
            "1",
            "1",
            "0",
            "99999999",
            "desc",
            "Z97HBEQT2U5B9JHV76FSH93J2892TX8TUD"
        ).enqueue(object :Callback<EtheriumResultModel>{
            override fun onResponse(
                call: Call<EtheriumResultModel>,
                response: Response<EtheriumResultModel>,
            ) {
                response.body()?.let {
                    viewModelScope.launch {
                        send(it.result)
                        close()
                    }
                }

            }
            override fun onFailure(call: Call<EtheriumResultModel>, t: Throwable) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        })
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun checkEthTransaction() = channelFlow{
        ApiEtheriumInterface.create().getEthInfo(
            "account",
            "tokentx",
            App.cryptoModel.address,
            "1",
            "1",
            "0",
            "99999999",
            "desc",
            "Z97HBEQT2U5B9JHV76FSH93J2892TX8TUD"
        ).enqueue(object :Callback<EtheriumResultModel>{
            override fun onResponse(
                call: Call<EtheriumResultModel>,
                response: Response<EtheriumResultModel>,
            ) {
                response.body()?.let {
                    viewModelScope.launch {
                        send(it.result)
                        close()
                    }
                }

            }
            override fun onFailure(call: Call<EtheriumResultModel>, t: Throwable) {
                viewModelScope.launch {
                    send(null)
                    close()
                }
            }
        })
        awaitClose()
    }.flowOn(Dispatchers.IO)
    fun checkHistory(id: String)= channelFlow{
        database.child(id).child("history").addListenerForSingleValueEvent(  object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild("1")){
                    viewModelScope.launch {
                        send(true)
                        close()
                    }
                }
                else{
                    viewModelScope.launch {
                        send(false)
                        close()
                    }
                }


            }
            override fun onCancelled(databaseError: DatabaseError) {
//                viewModelScope.launch {
//                    send(null)
//                    close()
//                }
            }
        })
        awaitClose()
    }.flowOn(Dispatchers.IO)
    suspend fun getHistoryLastChild(id:String)= channelFlow{
        checkHistory(id).collect{
            if (it){
                database.child(id).child("history").addListenerForSingleValueEvent(  object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        viewModelScope.launch {
                            send((dataSnapshot.childrenCount+1).toString())
                            close()
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
            })
        }
            else{
              send("1")
                close()
            }
}
    awaitClose()
    }.flowOn(Dispatchers.IO)

}