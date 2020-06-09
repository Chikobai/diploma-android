package kz.jibergroup.studyinn.presentation.profile

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myfirstapp.presentation.global.utils.pickTime
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.profile_fragment.*
import kz.jibergroup.studyinn.domain.entity.ProfileItem
import kz.jibergroup.studyinn.presentation.home.ItemClickListener
import kz.jibergroup.studyinn.presentation.image_dialog.GalleryDialogFragment
import kz.jibergroup.studyinn.presentation.image_dialog.ImageItem
import kz.jibergroup.studyinn.utils.AppConstants
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kz.jibergroup.studyinn.R


class ProfileFragment : BaseFragment(), ItemClickListener {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    val viewModel: ProfileViewModel by viewModel()

    override fun layoutId(): Int {
        return R.layout.profile_fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initStatistics()

        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner, userDataObserver)
        viewModel.userNotificationData.observe(viewLifecycleOwner, localNotificationObserver)
        viewModel.certificates.observe(this, contentObserver)


        profileSettingsBtn.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }


    }

    private val contentObserver = Observer<MutableList<ImageItem>> {
        initCertificates()
    }

    private val userDataObserver = Observer<ProfileItem> {
        profileUsername.text = it.first_name.plus(" " + it.last_name)
        profileUserMail.text = it.email
        Glide.with(activity as AppCompatActivity).load(it.image.toString())
            .into(profileUserImage)
    }

    private val localNotificationObserver = Observer<UserLocalNotificationItem> {
        if (it.enabled ?: false) {
            notificationSwitcher.isChecked = true
            notificationStatus.text = getString(R.string.reminder_about_lesson)
            notificationTimeContainer.visibility = View.VISIBLE
            notificationTime.text = getString(R.string.reminder_time).plus(" ").plus(it.time)
        } else {
            notificationSwitcher.isChecked = false
            notificationStatus.text = getString(R.string.notification_disabled)
            notificationTimeContainer.visibility = View.GONE
        }


        notificationSwitcher.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                context?.pickTime({
                    viewModel.saveLocalNotificationData(UserLocalNotificationItem(isChecked, it))
                    scheduleReminder(it)
                }, {}, {
                    if (it) {
                        viewModel.getLocalNotificationData()
                    }
                })
            } else {
                cancellReminder()
                viewModel.saveLocalNotificationData(UserLocalNotificationItem(isChecked, ""))
            }

        }

        notificationTimeContainer.setOnClickListener {
            context?.pickTime({
                viewModel.saveLocalNotificationData(UserLocalNotificationItem(true, it))
                scheduleReminder(it)
            }, {}, {})
        }
    }

    private fun openDialog(position: Int) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.addToBackStack(null)
        val dialogFragment = GalleryDialogFragment.newInstance(
            viewModel.certificates.value ?: arrayListOf(), position
        )
        fragmentTransaction?.let {
            dialogFragment.show(it, "dialog")
        }
    }


    override fun click(position: Int) {
        openDialog(position)
    }

    private fun initStatistics() {

        val barDataSet = BarDataSet(getEntries(), getString(R.string.day))
        val barData = BarData(barDataSet)
        statisticsBarChart.setData(barData)
        statisticsBarChart.setDrawGridBackground(false)
        statisticsBarChart.xAxis.setDrawGridLines(false)
        statisticsBarChart.animateY(500)
        barDataSet.setValueTextColor(Color.BLACK)

        statisticsBarChart.axisLeft.setDrawGridLines(false)
        statisticsBarChart.axisLeft.setDrawAxisLine(false)
        statisticsBarChart.axisRight.setDrawGridLines(false)
        statisticsBarChart.axisRight.setDrawAxisLine(false)
        statisticsBarChart.xAxis.setDrawGridLines(false)
        statisticsBarChart.axisRight.textColor = resources.getColor(android.R.color.transparent)
        val yAxis = statisticsBarChart.axisLeft
        yAxis.textColor = resources.getColor(android.R.color.transparent)

        val xAxis = statisticsBarChart.xAxis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)


    }

    private fun getEntries(): MutableList<BarEntry> {
        val barEntries = mutableListOf<BarEntry>()
        barEntries.add(BarEntry(2f, 0f))
        barEntries.add(BarEntry(4f, 1f))
        barEntries.add(BarEntry(6f, 1f))
        barEntries.add(BarEntry(8f, 3f))
        barEntries.add(BarEntry(7f, 4f))
        barEntries.add(BarEntry(3f, 3f))

        return barEntries
    }

    private fun initCertificates() {

        val certificateAdapter = CertificateAdapter()
        certificateAdapter.submitData(viewModel.certificates.value ?: mutableListOf())
        certificateAdapter.submitListener(this)

        certificateRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        certificateRecycler.adapter = certificateAdapter
    }

    private fun scheduleReminder(time: String) {

        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(AppConstants.TIME_PATTERN)
        cal.time = sdf.parse(time)

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(activity, AlarmReceiver::class.java)
        intent.action = LOCAL_NOTIFICATION_REMINDER

        val pendingIntent =
            PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            cal.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

    }

    private fun cancellReminder() {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(activity, AlarmReceiver::class.java)
        intent.action = LOCAL_NOTIFICATION_REMINDER
        val pendingIntent = PendingIntent.getBroadcast(
            activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager?.cancel(pendingIntent)
    }

}
