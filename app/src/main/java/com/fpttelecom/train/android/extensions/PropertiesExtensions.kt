package com.fpttelecom.train.android.extensions

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputFilter
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.api.RequestState
import com.fpttelecom.train.android.api.UiState
import com.fpttelecom.train.android.utils.Constants.TIME_OUT
import com.fpttelecom.train.android.utils.ImageUtils
import com.fpttelecom.train.android.utils.LogCat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform


fun View.visible() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.gone() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

fun View.disable() {
    alpha = 0.5f
    isEnabled = false
}

fun View.enable() {
    alpha = 1f
    isEnabled = true
}

fun TextView.visible(error: String) {
    visibility = View.VISIBLE
    text = error
}

fun EditText.requestError() {
    requestFocus()
}

fun EditText.normalStatus() {
    background = null
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.GONE || visibility == View.INVISIBLE
}

fun EditText.limitLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}


@SuppressLint("ClickableViewAccessibility")
fun View.onClick(unit: () -> Unit) {
    val gestureListener = GestureDetector(context, GestureListener())
    if (this is Button) {
        setOnTouchListener { _: View?, e: MotionEvent ->
            if (gestureListener.onTouchEvent(e)) {
                unit.invoke()
            }
            return@setOnTouchListener false
        }
    } else {
        setOnTouchListener { _: View?, e: MotionEvent ->
            if (gestureListener.onTouchEvent(e)) {
                unit.invoke()
            }
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    alpha = 0.5f
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    alpha = 1.0f
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_CANCEL -> {
                    alpha = 1.0f
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
}

fun <T> View.debounce(action: (T) -> Unit): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (debounceJob == null) {
            debounceJob = CoroutineScope(Dispatchers.Default).launch {
                action(param)
                delay(500)
                debounceJob = null
            }
        }
    }
}

fun View.debounce(action: () -> Unit) {
    var debounceJob: Job? = null
    if (debounceJob == null) {
        debounceJob = CoroutineScope(Dispatchers.Default).launch {
            action.invoke()
            delay(500)
            debounceJob = null
        }
    }
}

fun View.loadImage(url: Any?, drawable: Drawable? = null) {
//    CoroutineScope(Dispatchers.Default).launch {
//        Coil.imageLoader(context).execute(ImageRequest.Builder(context)
//            .data(url)
//            .target(
//                onStart = { placeholder ->
//                    background = placeholder
//                },
//                onSuccess = { result ->
//                    background = result
//                },
//                onError = { error ->
//                    background = error
//                }
//            ).placeholder(drawable)
//            .error(drawable)
//            .crossfade(true)
//            .transformations(
//                RoundedCornersTransformation(
//                    context.resources.getDimension(R.dimen.dp10),
//                    context.resources.getDimension(R.dimen.dp10),
//                    context.resources.getDimension(R.dimen.dp10),
//                    context.resources.getDimension(R.dimen.dp10)
//                )
//            )
//            .size(ViewSizeResolver(this@loadImage))
//            .build())
//    }
}

fun View.loadImageCircle(url: Any?, drawable: Drawable? = null) {
//    CoroutineScope(Dispatchers.Default).launch {
//        Coil.imageLoader(context).execute(ImageRequest.Builder(context)
//            .data(url)
//            .target(
//                onStart = { placeholder ->
//                    background = placeholder
//                },
//                onSuccess = { result ->
//                    background = result
//                },
//                onError = { error ->
//                    background = error
//                }
//            ).placeholder(drawable)
//            .error(drawable)
//            .crossfade(true)
//            .transformations(CircleCropTransformation())
//            .size(ViewSizeResolver(this@loadImageCircle))
//            .build())
//    }
}

fun View.loadImage(url: Any?) {
//    CoroutineScope(Dispatchers.Default).launch {
//        Coil.imageLoader(context).execute(ImageRequest.Builder(context)
//            .data(url)
//            .target(
//                onStart = { placeholder ->
//                    background = placeholder
//                },
//                onSuccess = { result ->
//                    background = result
//                },
//                onError = { error ->
//                    background = error
//                }
//            )
//            .crossfade(true)
//            .size(ViewSizeResolver(this@loadImage))
//            .build())
//    }
}

private class GestureListener : SimpleOnGestureListener() {
    override fun onSingleTapUp(event: MotionEvent): Boolean {
        return true
    }
}

fun RecyclerView.getCurrentPosition(): Int {
    return (this.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
}

fun Fragment.push(id: Int) {
//    findNavController().navigate(id)
}

fun Fragment.push(id: Int, bundle: Bundle) {
//    findNavController().navigate(id, bundle)
}
//
//fun Fragment.push(id: NavDirections) {
//    findNavController().navigate(id)
//}

fun Fragment.pop() {
//    findNavController().popBackStack()
}

fun Fragment.onBackPress(unit: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
        unit.invoke()
    }
}

suspend fun <T> timeOutSuspend(block: suspend CoroutineScope.() -> T): T {
    return withTimeout(TIME_OUT, block)
}

fun Fragment.launchWhenCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenCreated { block.invoke(this) }
}

fun Fragment.initLaunch(vararg listBlock: suspend CoroutineScope.() -> Unit) {
    listBlock.forEach {
        launchWhenCreated { it.invoke(this) }
    }
}

fun <T> handleStateFlow(
    state: UiState<T>,
    onSuccess: (() -> Unit)? = null,
    onError: (() -> Unit)? = null,
    onNon: (() -> Unit)? = null
) {
    when (state.state) {
        RequestState.SUCCESS -> onSuccess?.invoke()
        RequestState.ERROR -> onError?.invoke()
        RequestState.NON -> onNon?.invoke()
    }
}

inline fun getValueAnimator(
    forward: Boolean = true,
    duration: Long,
    interpolator: TimeInterpolator,
    crossinline updateListener: (progress: Float) -> Unit
): ValueAnimator {
    val a =
        if (forward) ValueAnimator.ofFloat(0f, 1f)
        else ValueAnimator.ofFloat(1f, 0f)
    a.addUpdateListener { updateListener(it.animatedValue as Float) }
    a.duration = duration
    a.interpolator = interpolator
    return a
}

@FlowPreview
fun <T> Flow<T>.onExpiredToken(): Flow<T> = transform { value ->
    if (value is UiState<*> && value.code == 401) {
        /*Do something*/
    }
    LogCat.d("AAAAA onExpiredToken ${(value as UiState<*>).code}")
    return@transform emit(value)
}

suspend fun <T> checkStates(
    state: UiState<T>,
    success: suspend () -> Unit = {},
    fail: suspend () -> Unit = {}
) {
    if (state.state == RequestState.SUCCESS) success.invoke()
    else fail.invoke()
}

suspend fun <T, R> checkState(state: UiState<T>, success: suspend () -> R, fail: suspend () -> R) =
    flow {
        if (state.state == RequestState.SUCCESS) emit(success.invoke())
        else emit(fail.invoke())
    }

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
    ImageUtils.loadImage(this, url)
}

@BindingAdapter("loadImageCircle",)
fun ImageView.loadImageCircle(urlImage: String, ) {
    ImageUtils.loadImageCircle(this, urlImage)
}
