package com.example.alarmapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmapp.AlarmAdapter

import com.example.alarmapp.AlarmApplication
import com.example.alarmapp.AlarmViewModel
import com.example.alarmapp.AlarmViewModelFactory
import com.example.alarmapp.databinding.FragmentAlarmsBinding



class AlarmsFragment : Fragment() {

    private val viewModel: AlarmViewModel by activityViewModels {
        AlarmViewModelFactory(
            (activity?.application as AlarmApplication).database.alarmDao()
        )
    }

    private var _binding: FragmentAlarmsBinding? = null
    private  val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AlarmAdapter { clickedAlarm ->
            val action =
                AlarmsFragmentDirections.actionAlarmsFragmentToAddAlarmFragment()
            findNavController().navigate(action)
        }

        binding.alarmList.layoutManager = LinearLayoutManager(this.context)
        binding.alarmList.adapter = adapter

        viewModel.allAlarms.observe(this.viewLifecycleOwner) {
                items -> items.let { adapter.submitList(it) }
        }

        binding.addButton.setOnClickListener{
            val action = AlarmsFragmentDirections.actionAlarmsFragmentToAddAlarmFragment()
            this.findNavController().navigate(action)
        }
    }
}