package br.com.vicentec12.mygames.ui.game

import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.databinding.ItemGameBinding
import br.com.vicentec12.mygames.interfaces.OnItemClickListener
import br.com.vicentec12.mygames.interfaces.OnItemLongClickListener

class GameHolder(
        private val mBinding: ItemGameBinding,
        private val mOnItemClickListener: OnItemClickListener?,
        private val mOnItemLongClickListener: OnItemLongClickListener?
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(mGame: Game, mViewModel: GameViewModel) {
        with(mBinding) {
            game = mGame
            viewModel = mViewModel
            position = adapterPosition
            onItemClickListener = mOnItemClickListener
            root.setOnLongClickListener { v ->
                mOnItemLongClickListener?.onItemLongClick(v, mGame, adapterPosition)
                mOnItemLongClickListener != null
            }
        }
    }

}