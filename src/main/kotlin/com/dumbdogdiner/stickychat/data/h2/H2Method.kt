package com.dumbdogdiner.stickychat.data.h2

import com.dumbdogdiner.stickychat.data.StorageMethod

/**
 * Stores data locally using H2.
 */
class H2Method : StorageMethod {
    override fun init() {
        TODO("Not yet implemented")
    }

    override fun getAllGroupFormats(): HashMap<String, String> {
        TODO("Not yet implemented")
    }

    override fun setGroupFormat(group: String, format: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getGroupFormat(group: String): String? {
        TODO("Not yet implemented")
    }
}
