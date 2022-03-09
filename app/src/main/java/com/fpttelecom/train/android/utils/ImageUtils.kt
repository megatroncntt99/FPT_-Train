package com.fpttelecom.train.android.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.fpttelecom.train.android.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


/**
 * Created by Nguyễn Văn Vân on 12/9/2021.
 */
object ImageUtils {

    fun <S> loadImage(imageView: ImageView, source: S) {
        loadImage(imageView, source, R.drawable.bg_place_hole_image, R.drawable.bg_place_hole_image)
    }

    fun <S> loadImageNoError(imageView: ImageView, source: S) {
        loadImage(imageView, source, 0, 0)
    }

    fun <S> loadImage(imageView: ImageView, source: S?, defaultResource: Int, placeHole: Int) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .placeholder(placeHole)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .format(DecodeFormat.PREFER_RGB_565)
            .error(defaultResource)
            .fallback(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .fitCenter() //                .thumbnail(0.2f)
            .into(imageView)
    }

    fun <S> loadImage(imageView: ImageView, source: S?, defaultResource: Int) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .format(DecodeFormat.PREFER_RGB_565)
            .error(defaultResource)
            .fallback(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .fitCenter() //                .thumbnail(0.2f)
            .into(imageView)
    }


    fun <S> loadImageCircle(imageView: ImageView, source: S) {
        loadImageCircle(
            imageView,
            source,
            R.drawable.bg_place_hole_image_circle,
            R.drawable.bg_place_hole_image_circle
        )
    }

    fun <S> loadImageCircle(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Int
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .circleCrop()
            .placeholder(placeHole)
            .format(DecodeFormat.PREFER_RGB_565)
            .error(defaultResource)
            .fallback(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade()) //                .thumbnail(0.2f)
            .into(imageView)
    }

    fun <S> loadImageCircle(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Drawable?
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .circleCrop()
            .placeholder(placeHole)
            .format(DecodeFormat.PREFER_RGB_565)
            .error(defaultResource)
            .fallback(defaultResource) //                .thumbnail(0.2f)
            .into(imageView)
    }

    fun <S> loadImageRoundCorner(imageView: ImageView, source: S) {
        loadImageRoundCorner(
            imageView,
            source,
            R.drawable.bg_place_hole_image,
            R.drawable.bg_place_hole_image
        )
    }

    fun <S> loadImageRoundCorner12(imageView: ImageView, source: S) {
        loadImageRoundCorner12(
            imageView,
            source,
            R.drawable.bg_place_hole_image12,
            R.drawable.bg_place_hole_image12
        )
    }

    fun <S> loadImageRoundCorner16(imageView: ImageView, source: S) {
        loadImageRoundCorner16(imageView, source, 0, 0)
    }

    fun <S> loadImageRoundCorner8(imageView: ImageView, source: S) {
        loadImageRoundCorner8(
            imageView,
            source,
            R.drawable.bg_place_hole_image8,
            R.drawable.bg_place_hole_image8
        )
    }

