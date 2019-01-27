package com.cybersuccess.demo.kotlinappliccation.sp

import android.content.Context
import android.support.v7.widget.AppCompatRadioButton
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.cybersuccess.demo.kotlinappliccation.R
import java.io.Serializable
import java.util.ArrayList
import java.util.Comparator


public class AdapterSingleSelectList @JvmOverloads constructor(mArrayListItems: ArrayList<Entity>, existingSelectedEntity: Entity,var onClickListner: OnClickListner) : RecyclerView.Adapter<AdapterSingleSelectList.ViewHolderSingleSelect>() {

    private var previousClickedPosition =0

    /**
     * List of all entities to be show initially
     */
    private val mListItemsOriginal = ArrayList<Entity>()

    /**
     * Modified / Filtered List of entities to be shown on List
     */
    private var mListItemsFiltered = ArrayList<Entity>()

    /**
     * Selected entity
     */
    /**
     * Returns the selected element from the list if any selected else return null
     *
     * @return Entity object of selected element
     */
    var selectedEntity: Entity? = null
        private set

    init {
        mListItemsOriginal.addAll(mArrayListItems)
        mListItemsFiltered.addAll(mArrayListItems)
        selectedEntity = existingSelectedEntity
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSingleSelect {
        return ViewHolderSingleSelect(LayoutInflater.from(parent.context).inflate(R.layout.adapter_single_select_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderSingleSelect, position: Int) {
        val currentEntity = mListItemsFiltered[position]
        holder.mRadioButton.setText(currentEntity.value)
        if(currentEntity.value.equals( selectedEntity?.value)){
            previousClickedPosition=position
            holder.mRadioButton.setChecked(currentEntity.value.equals( selectedEntity?.value))
        }else{
            holder.mRadioButton.setChecked(currentEntity.value.equals( selectedEntity?.value))
        }



    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return mListItemsFiltered[position].key
    }

    override fun getItemCount(): Int {
        return mListItemsFiltered.size
    }



    inner class ViewHolderSingleSelect(itemView: View) : RecyclerView.ViewHolder(itemView) {

         val mRadioButton: AppCompatRadioButton

        init {
            mRadioButton = itemView.findViewById(R.id.rbSingleSelectList) as AppCompatRadioButton

            itemView.setOnClickListener { v ->
                if (!mRadioButton.isChecked()) {

                    val clickedPosition = adapterPosition
                    selectedEntity = mListItemsFiltered[clickedPosition]

                    mRadioButton.setChecked(true)
                    if (previousClickedPosition >= 0) {
                        notifyItemChanged(previousClickedPosition)
                    }
                    previousClickedPosition = clickedPosition
                }

                onClickListner.selectedPostion("Selected Position"+adapterPosition)
            }
        }
    }
}


abstract class Entity : Comparator<Entity>, Serializable {
    constructor(b: Boolean){
        selected=b
    }

    constructor()

    public var selected: Boolean? = false

    abstract val key: Long

    open var value: String = ""
        set(s) {}

    val subValue: String?
        get() = null

   /* fun setSelected(selected: Boolean) {
        this.selected = selected
    }

    fun getSelected(): Boolean {
        return selected!!
    }*/

    override fun equals(obj: Any?): Boolean {
        if (obj != null && this === obj) {
            return true
        } else if (obj != null && key == (obj as Entity).key) {
            return true
        }
        return false
    }

    override fun compare(o1: Entity, o2: Entity): Int {
        val one = o1.key
        val two = o2.key
        return if (one == two) 0 else if (one < two) -1 else 1
    }

    override fun toString(): String {
        return value
    }

}

 class Util {
     fun getSelectAllEntity(): Entity {
         return object : Entity() {
             override val key: Long
                 get() = -1

             override var value: String = ""
                 get() = "Select All"
         }
     }

     fun getDummyEntity(id: Long, dummyText: String): Entity {
         return object : Entity() {
             override val key: Long
                 get() = id

             override var value: String = ""
                 get() = dummyText
         }
     }
}


class CustomRadioButton : AppCompatRadioButton {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (lineCount > 1) {
            gravity = Gravity.TOP
        } else {
            gravity = Gravity.CENTER_VERTICAL
        }
    }
}

public interface OnClickListner{
    fun selectedPostion(selectedValue: String)

}

