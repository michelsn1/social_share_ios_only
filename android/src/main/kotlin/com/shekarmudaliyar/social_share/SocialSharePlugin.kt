package com.shekarmudaliyar.social_share

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.FileProvider
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.io.File

// No Android so os Stories e a checagem de apps; o resto do plugin e iOS-only.
class SocialSharePlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var channel: MethodChannel
    private var activity: Activity? = null
    private var context: Context? = null

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext
        channel = MethodChannel(binding.binaryMessenger, "social_share")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "shareInstagramStory", "shareFacebookStory" -> shareStory(call, result)
            "checkInstalledApps" -> result.success(
                mapOf(
                    "instagram" to isInstalled("com.instagram.android"),
                    "facebook" to isInstalled("com.facebook.katana"),
                )
            )
            else -> result.notImplemented()
        }
    }

    private fun shareStory(call: MethodCall, result: Result) {
        val activity = activity
        val stickerImage: String? = call.argument("stickerImage")
        val backgroundImage: String? = call.argument("backgroundImage")
        val backgroundVideo: String? = call.argument("backgroundVideo")
        if (activity == null || (stickerImage == null && backgroundImage == null && backgroundVideo == null)) {
            result.success("error")
            return
        }

        val instagram = call.method == "shareInstagramStory"
        val appPackage = if (instagram) "com.instagram.android" else "com.facebook.katana"
        val intent = Intent(
            if (instagram) "com.instagram.share.ADD_TO_STORY" else "com.facebook.stories.ADD_TO_STORY"
        )

        // O Dart (reSaveImage) copia os arquivos para o cacheDir e manda so o nome.
        val authority = activity.packageName + ".com.shekarmudaliyar.social_share"
        fun uriOf(name: String) = FileProvider.getUriForFile(activity, authority, File(activity.cacheDir, name))

        fun grant(uri: android.net.Uri) =
            activity.grantUriPermission(appPackage, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

        intent.type = "image/*"
        stickerImage?.let {
            val sticker = uriOf(it)
            intent.putExtra("interactive_asset_uri", sticker)
            grant(sticker)
        }
        backgroundImage?.let {
            val uri = uriOf(it)
            intent.setDataAndType(uri, "image/*")
            grant(uri)
        }
        backgroundVideo?.let {
            val uri = uriOf(it)
            intent.setDataAndType(uri, "video/*")
            grant(uri)
        }

        val appId: String? = call.argument("appId")
        intent.putExtra("source_application", appId)
        if (!instagram) intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", appId)
        intent.putExtra("content_url", call.argument<String>("attributionURL"))
        intent.putExtra("top_background_color", call.argument<String>("backgroundTopColor"))
        intent.putExtra("bottom_background_color", call.argument<String>("backgroundBottomColor"))
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        if (activity.packageManager.resolveActivity(intent, 0) == null) {
            result.success("error")
            return
        }
        activity.startActivity(intent)
        result.success("success")
    }

    private fun isInstalled(packageName: String): Boolean = try {
        context!!.packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivity() {
        activity = null
    }
}