    fun <S> loadImageRoundCorner8(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Int
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(
                GranularRoundedCorners(
                    ViewUtils.dpToPx(8f).toFloat(),
                    ViewUtils.dpToPx(8f).toFloat(), 0f, 0f
                )
            )
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(placeHole)
            .error(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(defaultResource) //                .thumbnail(0.2f)
            .into(imageView)
    }

    fun <S> loadImageRoundCorner10(imageView: ImageView, source: S) {
        loadImageRoundCorner10(
            imageView,
            source,
            R.drawable.bg_place_hole_image10,
            R.drawable.bg_place_hole_image10
        )
    }

    fun <S> loadImageRoundCorner10(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Int
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(
                GranularRoundedCorners(
                    ViewUtils.dpToPx(10f).toFloat(),
                    ViewUtils.dpToPx(10f).toFloat(), 0f, 0f
                )
            )
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(placeHole)
            .error(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(defaultResource) //                .thumbnail(0.2f)
            .into(imageView)
    }

    fun <S> loadImageRoundCorner12(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Int
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(
                GranularRoundedCorners(
                    ViewUtils.dpToPx(12f).toFloat(),
                    ViewUtils.dpToPx(12f).toFloat(),
                    ViewUtils.dpToPx(0f).toFloat(),
                    ViewUtils.dpToPx(0f).toFloat()
                )
            )
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(placeHole)
            .error(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(defaultResource)
            .thumbnail(0.2f)
            .into(imageView)
    }

    fun <S> loadImageRoundCorner16(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Int
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(
                GranularRoundedCorners(
                    ViewUtils.dpToPx(16f).toFloat(),
                    ViewUtils.dpToPx(16f).toFloat(),
                    ViewUtils.dpToPx(0f).toFloat(),
                    ViewUtils.dpToPx(0f).toFloat()
                )
            )
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(placeHole)
            .error(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(defaultResource)
            .thumbnail(0.2f)
            .into(imageView)
    }


    fun <S> loadImageRoundCorner(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Int
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(RoundedCorners(ViewUtils.dpToPx(4f)))
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(placeHole)
            .error(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(defaultResource) //                .thumbnail(0.2f)
            .into(imageView)
    }

    fun loadImageDrawable(context: Context?, view: View, drawable: Int) {
        Glide.with(context!!)
            .load(drawable)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .format(DecodeFormat.PREFER_RGB_565)
            .into(object : SimpleTarget<Drawable?>() {

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    if (view is ImageView) {
                        view.setImageDrawable(resource)
                    } else {
                        view.background = resource
                    }
                }
            })
    }

    fun <S> loadDrawable(imageView: ImageView, source: S?) {
        Glide.with(imageView.context)
            .load(if (source == null) 0 else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .format(DecodeFormat.PREFER_RGB_565)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }


    fun imageToBitmap(image: ImageView): Bitmap? {
        return if (image.drawable is BitmapDrawable) {
            val bitmapDrawable = image.drawable as BitmapDrawable
            bitmapDrawable.bitmap
        } else {
            if (image.drawingCache == null) image.buildDrawingCache()
            image.drawingCache
        }
    }

    fun imageToBytes(image: ImageView, maxWidthPx: Int): ByteArray? {
        return bitmapToBytes(imageToBitmap(image), maxWidthPx)
    }

    fun bitmapToBytes(bitmap: Bitmap?, maxWidthPx: Int): ByteArray? {
        if (bitmap == null) return null
        val stream = ByteArrayOutputStream()
        resizeBitmap(bitmap, maxWidthPx).compress(Bitmap.CompressFormat.JPEG, 95, stream)
        val data = stream.toByteArray()
        try {
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return data
    }

    fun bytesToBitmap(data: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    fun resizeBitmap(bitmap: Bitmap, maxWidthPx: Int): Bitmap {
        val currentWidth = bitmap.width
        val ratio = if (currentWidth > maxWidthPx) (currentWidth / maxWidthPx).toFloat() else 1f
        val resultWidth = if (ratio == 1f) currentWidth else Math.round(currentWidth / ratio)
        val resultHeight = if (ratio == 1f) bitmap.height else Math.round(bitmap.height / ratio)
        return Bitmap.createScaledBitmap(bitmap, resultWidth, resultHeight, true)
    }

    fun clear(context: Context?) {
        Glide.get(context!!).clearMemory()
    }

    fun resizeImageAsync(data: ByteArray, maxSize: Int, listener: ResizeListener?) {
        object : AsyncTask<ByteArray?, Void?, ByteArray?>() {

            override fun onPostExecute(bytes: ByteArray?) {
                super.onPostExecute(bytes)
                if (listener == null) return
                listener.onDone(bytes)
            }

            override fun doInBackground(vararg p0: ByteArray?): ByteArray? {
                return bitmapToBytes(p0.get(0)?.let { bytesToBitmap(it) }, maxSize)
            }
        }.execute(data)
    }

    fun getLink(sourceUrl: String?): String? {
//        return sourceUrl.replace("https","http");
        return sourceUrl
    }

    fun loadImageIconUrl(imageView: ImageView, url: String?, defaultResource: Int, placeHole: Int) {
        Glide.with(imageView.context).load(url)
            .placeholder(placeHole)
            .error(defaultResource)
            .fallback(defaultResource)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //.format(DecodeFormat.PREFER_RGB_565)
            .transition(DrawableTransitionOptions.withCrossFade()) //                .thumbnail(0.05f)
            .override(720, 720)
            .into(imageView)
    }

    interface ResizeListener {
        fun onDone(result: ByteArray?)
    }

    fun <S> loadImageRoundCorner2(imageView: ImageView, source: S) {
        loadImageRoundCorner2(
            imageView,
            source,
            R.drawable.bg_place_hole_image2,
            R.drawable.bg_place_hole_image2
        )
    }

    fun <S> loadImageRoundCorner2(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Int
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(
                GranularRoundedCorners(
                    ViewUtils.dpToPx(2f).toFloat(),
                    ViewUtils.dpToPx(2f).toFloat(), 0f, 0f
                )
            )
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(placeHole)
            .error(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(defaultResource) //                .thumbnail(0.2f)
            .into(imageView)
    }

    fun <S> loadImageRoundAllCorner8(imageView: ImageView, source: S) {
        loadImageRoundAllCorner8(
            imageView,
            source,
            R.drawable.bg_place_hole_image_all_corner_8,
            R.drawable.bg_place_hole_image_all_corner_8
        )
    }

    fun <S> loadImageRoundAllCorner8(
        imageView: ImageView,
        source: S?,
        defaultResource: Int,
        placeHole: Int
    ) {
        Glide.with(imageView.context)
            .load(if (source == null) defaultResource else if (source is String) source else if (source is Int) source else if (source is Uri) source else if (source is File) source else if (source is ByteArray) source else if (source is Bitmap) source else if (source is Drawable) source else source)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(
                GranularRoundedCorners(
                    ViewUtils.dpToPx(8f).toFloat(),
                    ViewUtils.dpToPx(8f).toFloat(),
                    ViewUtils.dpToPx(8f).toFloat(),
                    ViewUtils.dpToPx(8f).toFloat()
                )
            )
            .format(DecodeFormat.PREFER_RGB_565)
            .placeholder(placeHole)
            .error(defaultResource)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(defaultResource) //                .thumbnail(0.2f)
            .into(imageView)
    }
}