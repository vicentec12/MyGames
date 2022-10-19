package br.com.vicentec12.mygames.presentation.game

import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.mygames.databinding.ItemGameBinding
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.util.OnItemClickListener
import br.com.vicentec12.mygames.util.OnItemLongClickListener

class GameViewHolder(
    private val mBinding: ItemGameBinding,
    private val mOnItemClick: OnItemClickListener?,
    private val mOnItemLongClick: OnItemLongClickListener?
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(mGame: Game, mViewModel: GameViewModel) {
        with(mBinding) {
            game = mGame
            viewModel = mViewModel
            position = adapterPosition
            root.setOnLongClickListener {
                mOnItemLongClick?.invoke(it, mGame, adapterPosition)
                mOnItemLongClick != null
            }
            root.setOnClickListener { mOnItemClick?.invoke(it, mGame, adapterPosition) }
        }
    }

}