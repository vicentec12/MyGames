package br.com.vicentec12.mygames.presentation.console

import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.mygames.databinding.ItemConsoleBinding
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.util.OnItemClickListener

class ConsoleViewHolder(
    private val mBinding: ItemConsoleBinding,
    private val mOnItemClick: OnItemClickListener?
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(mConsole: Console) {
        with(mBinding) {
            console = mConsole
            root.setOnClickListener { mOnItemClick?.invoke(it, mConsole, adapterPosition) }
        }
    }

}