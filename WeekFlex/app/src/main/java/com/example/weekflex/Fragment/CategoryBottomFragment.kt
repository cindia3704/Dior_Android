package com.example.weekflex.Fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weekflex.Activity.routineList
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
    private lateinit var categoryDeleteBtn : TextView

    companion object {
        private lateinit var category: Category
        val instance: CategoryBottomFragment
            get() = CategoryBottomFragment()
        val TAG = "bottomSheet"

    }

    fun showCategory(supportFragmentManager: FragmentManager, passedCategory: Category) {
        category = passedCategory
        instance.show(supportFragmentManager, TAG)
        Log.d("category Name:",passedCategory.categoryName.toString())

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
        categoryDeleteBtn = dialogView.findViewById(R.id.all_category_delete_category_btn)

        categoryName.setText(category.categoryName)
        categoryToStarImage[category.categoryColor]?.let { categoryImg.setImageResource(it) }


        availableColors.adapter = CategoryColorListAdapter(this,inflater, availableColors = availColors,
            selectedColor = category.categoryColor, tag = "A"
        )
        availableColors.layoutManager = GridLayoutManager(this.context, 1,
            GridLayoutManager.HORIZONTAL, false)

        notAvailableColors.adapter = CategoryColorListAdapter(this,inflater, availableColors = notAvailColors,
            selectedColor = null, tag = "NA"
        )
        notAvailableColors.layoutManager = GridLayoutManager(this.context, 2,
            GridLayoutManager.HORIZONTAL, false)

        setListener()

        return view
    }
    fun setListener(){
        with(cancleBtn) { setOnClickListener {
            dialog?.also { getBottomSheetDialog(it).state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN }
        } }


        categoryDeleteBtn.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)

            builder.setMessage("카테고리에 포함된 할 일 ("+ category.taskList.size+") 이 모두\n" +
                    "삭제됩니다. 이대로 삭제를 진행할까요?")

            builder.setPositiveButton("삭제하기") { dialog, which ->
                // TODO: 서버 연결시 삭제 요청 보내기
            }

            builder.setNegativeButton("그만두기") { dialog, which ->
                dialog.dismiss()
            }

            val dialog: android.app.AlertDialog = builder.create()
            dialog.show()

            this.context?.let {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it, R.color.gray_4))
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it, R.color.color6))
            }
        }

        with(okBtn){setOnClickListener {
            val newCategoryName = categoryName.text.toString()
            Log.d("NEW CATEGORY NAME", newCategoryName)
            var found = false
            for(cat in categories) {
                if (cat.categoryName.equals(newCategoryName) && cat.categoryId!= category.categoryId) {
                    val builder: android.app.AlertDialog.Builder =
                        android.app.AlertDialog.Builder(context)
                    builder.setTitle("존재하는 카테고리")
                    builder.setMessage(
                        "이미 존재하는 카테고리 이름입니다.\n" +
                                "다른 이름을 입력해주세요."
                    )

                    builder.setPositiveButton("확인") { dialog, which ->
                        dialog.dismiss()
                    }


                    val dialog: android.app.AlertDialog = builder.create()
                    dialog.show()

                    this.context?.let {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(ContextCompat.getColor(it, R.color.color6))
                    }
                    found = true
                }
            }
            if(!found) {
                // TODO: 서버에 patch 요청!
                category.categoryName=newCategoryName
                dialog?.also { getBottomSheetDialog(it).state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN }
                Log.d("NEW!!! ", category.categoryName)
            }
        }
        }
    }
    fun changeStarImg(startImg:Int){
        categoryToStarImage[startImg]?.let { categoryImg.setImageResource(it) }
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