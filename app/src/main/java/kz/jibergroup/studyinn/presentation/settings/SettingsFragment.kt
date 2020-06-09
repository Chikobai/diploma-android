package kz.jibergroup.studyinn.presentation.settings

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.example.myfirstapp.presentation.global.utils.EventObserver
import com.example.myfirstapp.presentation.global.utils.alert
import com.example.myfirstapp.presentation.global.utils.alertWithActions
import kotlinx.android.synthetic.main.settings_fragment.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.presentation.auth.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun layoutId(): Int {
        return R.layout.settings_fragment
    }

    val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.userLogState.observe(activity as AppCompatActivity, logoutObserver)
        viewModel.statusLiveData.observe(this, statusObserver)
        viewModel.errorLiveData.observe(this, errorObserver)
        viewModel.userData.observe(viewLifecycleOwner, userDataObserver)
        viewModel.image.observe(viewLifecycleOwner, imageObserver)
        viewModel.userLang.observe(viewLifecycleOwner, langObserver)
        viewModel.getUserData()
        viewModel.getLanguage()

        settingsLogout.setOnClickListener {
            viewModel.onLogoutUser()
        }

        settingsPasswordChangeContainer.setOnClickListener {
            findNavController().navigate(R.id.changePasswordFragment)
        }

        settingsEmailChangeContainer.setOnClickListener {
            findNavController().navigate(R.id.changeEmailFragment)
        }

        settingsLangContainer.setOnClickListener {
            showLanguageDialog()
        }
        settingsAboutApp.setOnClickListener {
            findNavController().navigate(R.id.aboutAppFragment)
        }

        editProfilePhotoContainer.setOnClickListener {
            openImagePicker()
        }

        settingsDataContainer.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }


    }

    private val langObserver = Observer<String> {
        settingsLang.text = it
    }

    private val errorObserver = EventObserver<String> {
        context?.alert(message = it)
        val data = viewModel.userData.value
        viewModel.userData.value = data
    }

    private val userDataObserver = Observer<ProfileItem> {
        //        settingName.text = it.first_name.plus(" " + it.last_name)
        settingsNameChangeTitle.text = it.first_name.plus(" " + it.last_name)
        settingsEmail.text = it.email
        viewModel.image.value = it.image.toString()
    }

    private val imageObserver = Observer<String> {
        Glide.with(activity as AppCompatActivity).load(it)
            .into(settingsImage)
    }

    private val logoutObserver = Observer<LogOutState> { state ->
        when (state) {
            LogOutState.CONFIRM -> {
                context?.alertWithActions(
                    getString(R.string.attention_alert),
                    getString(R.string.sure_to_logout),
                    true,
                    {
                        viewModel.userLogState.value = LogOutState.CONFIRMED
                    },
                    {})
            }
            LogOutState.CONFIRMED -> {
                viewModel.cleanStorage()
            }
            LogOutState.FINISHED -> {
                navigationLogin()
            }

            else -> {
                //do nothing
            }
        }
    }


    private fun openImagePicker() {
        ImagePicker.create(this)
            .returnMode(ReturnMode.ALL)
            .folderMode(true)
            .toolbarFolderTitle(getString(R.string.file))
            .toolbarImageTitle(getString(R.string.tap_to_choose))
            .toolbarArrowColor(Color.BLACK)
            .single()
            .showCamera(true)
            .imageDirectory(getString(R.string.camera))
            .enableLog(false)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)
            viewModel.image.value = image.path
            photoEditAction()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun photoEditAction() {
        photoEditActionContainer.visibility = View.VISIBLE

        photoEditCancell.setOnClickListener {
            photoEditActionContainer.visibility = View.GONE
            viewModel.image.value = viewModel.userData.value?.image.toString()
        }

        photoEditSave.setOnClickListener {
            photoEditActionContainer.visibility = View.GONE
            viewModel.savePhoto()
        }
    }

    private fun showLanguageDialog() {
        val alertDialog = AlertDialog.Builder(activity as AppCompatActivity)
        alertDialog.setTitle(getString(R.string.language))
        val items = resources.getStringArray(R.array.languagelist)
        var checkedItem = -1
        if (viewModel.getLanguage().equals(items.get(0))) {
            checkedItem = 0
        } else {
            checkedItem = 1
        }

        alertDialog.setPositiveButton(getString(R.string.choose)) { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.setSingleChoiceItems(items, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    viewModel.saveLanguage(items.get(0))
                    setLocale("kk-rKZ")
//                    activity?.recreate()
                }
                1 -> {
                    viewModel.saveLanguage(items.get(1))
                    setLocale("ru")
//                    activity?.recreate()

                }
            }
        }


        val alert = alertDialog.create()
        alert.show()
    }


    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun navigationLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


}
