package com.cybersuccess.demo.kotlinappliccation

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.Fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.cybersuccess.demo.kotlinappliccation.sp.AdapterSingleSelectList
import com.cybersuccess.demo.kotlinappliccation.sp.Entity
import com.cybersuccess.demo.kotlinappliccation.sp.OnClickListner
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.fragment_documents_list.*

/**
 * A placeholder fragment containing a simple view.
 */
class DocumentsListFragment : Fragment() {
    lateinit var documentsAdapter: AnimalAdapter
    val list = arrayListOf<Documents>()
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var cnt: Context

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        cnt = this!!.context!!
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_documents_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createList()
        //  documentsAdapter = AnimalAdapter(list,context)
        // documentsAdapter.setMovies(list)
        //   documentRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // documentRecyclerView.adapter = documentsAdapter
// Creates a vertical Layout Manager
        documentRecyclerView.layoutManager = LinearLayoutManager(context)

        // You can use GridLayoutManager if you want multiple columns. Enter the number of columns as a parameter.
//        rv_animal_list.layoutManager = GridLayoutManager(this, 2)

        // Access the RecyclerView Adapter and load the data into it
        // documentRecyclerView.adapter = AnimalAdapter(list, context)

        documentRecyclerView.adapter = AdapterSingleSelectList(getDummyData(),
                object : Entity() {
                    override val key: Long
                        get() = 122

                    override var value: String = ""
                        get() = "Bring Pluto Back!"
                },
                object : OnClickListner {
                    override fun selectedPostion(selectedValue: String) {
                        Toast.makeText(context, selectedValue, Toast.LENGTH_SHORT).show()
                    }
                })



        sheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottom_sheet)

        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED ->
                        btBottomSheet.text = "Close Bottom Sheet"
                    BottomSheetBehavior.STATE_COLLAPSED ->
                        btBottomSheet.text = "Expand Bottom Sheet"
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        btBottomSheet.setOnClickListener(View.OnClickListener {
            expandCloseSheet()
        })

        btBottomSheetDialog.setOnClickListener(View.OnClickListener {


            context?.let {
                val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
                val dialog = BottomSheetDialog(this.context!!)

                dialog.setContentView(view)
                dialog.readToUnread.setOnClickListener(View.OnClickListener {


                })

                dialog.show()
            }
        })
    }

    private fun sortLisRead(list: ArrayList<Documents>) {

    }

    private fun createList() {
        list.add(Documents(1, "title", "11-12-2018", "read"))
        list.add(Documents(1, "title 2", "10-12-2018", "unread"))
        list.add(Documents(1, "title 3", "10-12-2018", "unread"))
        list.add(Documents(1, "title 4", "10-12-2018", "read"))
        list.add(Documents(1, "title 5", "10-12-2018", "unread"))
        list.add(Documents(1, "title 6", "10-12-2018", "read"))
    }

    private fun expandCloseSheet() {
        if (sheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            btBottomSheet.text = "Close Bottom Sheet"
        } else {
            sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            btBottomSheet.text = "Expand Bottom Sheet"
        }
    }


    private fun getDummyData(): java.util.ArrayList<Entity> {
        val arrayList = java.util.ArrayList<Entity>()
        arrayList.add(getDummyEntity1(true,1, "Adventure Camping"))
        arrayList.add(getDummyEntity(122, "Bring Pluto Back!"))
        arrayList.add(getDummyEntity(123, "Existential Boy’s Night Out"))
        arrayList.add(getDummyEntity(124, "New York Punks Polka under the Planets"))
        arrayList.add(getDummyEntity(125, "Dutch Auction Rollover"))
        arrayList.add(getDummyEntity(126, "Hoist the Local Rag"))
        arrayList.add(getDummyEntity(127, "Dance Contagion"))
        arrayList.add(getDummyEntity(128, "Jimmy Buffet’s Granola Fantasy Canteen"))
        arrayList.add(getDummyEntity(129, "Forty Five Kids, Hot Dogs and Spaghetti"))
        arrayList.add(getDummyEntity(130, "Mannequin Mogul, Lower Pacific Heights, St Pittsburgh Rd, Sector 38, Broadway Etiquette, Near Grand Towers Bolinas, North Carolina."))
        arrayList.add(getDummyEntity(131, "Pikachu and Hello Kitty Fight to the Death"))
        arrayList.add(getDummyEntity(132, "Ice Cream Asocial"))
        arrayList.add(getDummyEntity(133, "LP Youth – Leadership Program for Youth"))
        arrayList.add(getDummyEntity(134, "The Summit for Performance"))
        arrayList.add(getDummyEntity(135, "Roman Candle Empire"))
        return arrayList
    }

    fun getDummyEntity(id: Long, dummyText: String): Entity {
        return object : Entity() {
            override val key: Long
                get() = id

            override var value: String = ""
                get() = dummyText
        }
    }

    fun getDummyEntity1(boolean: Boolean,id: Long, dummyText: String): Entity {
        return   object : Entity() {
            override val key: Long
            get() = 0

            override var value: String = ""
            get() = "Adventure Camping"
        }
    }
}
