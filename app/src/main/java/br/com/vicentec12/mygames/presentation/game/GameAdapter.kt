package br.com.vicentec12.mygames.presentation.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import br.com.vicentec12.mygames.databinding.ItemGameBinding
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.util.OnItemClickListener
import br.com.vicentec12.mygames.util.OnItemLongClickListener

class GameAdapter(
    private val mViewModel: GameViewModel,
    private val mOnItemClick: OnItemClickListener?,
    private val mOnItemLongClick: OnItemLongClickListener?
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