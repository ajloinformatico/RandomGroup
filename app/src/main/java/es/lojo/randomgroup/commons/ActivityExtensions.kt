package es.lojo.randomgroup.commons

import android.app.Activity
import android.content.Intent
import android.net.Uri
import es.lojo.randomgroup.R

fun Activity.manageWebViewInfolojo() {
    startActivity(
        Intent(Intent.ACTION_VIEW).setData(
            Uri.parse(
                resources.getString(
                    R.string.infolojo_complete_url
                )
            )
        )
    )
}