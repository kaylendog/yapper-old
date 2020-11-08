package com.dumbdogdiner.stickychat.bukkit.redis

import com.dumbdogdiner.stickychat.bukkit.WithPlugin
import com.google.common.io.ByteStreams
import java.nio.charset.Charset
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.JedisPubSub

/**
 * Handles pub/sub messaging between servers.
 */
class RedisMessenger : WithPlugin, JedisPubSub() {
    private lateinit var pool: JedisPool

    /**
     * Initialize the Redis messenger and create the connection.
     */
    fun init() {
        this.logger.info("Initializing Redis Pub-Sub service...")
        this.pool = JedisPool(JedisPoolConfig())
    }

    /**
     * Close the redis messenger and all connections.
     */
    fun close() {
        this.logger.info("Closing the Redis Pub-Sub service...")
        pool.close()
    }

    /**
     * Handle a message received from Redis.
     */
    override fun onMessage(channel: String, message: String) {
        val incoming = message.toByteArray(Charset.defaultCharset())
        val packet = PacketBuilder.decodePacket(ByteStreams.newDataInput(incoming))
    }

    /**
     * Send a raw message via redis pub-sub.
     */
    fun sendRaw(channel: String, data: ByteArray): Long {
        return this.pool.resource.publish(channel.toByteArray(Charset.forName("UTF-8")), data)
    }
}
