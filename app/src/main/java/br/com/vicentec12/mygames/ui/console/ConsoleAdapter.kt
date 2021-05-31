package br.com.vicentec12.mygames.ui.console

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import br.com.vicentec12.mygames.data.model.ConsoleWithGames
import br.com.vicentec12.mygames.databinding.ItemConsoleBinding
import br.com.vicentec12.mygames.interfaces.OnItemClickListener

class ConsoleAdapter(
        val mOnItemClickListener: OnItemClickListener?
) : ListAdapter<ConsoleWithGames, ConsoleHolder>(ConsoleWithGames.DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ConsoleHolder(ItemConsoleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false), mOnItemClickListener)

    override fun onBindViewHolder(holder: ConsoleHolder, position: Int) {
        holder.bind(getItem(position))
    }

}