package com.example.alarmapp.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.example.alarmapp.AlarmApplication
import com.example.alarmapp.AlarmViewModel
import com.example.alarmapp.AlarmViewModelFactory
import com.example.alarmapp.data.Alarm
import com.example.alarmapp.databinding.FragmentAddAlarmBinding
import com.google.android.material.timepicker.MaterialTimePicker
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.alarmapp.AlarmAdapter
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalDateTime


class AddAlarmFragment : Fragment() {

    private var _binding: FragmentAddAlarmBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: AlarmsFragmentArgs by navArgs()


    private val viewModel: AlarmViewModel by activityViewModels{
        AlarmViewModelFactory(
            (activity?.application as AlarmApplication).database.alarmDao()
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAlarmBinding.inflate(inflater, container,false)
        return binding.root
    }

    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.addAlarmMessage.text.toString(),
            binding.addAlarmSound.toString()
        )
    }

    private  fun bind(alarm: Alarm){
        binding.apply{
            addAlarmMessage.setText(alarm.alarmMessage, TextView.BufferType.SPANNABLE)
            addAlarmSound.setText(alarm.alarmSound, TextView.BufferType.SPANNABLE)
            addAlarmTime.text = alarmFormattedTime(alarm.alarmTime)
            saveAlarmButton.setOnClickListener {
                updateAlarm(alarm.id)
            }
        }
    }

    private fun addNewAlarm() {
        if (isEntryValid()) {
            val alarmTime = parseFormattedTime(binding.addAlarmTime.text.toString())
            viewModel.addNewAlarm(
                alarmTime,
                binding.addAlarmMessage.text.toString(),
                binding.addAlarmSound.text.toString()
            )
            navigateBackToAlarmsFragment()
        }
    }

    private fun updateAlarm(alarmId: Int) {
        if (isEntryValid()) {
            val alarmTime = parseFormattedTime(binding.addAlarmTime.text.toString())
            viewModel.updateAlarm(
                this.binding.navigationArgs.alarmId,
                this.binding.addAlarmTime.text.toString(),
                this.binding.addAlarmMessage.text.toString(),
                this.binding.addAlarmSound.text.toString()
            )
            navigateBackToAlarmsFragment()
        }
    }

    private fun navigateBackToAlarmsFragment() {
        val action = AddAlarmFragmentDirections.actionAddAlarmFragmentToAlarmsFragment()
        findNavController().navigate(action)
    }

    // Utility functions for formatting and parsing time
    @RequiresApi(Build.VERSION_CODES.O)
    private fun alarmFormattedTime(time: LocalDateTime): String {
        return String.format("%02d:%02d", time.hour, time.minute)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseFormattedTime(formattedTime: String): LocalDateTime {
        val parts = formattedTime.split(":")
        return LocalDateTime.now()
            .withHour(parts[0].toInt())
            .withMinute(parts[1].toInt())
            .withSecond(0)
    }

    val adapter = AlarmAdapter { clickedAlarm ->
        val action = AlarmsFragmentDirections.actionAlarmsFragmentToAddAlarmFragment()
        findNavController().navigate(action)
    }

    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .build()

        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute

            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            binding.addAlarmTime.text = formattedTime
        }

        picker.show(parentFragmentManager, "timePicker")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.alarmId
        if (id > 0){
            viewModel.retrieveAlarm(id).observe(this.viewLifecycleOwner){selectedAlarm ->
               val alarm = selectedAlarm
                bind(alarm)
            }
        } else {
            binding.saveAlarmButton.setOnClickListener{
                addNewAlarm()
            }
        }
    }








}


