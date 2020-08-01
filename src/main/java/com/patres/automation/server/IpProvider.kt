package com.patres.automation.server

import java.net.DatagramSocket

object IpProvider {

    fun getIp(): String {
        val datagramSocket = DatagramSocket().apply {
            connect(java.net.InetAddress.getByName("8.8.8.8"), 10002)
        }
        return datagramSocket.localAddress.hostAddress
    }
}