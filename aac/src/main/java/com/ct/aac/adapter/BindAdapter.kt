package com.ct.aac.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.bumptech.glide.Glide

/**
 * 绑定适配器
 * 1.系统自动选择方法
 * 2.自定义方法名称
 * 3.提供自定义逻辑
 * 4.自定义转换
 * */
//@BindingMethods(
//    value = [
//        BindingMethod(
//            type = ImageView::class,
//            attribute = "android:tint",
//            method = "setImageTintList"
//        )
//    ]
//)
//class BindMethod


@BindingAdapter("android:paddingLeft")
fun setPaddingLeft(view: View, padding: Int) {
    Log.e("TAG", "自定义逻辑")
    view.setPadding(padding, 20, 0, 0)
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
   // Log.e("TAG", "setImageUrl")
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

//可以使用接收多个特性的适配器
//@BindingAdapter("imageUrl", "error")
//fun setImageUrl(imageView: ImageView, url: String, error: Drawable) {
//    Log.e("TAG", "setImageUrl--多个特性--url \r\n -- error")
//    Glide.with(imageView)
//        .load(url)
//        .error(error)
//        .into(imageView)
//}

//如果希望在设置了任意特性时调用适配器，则可以将适配器的可选 requireAll 标记设置为 false
//@BindingAdapter(value = ["imageUrl", "error"], requireAll = false)
//fun setImageUrl(imageView: ImageView, url: String, error: Drawable) {
//    Glide.with(imageView).load(url)
//        .apply {
//            if (error != null)
//                this.error(error)
//
//        }.into(imageView)
//}


@BindingAdapter("android:paddingLeft")
fun setPaddingLeft(view: View, oldPadding: Int, newPadding: Int) {
    Log.e("TAG", "绑定旧值新值--$oldPadding -- $newPadding")
    if (oldPadding != newPadding) {
        view.setPadding(newPadding, 0, 0, 0)
    }
}

@BindingAdapter("android:onLayoutChange")
fun setOnLayoutChangeListener(
    view: View,
    oldValue: View.OnLayoutChangeListener?,
    newValue: View.OnLayoutChangeListener?
) {
    Log.e("TAG", "setOnLayoutChangeListener $view -- $oldValue -- $newValue")
    if (oldValue != null)
        view.removeOnLayoutChangeListener(oldValue)
    if (newValue != null)
        view.addOnLayoutChangeListener(newValue)
}



//@BindingAdapter("android:onCheckedChange")
//fun setOnCheckedChangeListener(view: CheckBox){
//
//}

/**
 * 自定义转换
 * setBackground为例
 * */
@BindingConversion
fun converColorToDrawable(color: String) = ColorDrawable(Color.parseColor("#$color"))