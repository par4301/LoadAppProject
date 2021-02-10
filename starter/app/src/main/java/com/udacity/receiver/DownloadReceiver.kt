package com.udacity.receiver

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.udacity.ButtonState
import com.udacity.R
import java.io.File


class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
    }

//    private val receiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent) {
//            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
//            val action = intent.action
//
//            if (downloadID == id) {
//                if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
//                    val query = DownloadManager.Query()
//                    query.setFilterById(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
//                    val manager = context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//                    val cursor: Cursor = manager.query(query)
//                    if (cursor.moveToFirst()) {
//                        if (cursor.count > 0) {
//                            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
//                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
//                                loadingButton.setLoadingButtonState(ButtonState.Completed)
//                                notificationManager.sendNotification(selectedGitHubRepository.toString(), applicationContext, "Complete")
//                            } else {
//                                loadingButton.setLoadingButtonState(ButtonState.Completed)
//                                notificationManager.sendNotification(selectedGitHubRepository.toString(), applicationContext, "Failed")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    // Get the URI from the selected git hub repository and download it otherwise provide a text that a file is not downloaded and don't call download.
//    private fun download() {
//        loadingButton.setLoadingButtonState(ButtonState.Clicked)
//
//        if (selectedGitHubRepository != null) {
//            loadingButton.setLoadingButtonState(ButtonState.Loading)
//            notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
//            createChannel(getString(R.string.githubRepo_notification_channel_id), getString(R.string.githubRepo_notification_channel_name))
//
//            var file = File(getExternalFilesDir(null), "/repos")
//
//            if (!file.exists()) {
//                file.mkdirs()
//            }
//
//            val request =
//                    DownloadManager.Request(Uri.parse(selectedGitHubRepository))
//                            .setTitle(getString(R.string.app_name))
//                            .setDescription(getString(R.string.app_description))
//                            .setRequiresCharging(false)
//                            .setAllowedOverMetered(true)
//                            .setAllowedOverRoaming(true)
//                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/repos/repository.zip")
//
//
//            val downloadManager = getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
//            downloadID =
//                    downloadManager.enqueue(request)// enqueue puts the download request in the queue.
//        } else {
//            loadingButton.setLoadingButtonState(ButtonState.Completed)
//            showToast(getString(R.string.noRepotSelectedText))
//        }
//    }
//
//    companion object {
//        private const val CHANNEL_ID = "channelId"
//    }
//
//    // Disable the button while the animation is running
//    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
//        addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationStart(animation: Animator?) {
//                view.isEnabled = false
//            }
//            override fun onAnimationEnd(animation: Animator?) {
//                view.isEnabled = true
//            }
//        })
//    }

}