package com.example.alarmapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmapp.data.Alarm
import com.example.alarmapp.databinding.AlarmListItemBinding

class AlarmAdapter(private val onItemClicked: (Alarm) -> Unit) :
    ListAdapter<Alarm, AlarmAdapter.AlarmViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(
            AlarmListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int){
        val current = getItem(position)
        holder.itemView.setOnClickListener()  {
            onItemClicked(current)
        }
        holder.bind(current)
    }
    class AlarmViewHolder(private var binding: AlarmListItemBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bind(alarm: Alarm ){
            binding.alarmTime.  text = alarm.alarmTime.toString()
            binding.alarmMessage.text = alarm.alarmMessage
        }

    }


    companion object{
     private val DiffCallback = object : DiffUtil.ItemCallback<Alarm>() {
         override fun areItemsTheSame(oldAlarm: Alarm, newAlarm: Alarm): Boolean{
             return oldAlarm == newAlarm
         }

         override fun areContentsTheSame(oldAlarm: Alarm, newAlarm: Alarm): Boolean {
             return oldAlarm.alarmTime == newAlarm.alarmTime
         }
        }
    }
}
