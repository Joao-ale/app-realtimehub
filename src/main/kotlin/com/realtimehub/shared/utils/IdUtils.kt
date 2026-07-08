package com.realtimehub.shared.utils

import java.util.UUID

object IdUtils {
	fun generateId(): String {
		return UUID.randomUUID().toString()
	}
}