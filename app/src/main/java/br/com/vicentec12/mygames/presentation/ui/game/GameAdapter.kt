package br.com.vicentec12.mygames.presentation.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import br.com.vicentec12.mygames.databinding.ItemGameBinding
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.commons.util.OnItemClickListener
import br.com.vicentec12.mygames.commons.util.OnItemLongClickListener

class GameAdapter(
    private val mViewModel: GameViewModel,
    private val mOnItemClick: OnItemClickListener<Game>?,
    private val mOnItemLongClick: OnItemLongClickListener<Game>?
) : ListAdapter<Game, GameViewHolder>(Game.DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameViewHolder(
        ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        mOnItemClick, mOnItemLongClick
    )

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position), mViewModel)
    }

    override fun getItemId(position: Int) = getItem(position).id

}