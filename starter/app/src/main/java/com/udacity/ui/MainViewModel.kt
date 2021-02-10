package com.udacity.ui

import android.app.Application
import android.app.NotificationManager
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.udacity.R
import com.udacity.util.sendNotification

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    private var selectedGitHubRepository: String? = null
    private var notificationManager: NotificationManager =
            ContextCompat.getSystemService(app, NotificationManager::class.java) as NotificationManager

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val isChecked = view.isChecked
            when (view.getId()) {
                R.id.rb_glide ->
                    if (isChecked) {
                        selectedGitHubRepository = app.applicationContext.getString(R.string.glide_github_url)
                        showToast(app.applicationContext.getString(R.string.glide_message))
                    }

                R.id.rb_loadApp ->
                    if (isChecked) {
                        selectedGitHubRepository = app.applicationContext.getString(R.string.load_app_github_url)
                        showToast(app.applicationContext.getString(R.string.load_app_message))
                    }

                R.id.rb_retrofit -> {
                    if (isChecked) {
                        selectedGitHubRepository = app.applicationContext.getString(R.string.retrofit_github_url)
                        showToast(app.applicationContext.getString(R.string.retrofit_message))
                    }
                }
            }
            notificationManager.sendNotification(selectedGitHubRepository.toString(), app, "Complete")
        }
    }

    private fun showToast(text: String) {
        val toast = Toast.makeText(app.applicationContext, text, Toast.LENGTH_SHORT)
        toast.show()
    }

}