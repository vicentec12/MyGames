package br.com.vicentec12.mygames.ui.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import br.com.vicentec12.mygames.data.model.Game
import br.com.vicentec12.mygames.databinding.ItemGameBinding
import br.com.vicentec12.mygames.interfaces.OnItemClickListener
import br.com.vicentec12.mygames.interfaces.OnItemLongClickListener

class GameAdapter(
        private val mViewModel: GameViewModel,
        private val mOnItemClickListener: OnItemClickListener?,
        private val mOnItemLongClickListener: OnItemLongClickListener?
) : ListAdapter<Game, GameHolder>(Game.DIFF_UTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GameHolder(
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            mOnItemClickListener,
            mOnItemLongClickListener
    )

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(getItem(position), mViewModel)
    }

    override fun getItemId(position: Int) = getItem(position).id

}