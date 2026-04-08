package dev.septianbeneran.technicaltest.core.interceptor

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.annotation.RequiresPermission
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val context: Context) : Interceptor {
    @RequiresPermission(ACCESS_NETWORK_STATE)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable(context)) {
            throw NoInternetException()
        }
        return chain.proceed(chain.request())
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}

class NoInternetException : IOException("No internet connection")
