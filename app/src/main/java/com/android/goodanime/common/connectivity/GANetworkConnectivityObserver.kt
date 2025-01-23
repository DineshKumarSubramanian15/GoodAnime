package com.android.goodanime.common.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.android.goodanime.common.GAApplication.Companion.getInstance
import com.android.goodanime.common.GAInternetConnectivityStatus
import com.android.goodanime.common.GALog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

// TODO : Need to change networkState as flow, if there is alternative approach
/**
 * GANetworkConnectivityObserver class observes changes in network connectivity.
 */
class GANetworkConnectivityObserver @Inject constructor() {
    private val TAG = "GANetworkConnectivityObserver"
    var networkState: MutableStateFlow<GAInternetConnectivityStatus> =
        MutableStateFlow(GAInternetConnectivityStatus.AVAILABLE)

    /**
     * Function to observe the changes in connection status
     */
    fun observe(): Flow<GAInternetConnectivityStatus> {
        try {
            val connectivityManager =
                getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities == null) {
                networkState.value = GAInternetConnectivityStatus.UNAVAILABLE
            }
            connectivityManager.registerDefaultNetworkCallback(
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        networkState.value = GAInternetConnectivityStatus.AVAILABLE
                    }

                    override fun onLost(network: Network) {
                        networkState.value = GAInternetConnectivityStatus.UNAVAILABLE
                    }
                }
            )
        } catch (e: Exception) {
            GALog.e(TAG, "NetworkListener: exception: " + e.message, e)
        }
        return networkState
    }
}