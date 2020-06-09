package com.example.myfirstapp.presentation.global.utils

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.content.ContextCompat
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.utils.AppConstants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KClass


const val PREF_NAME_FIELD = "stateFiled"

fun Context.toast(message: String, longToast: Boolean = true) {
    Toast.makeText(this, message, if (longToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

enum class MessageType {
    ALERT,
    TOAST
}

fun Context.alert(
    title: String? = null,
    message: String? = getString(R.string.something_went_wrong),
    positiveButton: (() -> Unit?)? = null,
    cancelable: Boolean = true,
    type: MessageType = MessageType.ALERT
) {
    when (type) {
        MessageType.TOAST -> {
            this.toast(message ?: getString(R.string.something_went_wrong))
        }
        MessageType.ALERT -> {
            val dialog = AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    dialog.dismiss()
                    positiveButton?.invoke()
                }
                .setCancelable(cancelable)
                .create()
            if (title != null)
                dialog.setTitle(title)

            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent))
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent))
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            }
            dialog.show()
        }
    }
}

fun Context.alertWithActions(
    title: String,
    message: String,
    cancelable: Boolean = true,
    positiveButtonCallback: () -> Unit,
    negativeButtonCallback: () -> Unit,
    positiveText: String? = resources.getString(android.R.string.yes),
    negativeText: String? = getString(R.string.cancel)
) {

    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveText) { dialog, _ ->
            positiveButtonCallback()
            dialog.dismiss()
        }
        .setNegativeButton(negativeText) { dialog, _ ->
            negativeButtonCallback()
            dialog.dismiss()
        }
        .setCancelable(cancelable)
        .create()

    dialog.setOnShowListener {
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }
    dialog.show()
}


fun Context.pickTime(
    positiveButton: ((String) -> Unit)? = null,
    dismissCallback: (() -> Unit)? = null,
    chooseCallback: ((Boolean) -> Unit)? = null
): String {
    var timeString = ""
    val cal = Calendar.getInstance()
    val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        timeString = SimpleDateFormat(AppConstants.TIME_PATTERN).format(cal.time)
        positiveButton?.invoke(timeString)
    }
    val dialog = TimePickerDialog(
        this,
        timeSetListener,
        cal.get(Calendar.HOUR_OF_DAY),
        cal.get(Calendar.MINUTE),
        true
    )

    dialog.setOnDismissListener {
        dismissCallback?.invoke()
    }

    dialog.setOnCancelListener{
        chooseCallback?.invoke(true)
    }

    dialog.show()

    return timeString
}


/**
 * В параметр [className] передаем обект класса
 * для того чтобы система понимала в каком окне
 * вы хотите сохранить сосояние
 */
fun <T : Any> Context.saveStateField(className: KClass<T>, keyField: String?, valueField: String) {
    val sharedPreferences: SharedPreferences = this.getSharedPreferences(
        PREF_NAME_FIELD + className.simpleName,
        Context.MODE_PRIVATE
    )
    sharedPreferences.edit().putString(keyField, valueField).apply()
}

/**
 * Получение сохраненного состояние поля
 * Для получения необходжимо передать тип класса и ключь получаемого значения
 */
fun <T : Any> Context.getStateField(className: KClass<T>, keyField: String?): String {
    val sharedPreferences: SharedPreferences = this.getSharedPreferences(
        PREF_NAME_FIELD + className.simpleName,
        Context.MODE_PRIVATE
    )
    return sharedPreferences.getString(keyField, "").orEmpty()
}

/**
 * Обязательная вызывать в onDestroy()
 * Для избежания получения ложного значения в фильтре
 */
fun <T : Any> Context.clearStateField(className: KClass<T>) {
    val sharedPreferences: SharedPreferences = this.getSharedPreferences(
        PREF_NAME_FIELD + className.simpleName,
        Context.MODE_PRIVATE
    )
    sharedPreferences.edit().clear().apply()
}
