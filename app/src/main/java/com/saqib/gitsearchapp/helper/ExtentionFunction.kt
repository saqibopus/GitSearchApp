package com.saqib.gitsearchapp.helper

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.saqib.gitsearchapp.R
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImage(context: Context, url: String?) {
    url?.let {
        Picasso
            .get()
            .load(it)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(this)
    }
}
fun String?.removeHtmlTags(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        "${Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)}"
    } else {
        "${Html.fromHtml(this)}"
    }
}

private fun convertBase64ToBitmap(b64: String): Bitmap {
    val imageAsBytes = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
}

fun Activity.launchFeedback() {
    val packageName = packageName
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
    startActivity(intent)
}

fun Activity.navigateTo(bundle: Bundle?, classType: Class<out Any>) {
    if (bundle != null) {
        this.startActivity(Intent(this, classType).putExtras(bundle))
    } else {
        this.startActivity(Intent(this, classType))
        Logs.p("Bundle is null in ${this.localClassName}")
    }
}

fun Activity.navigateToAndFinish(bundle: Bundle?, classType: Class<out Any>) {
    if (bundle != null) {
        this.startActivity(Intent(this, classType).putExtras(bundle))
        this.finishAffinity()
    } else {
        this.startActivity(Intent(this, classType))
        this.finishAffinity()
        Logs.p("Bundle is null in ${this.localClassName}")
    }
}

/*fun String.toDate(
    dateFormat: String = "yyyy-MM-dd HH:mm:ss",
    timeZone: TimeZone = TimeZone.getTimeZone("GMT")
): Date {
    val parser = SimpleDateFormat(dateFormat)
    parser.timeZone = timeZone
    return parser.parse(this)
}*/

fun String.toDateNoTimeZone(dateFormat: String = "yyyy-MM-dd HH:mm:ss"): String {
    val date = SimpleDateFormat(dateFormat).parse(this)
    return SimpleDateFormat(dateFormat).format(date)
}

fun String.toDateLocalTimeZone(
    dateFormat: String = "yyyy-MM-dd HH:mm:ss",
    timeZone: TimeZone = TimeZone.getDefault()
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.FormatDate(
    dateFormat: String = "yyyy-MM-dd HH:mm:ss",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): String {
    val parser = SimpleDateFormat(dateFormat)
    parser.timeZone = timeZone
    return parser.format(this)
}

fun String.toDate(
    dateFormat: String = "yyyy-MM-dd HH:mm:ss",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): Date {
    //  Logs.p("time zone : $timeZone")
    //  Logs.p("time zone : ${timeZone.id}")
    val parser = SimpleDateFormat(dateFormat)
    parser.timeZone = timeZone
    // Logs.p("time zone : ${parser.timeZone.id}")

    val date = parser.parse(this)
    val parserLocal = SimpleDateFormat(dateFormat)

    return parserLocal.parse(date.formatTo("yyyy-MM-dd HH:mm:ss"))
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun String.toUTC(dateFormat: String = "yyyy-MM-dd HH:mm:ss") {
    val parser = SimpleDateFormat(dateFormat)
    parser.timeZone = TimeZone.getTimeZone("UTC")
    val date = parser.parse(this)
}

fun String.toDateGMT(
    dateFormat: String = "yyyy-MM-dd HH:mm:ss",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): Date {
    val parser = SimpleDateFormat(dateFormat)
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.formatToGMT(dateFormat: String, timeZone: TimeZone = TimeZone.getTimeZone("UTC")): String {
    val formatter = SimpleDateFormat(dateFormat)
    //formatter.timeZone = timeZone
    return formatter.format(this)
}

fun Date.localDateFormat(context: Context): String {
    val formatter = SimpleDateFormat(getLocalDateFormat(context), Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(this)
}

fun Date.formatToShortDate(): String {
    return DateFormat.getDateInstance(DateFormat.LONG).format(this)
}

fun Date.formatToShortDateTime(): String {
    return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.MEDIUM).format(this)
}

fun getLocalDateFormat(context: Context): String {
    val dateFormat = android.text.format.DateFormat.getDateFormat(context)


    return (dateFormat as SimpleDateFormat).toLocalizedPattern()
}

fun Date.inBetween(startDate: Date, endDate: Date): Boolean {
    Logs.p("startDate - ${startDate.formatTo("yyyy-MM-dd HH:mm:ss")}")
    Logs.p("endDate - ${endDate.formatTo("yyyy-MM-dd HH:mm:ss")}")
    Logs.p("currentDates - ${this.formatTo("yyyy-MM-dd HH:mm:ss")}")
    return this.after(startDate) && this.before(endDate)
}

fun String.formatToPhone(): String {
    return if (this.length >= 10) {
        val stringBuilder = StringBuilder(this)
        stringBuilder.insert(3, "-")
        stringBuilder.insert(7, "-")
        stringBuilder.toString()
    } else {
        ""
    }

}

fun roundOffDecimal(number: Double): Double? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toDouble()
}

fun roundOffDecimalTwoDigit(number: Double): Double? {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toDouble()
}

fun String.alertOk(msg: String, context: Context) {
    val dialogBuilder = AlertDialog.Builder(context)
    dialogBuilder.setMessage(msg)
        .setCancelable(false)
        .setNegativeButton("OK", DialogInterface.OnClickListener { dialog, id ->
            dialog.cancel()
        })

    val alert = dialogBuilder.create()
    alert.setTitle("Alert")
    alert.show()
}

fun ImageView.setSvgColor(context: Context, color: Int) =
    this.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)

fun Activity.navigateToAddressOnMap(address: String?) {
    val map = "http://maps.google.co.in/maps?q=${address}"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
    this.startActivity(intent)
}

fun Activity.dialNumber(number: String?) {
    val uri = "tel:$number".trim()
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(uri))
    this.startActivity(intent)
}

fun defaultDateFormat(context: Context): String {
    val dateFormat = android.text.format.DateFormat.getDateFormat(context)
    val pattern = (dateFormat as SimpleDateFormat).toLocalizedPattern()
    return pattern
}

fun fromHtml(str: String): Spanned {
    return if (Build.VERSION.SDK_INT >= 24) Html.fromHtml(
        str,
        Html.FROM_HTML_MODE_LEGACY
    ) else Html.fromHtml(str)
}


