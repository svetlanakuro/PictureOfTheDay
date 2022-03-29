package com.svetlanakuro.pictureoftheday.ui.favorites

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.*
import com.svetlanakuro.pictureoftheday.R
import com.svetlanakuro.pictureoftheday.databinding.ActivityNotesBinding
import com.svetlanakuro.pictureoftheday.domain.AppThemePreferenceDelegate

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var adapter: RecyclerActivityAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    private val appThemePreference by lazy { AppThemePreferenceDelegate() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        val savedTheme = appThemePreference.getSavedTheme(this)
        setTheme(appThemePreference.savedThemeToStyleId(savedTheme))
        setContentView(binding.root)

        val data = arrayListOf(
            Pair(NotesData(1, "Some Task", ""), false)
        )

        data.add(0, Pair(NotesData(0, "Header"), false))

        adapter = RecyclerActivityAdapter(object : OnListItemClickListener {
            override fun onItemClick(data: NotesData) {
                Toast.makeText(this@NotesActivity, data.someTitle, Toast.LENGTH_SHORT).show()
            }
        }, data, object : OnStartDragListener {
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }
        })

        binding.recyclerView.adapter = adapter

        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
        }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}

class RecyclerActivityAdapter(
    private val onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<NotesData, Boolean>>,
    private val dragListener: OnStartDragListener
) : RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    companion object {

        private const val TYPE_HEADER = 0
        private const val TYPE_TASK = 1
//        private const val TYPE_NOTE = 2
    }

//    inner class NoteViewHolder(view: View) : BaseViewHolder(view) {
//
//        override fun bind(data: Pair<NotesData, Boolean>) {
//            if (layoutPosition != RecyclerView.NO_POSITION) {
//                itemView.findViewById<TextView>(R.id.noteTitleTextView).text = data.first.someTitle
//                itemView.findViewById<TextView>(R.id.noteDescriptionTextView).text =
//                    data.first.someDescription
//                itemView.findViewById<ImageView>(R.id.noteImageView).setOnClickListener {
//                    onListItemClickListener.onItemClick(data.first)
//                }
//            }
//        }
//    }

    inner class TaskViewHolder(view: View) : BaseViewHolder(view), ItemTouchHelperViewHolder {

        override fun bind(data: Pair<NotesData, Boolean>) {
            itemView.findViewById<TextView>(R.id.taskTextView).text = data.first.someTitle
            itemView.findViewById<ImageView>(R.id.taskImageView).setOnClickListener {
                onListItemClickListener.onItemClick(data.first)
            }

            itemView.findViewById<ImageView>(R.id.addItemImageView).setOnClickListener {
                addItem()
            }

            itemView.findViewById<ImageView>(R.id.removeItemImageView).setOnClickListener {
                removeItem()
            }

            itemView.findViewById<ImageView>(R.id.moveItemDown).setOnClickListener { moveDown() }
            itemView.findViewById<ImageView>(R.id.moveItemUp).setOnClickListener { moveUp() }

            itemView.findViewById<TextView>(R.id.taskDescriptionTextView).visibility =
                if (data.second) View.VISIBLE else View.GONE

            itemView.findViewById<TextView>(R.id.taskTextView).setOnClickListener {
                toggleText()
            }

            itemView.findViewById<ImageView>(R.id.dragHandleImageView).setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }

                false
            }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }

            notifyItemChanged(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }

                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }

                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(data: Pair<NotesData, Boolean>) {
            itemView.setOnClickListener {
                onListItemClickListener.onItemClick(data.first)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_TASK -> TaskViewHolder(
                inflater.inflate(
                    R.layout.activity_notes_item_task, parent, false
                ) as View
            )
//            TYPE_NOTE -> NoteViewHolder(
//                inflater.inflate(
//                    R.layout.activity_notes_item_note, parent, false
//                ) as View
//            )
            else -> HeaderViewHolder(
                inflater.inflate(
                    R.layout.activity_notes_item_header, parent, false
                ) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            else -> TYPE_TASK
//            data[position].first.someDescription.isNullOrBlank() -> TYPE_TASK
//            else -> TYPE_NOTE
        }
    }

    fun appendItem() {
        data.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Pair(NotesData(1, "Task", ""), false)

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}