package com.example.weekflex.Fragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Task
import com.example.weekflex.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
        fun newInstance(userId: Int): CategoryBottomFragment {
            var bundle = Bundle()
            bundle.putInt("userId", userId)
            var fragment = CategoryBottomFragment()
            fragment.arguments = bundle
            return fragment
        }

        private lateinit var category: Category
        val instance: CategoryBottomFragment
            get() = CategoryBottomFragment()
        val TAG = "bottomSheet"

        fun showCategory(fragmentManager: FragmentManager, passedCategory: Category) {
            category = passedCategory
            instance.show(fragmentManager, TAG)

            instance.setFragmentResultListener(requestKey(category)) { requestKey, bundle ->
                val result = bundle.getString(bundleKey(category))
                Log.e("와라", "$result")
            }
        }

        fun requestKey(category: Category) = "${category.categoryId}"
        fun bundleKey(category: Category) = "${category.categoryName}${category.categoryColor}"
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

        with(cancleBtn) { setOnClickListener {
            dialog?.also { getBottomSheetDialog(it).state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN }
        } }

        return view
    }

    fun getBottomSheetDialog(dialog: DialogInterface): BottomSheetBehavior<FrameLayout> {
        val d = dialog as BottomSheetDialog

        val bottomSheet =
            d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?

        return BottomSheetBehavior.from(bottomSheet!!)
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
        dismiss()

    }
}