package com.example.weekflex.Fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Adapter.CategoryColorListAdapter
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.categoryToStarImage
import com.example.weekflex.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_complete_make_routine.*

private val availColors = listOf(
   1,2,3
)

private val notAvailColors = listOf(
    4,5,6,7,8,9,10,11,12,13,14,15
)
class CategoryBottomFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var cancleBtn: ImageView
    private lateinit var okBtn: ImageView
    private lateinit var categoryEditMent : TextView
    private lateinit var categoryImg : ImageView
    private lateinit var categoryName: EditText
    private lateinit var dialogView : View
    private lateinit var availableColors: RecyclerView
    private lateinit var notAvailableColors: RecyclerView

    companion object {
        private lateinit var category: Category
        val instance: CategoryBottomFragment
            get() = CategoryBottomFragment()
        val TAG = "bottomSheet"

//        fun showCategory(supportFragmentManager: FragmentManager, passedCategory: Category) {
//            category = passedCategory
//            instance.show(supportFragmentManager, TAG)
//
//            instance.setFragmentResultListener(requestKey(category)) { requestKey, bundle ->
//                val result = bundle.getString(bundleKey(category))
//                Log.e("와라", "$result")
//            }
//        }

//        fun requestKey(category: Category) = "${category.categoryId}"
//        fun bundleKey(category: Category) = "${category.categoryName}${category.categoryColor}"
    }

    fun showCategory(supportFragmentManager: FragmentManager, passedCategory: Category) {
        category = passedCategory
        instance.show(supportFragmentManager, TAG)
        Log.d("category Name:",passedCategory.categoryName.toString())


//        instance.setFragmentResultListener(requestKey(category)) { requestKey, bundle ->
//            val result = bundle.getString(bundleKey(category))
//            Log.e("와라", "$result")
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.all_category_color_list_layout, container, false)
        dialogView = view

        cancleBtn = dialogView.findViewById(R.id.all_category_cancel)
        okBtn = dialogView.findViewById(R.id.all_category_check)
        categoryEditMent = dialogView.findViewById(R.id.all_category_edit_text)
        categoryImg = dialogView.findViewById(R.id.all_category_selectedCategory_img)
        categoryName = dialogView.findViewById(R.id.all_category_selectedCategory_name)
        availableColors = dialogView.findViewById(R.id.all_category_available_colors)
        notAvailableColors = dialogView.findViewById(R.id.all_category_not_available_colors)

        categoryName.setText(category.categoryName)
        categoryToStarImage[category.categoryColor]?.let { categoryImg.setImageResource(it) }


        availableColors.adapter = CategoryColorListAdapter(inflater, availableColors = availColors,
            selectedColor = category.categoryColor
        )
        availableColors.layoutManager = GridLayoutManager(this.context, 1,
            GridLayoutManager.HORIZONTAL, false)

        notAvailableColors.adapter = CategoryColorListAdapter(inflater, availableColors = notAvailColors,
            selectedColor = null
        )
        notAvailableColors.layoutManager = GridLayoutManager(this.context, 2,
            GridLayoutManager.HORIZONTAL, false)
        with(cancleBtn) { setOnClickListener {
            dialog?.also { getBottomSheetDialog(it).state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN }
        } }

        return view
    }

    fun getBottomSheetDialog(dialog: DialogInterface): BottomSheetBehavior<FrameLayout> {
        val d = dialog as BottomSheetDialog

        val bottomSheet =
            d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?

        if (bottomSheet != null) {
//            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT

        }
        return BottomSheetBehavior.from(bottomSheet!!)

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
        dismiss()

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialog ->
            getBottomSheetDialog(dialog).also {
                it.peekHeight = 0
                it.state = BottomSheetBehavior.STATE_COLLAPSED
                it.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        Log.d("Dismiss", dialog.dismissWithAnimation.toString())
        dialog.dismissWithAnimation = true
        return dialog
    }
}