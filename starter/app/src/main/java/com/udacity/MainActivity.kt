package com.udacity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.udacity.util.createChannel
import com.udacity.util.sendNotification
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private var selectedGitHubUrl: String? = null
    private var selectedGitHubDescription: String? = null
    lateinit var loadingButton: LoadingButton

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        loadingButton = findViewById(R.id.loadingButton)
        loadingButton.setLoadingButtonState(ButtonState.Completed)
        loadingButton.setOnClickListener {
            download()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val action = intent.action

            if (downloadID == id) {
                if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                    val query = DownloadManager.Query()
                    query.setFilterById(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
                    val manager = context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    val cursor: Cursor = manager.query(query)
                    if (cursor.moveToFirst()) {
                        if (cursor.count > 0) {
                            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                loadingButton.setLoadingButtonState(ButtonState.Completed)
                                notificationManager.sendNotification(
                                        getString(R.string.notification_description),
                                        applicationContext,
                                        selectedGitHubDescription.toString(),
                                        "Success"
                                )
                            } else {
                                loadingButton.setLoadingButtonState(ButtonState.Completed)
                                notificationManager.sendNotification(
                                        getString(R.string.notification_description),
                                        applicationContext,
                                        selectedGitHubDescription.toString(),
                                        "Failed"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Get the URI from the selected git hub repository and download it otherwise provide a text that a file is not downloaded and don't call download.
    private fun download() {
        loadingButton.setLoadingButtonState(ButtonState.Clicked)

        if (selectedGitHubUrl != null) {
            loadingButton.setLoadingButtonState(ButtonState.Loading)
            notificationManager = ContextCompat.getSystemService(
                    applicationContext,
                    NotificationManager::class.java
            ) as NotificationManager

            createChannel(
                    applicationContext,
                    getString(R.string.file_download_complete_channel_id),
                    getString(R.string.file_download_complete_channel_name)
            )

            var file = File(getExternalFilesDir(null), "/repos")

            if (!file.exists()) {
                file.mkdirs()
            }

            val request =
                    DownloadManager.Request(Uri.parse(selectedGitHubUrl))
                            .setTitle(getString(R.string.app_name))
                            .setDescription(getString(R.string.app_description))
                            .setRequiresCharging(false)
                            .setAllowedOverMetered(true)
                            .setAllowedOverRoaming(true)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/repos/github_repository.zip")


            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            downloadID =
                    downloadManager.enqueue(request)
        } else {
            loadingButton.setLoadingButtonState(ButtonState.Completed)
            showToast(getString(R.string.nothingSelected))
        }
    }

    companion object {
        private const val CHANNEL_ID = "channelId"
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }
            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            if(view.isChecked)
                when (view.getId()) {
                    R.id.rb_glide -> {
                        selectedGitHubUrl = getString(R.string.glide_github_url)
                        selectedGitHubDescription = getString(R.string.str_glide)
                        showToast(getString(R.string.glide_message))
                    }

                    R.id.rb_loadApp -> {
                        selectedGitHubUrl = getString(R.string.load_app_github_url)
                        selectedGitHubDescription = getString(R.string.str_loadApp)
                        showToast(getString(R.string.load_app_message))
                    }

                    R.id.rb_retrofit -> {
                        selectedGitHubUrl = getString(R.string.retrofit_github_url)
                        selectedGitHubDescription = getString(R.string.str_retrofit)
                        showToast(getString(R.string.retrofit_message))
                    }
                }
        }
    }

    private fun showToast(text: String) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.show()
    }
}
