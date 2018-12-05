package com.alavpa.logger

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LogAdapter : RecyclerView.Adapter<LogAdapter.ViewHolder>() {
    private val list = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_log_line,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun add(buffer: String?) {
        buffer?.let {
            list.add(it)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val logTextView = view.findViewById<TextView>(R.id.logTextView)
        fun bind(line: String) {
            logTextView.setTextColor(getColor(line))
            logTextView.text = line
        }

        private fun getColor(line: String): Int {
            return when {
                line.contains(" V/") -> Color.BLUE
                line.contains(" I/") -> Color.GREEN
                line.contains(" D/") -> Color.WHITE
                line.contains(" W/") -> Color.YELLOW
                line.contains(" E/") -> Color.RED
                else -> Color.BLUE
            }
        }
    }
}
