import android.util.Log
import com.wellbeing.pharmacyjob.BuildConfig

object AppLogger {

    private const val TAG = "AppLogger"

    // Debug log
    fun d(tag: String = TAG, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    // Error log with optional throwable
    fun e(tag: String = TAG, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) {
                Log.e(tag, message, throwable)
            } else {
                Log.e(tag, message)
            }
        }
    }

    // Info log
    fun i(tag: String = TAG, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    // Warning log
    fun w(tag: String = TAG, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }

    // Verbose log
    fun v(tag: String = TAG, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }
}

// Usaga:  AppLogger.d(debug log)
// Usaga:  AppLogger.e("UncaughtException", "Uncaught exception in thread: $thread", throwable)

