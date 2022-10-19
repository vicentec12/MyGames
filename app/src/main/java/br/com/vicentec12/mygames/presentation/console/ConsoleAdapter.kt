package br.com.vicentec12.mygames.presentation.console

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import br.com.vicentec12.mygames.databinding.ItemConsoleBinding
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.util.OnItemClickListener

class ConsoleAdapter(
    private val mOnItemClick: OnItemClickListener?
) : ListAdapter<Console, ConsoleViewHolder>(Console.DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ConsoleViewHolder(
        ItemConsoleBinding.inflate(LayoutInflater.from(parent.context), parent, false), mOnItemClick
    )

    override fun onBindViewHolder(viewHolder: ConsoleViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

}