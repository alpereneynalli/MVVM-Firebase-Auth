package net.firebase_auth.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { continuation ->
        addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                continuation.resumeWithException(task.exception!!)
            } else {
                continuation.resume(task.result)
            }
        }
    }
}