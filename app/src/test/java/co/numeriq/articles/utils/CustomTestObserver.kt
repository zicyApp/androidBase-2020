package co.numeriq.articles.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class CustomTestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = CustomTestObserver<T>().also {
    observeForever(it)
}