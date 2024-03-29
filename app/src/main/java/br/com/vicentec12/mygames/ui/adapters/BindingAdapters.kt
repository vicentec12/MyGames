package br.com.vicentec12.mygames.ui.adapters

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ViewFlipper
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.mygames.R
import br.com.vicentec12.mygames.domain.model.Console
import br.com.vicentec12.mygames.domain.model.Game
import br.com.vicentec12.mygames.ui.game.GameAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("displayedChild")
    fun setDisplayedChild(mView: ViewFlipper, mDisplayChild: Int) {
        if (mView.displayedChild != mDisplayChild)
            mView.displayedChild = mDisplayChild
    }

    @JvmStatic
    @BindingAdapter("srcVector")
    fun setFabIcon(mView: FloatingActionButton, mResourceId: Int) {
        mView.setImageResource(mResourceId)
    }

    @JvmStatic
    @BindingAdapter("srcVector")
    fun setImageViewIcon(mView: AppCompatImageView, mResourceId: Int) {
        mView.setImageResource(mResourceId)
    }

    @JvmStatic
    @BindingAdapter("items")
    fun setItemsGame(mView: RecyclerView, mGames: List<Game>?) {
        mView.adapter?.apply {
            (this as GameAdapter).submitList(mGames)
        }
    }

    @JvmStatic
    @BindingAdapter("items")
    fun setItemsConsoleSpinner(mView: AppCompatSpinner, mConsoles: List<Console>?) {
        val mConsolesArrayList = ArrayList(mConsoles.orEmpty())
        mConsoles?.let {
            if (mConsolesArrayList.size == 0 || mConsoles[0].id != 0L)
                mConsolesArrayList.add(0, Console(name = mView.context.getString(R.string.text_select)))
            mView.adapter = ArrayAdapter(
                mView.context, R.layout.support_simple_spinner_dropdown_item, mConsolesArrayList
            ).apply {
                setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("selection")
    fun setConsoleSelection(mView: AppCompatSpinner, mConsoleId: Long) {
        mView.adapter?.apply {
            for (i in 0 until count) {
                if ((getItem(i) as Console).id == mConsoleId) {
                    mView.setSelection(i)
                    break
                }
            }
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "selection", event = "selectionAttr")
    fun getConsoleSelection(mView: AppCompatSpinner) = (mView.selectedItem as Console).id

    @JvmStatic
    @BindingAdapter("selectionAttr")
    fun setConsoleSelectionListener(
        mView: AppCompatSpinner,
        selectionAttr: InverseBindingListener?
    ) {
        mView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0)
                    selectionAttr?.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

}