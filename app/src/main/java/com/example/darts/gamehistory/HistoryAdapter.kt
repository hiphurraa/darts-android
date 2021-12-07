//package com.example.darts.gamehistory
//
//import android.widget.ListAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.example.darts.database.entities.Game
//import com.example.darts.databinding.FragmentGameSettingsBinding
//
//class HistoryAdapter : ListAdapter<Game, HistoryAdapter.ViewHolder>(GameDiffCallback()) {
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
//    }
//
//    class ViewHolder private constructor(val binding: Histo) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: Game){
//            binding. = item
//        }
//    }
//}
//
//class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
//    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
//        return oldItem == newItem
//    }
//}