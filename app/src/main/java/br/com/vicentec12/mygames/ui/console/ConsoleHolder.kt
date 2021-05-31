package br.com.vicentec12.mygames.ui.console

import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.mygames.data.model.ConsoleWithGames
import br.com.vicentec12.mygames.databinding.ItemConsoleBinding
import br.com.vicentec12.mygames.interfaces.OnItemClickListener

class ConsoleHolder(
        private val mBinding: ItemConsoleBinding,
        private val mOnItemClickListener: OnItemClickListener?
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(mConsoleWithGames: ConsoleWithGames) {
        mBinding.position = adapterPosition
        mBinding.consoleWithGames = mConsoleWithGames
        mBinding.onItemClickListener = mOnItemClickListener
    }

}