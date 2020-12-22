package com.dumbdogdiner.stickychat.bukkit.redis

import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import java.util.Base64
import kotlin.concurrent.thread
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.JedisPubSub
import redis.clients.jedis.exceptions.JedisConnectionException

/**
 * Handles pub/sub messaging between servers.
 */
class RedisMessenger : WithPlugin, JedisPubSub() {
    companion object {
        const val CHANNEL_NAME = "stickychat"
    }

    private var initialized = false

    private var pool: JedisPool? = null
    private var subscribeJob: Thread? = null

    /**
     * Initialize the Redis messenger and create the connection.
     */
    fun init() {
        if (initialized) {
            return
        }

        this.logger.info("[REDIS] Initializing Redis Pub-Sub service...")
        try {
            this.pool = JedisPool(JedisPoolConfig())
            if (!this.pool!!.resource.isConnected) {
                this.logger.warning("[REDIS] Failed to connect to Redis - unknown error")
            }
        } catch (e: JedisConnectionException) {
            this.logger.warning("[REDIS] Failed to connect to Redis - incorrect connection details")
            e.printStackTrace()
            this.logger.warning("[REDIS] Redis pub/sub has been disabled")
            return
        }

        // run the subscription in a separate thread - it's blocking
        subscribeJob = thread {
            try {
                this.pool!!.resource.subscribe(this, "stickychat")
            } catch (e: InterruptedException) {
                this.logger.info("[REDIS] Redis subscription thread closed")
            }
        }

        this.initialized = true
    }

    /**
     * Close the redis messenger and all connections.
     */
    fun close() {
        if (!initialized) {
            return
        }

        this.logger.info("[REDIS] Closing the Redis Pub-Sub service...")
        this.subscribeJob?.interrupt()
        pool?.close()
    }

    /**
     * Handle a message received from Redis.
     */
    override fun onMessage(channel: String, message: String) {
        if (channel != CHANNEL_NAME) {
            return
        }

        val packet = PacketBuilder.decodePacket(Base64.getDecoder().decode(message))
        this.logger.info("[REDIS] Received packet '${packet.uniqueId}' from '${packet.sender}' - type=${packet.type.name}")

        when (packet.type) {
            PacketBuilder.Type.DM_MESSAGE -> {}
            PacketBuilder.Type.MESSAGE -> {}
        }
    }

    /**
     * Send a raw message via redis pub-sub.
     */
    fun sendRaw(channel: String, data: ByteArray): Long {
        if (!initialized) {
            return -1
        }

        return this.pool!!.resource.publish(channel, Base64.getEncoder().encodeToString(data))
    }
}
