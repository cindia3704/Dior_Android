package com.example.weekflex.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.weekflex.Data.Category
import com.example.weekflex.Network.GlobalApplication
import com.example.weekflex.Network.GlobalApplication.Companion.currentCategory
import com.example.weekflex.R
import kotlinx.android.synthetic.main.activity_add_category.*

// GlobalApplication.currentCategory 로 저장한 카테고리 불러와서 다른 곳에서 사용 가능

class AddCategory : AppCompatActivity(){

    private lateinit var categoryNameView: EditText
    private lateinit var saveCategory_btn: Button
    private lateinit var cancelCategory_btn: Button
    private lateinit var red_btn: CardView
    private lateinit var yellow_btn: CardView
    private lateinit var blue_btn: CardView
    private lateinit var violet_btn: CardView
    private lateinit var purple_btn: CardView

    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        initView()
        setListener()
        category = Category(0,"",0)

    }

    private fun initView(){
        categoryNameView=findViewById(R.id.insertCategory)
        saveCategory_btn=findViewById(R.id.save_add_category)
        cancelCategory_btn=findViewById(R.id.cancel_add_category)
        red_btn=findViewById(R.id.btn_red)
        yellow_btn=findViewById(R.id.btn_yellow)
        blue_btn=findViewById(R.id.btn_blue)
        violet_btn=findViewById(R.id.btn_violet)
        purple_btn=findViewById(R.id.btn_purple)
    }

    private fun setListener(){


        cancelCategory_btn.setOnClickListener {
            finish()
        }

        red_btn.setOnClickListener{
            category.categoryColor = Color.parseColor("#FB4F55") // 58~72줄: 각 색깔 버튼 별 버튼 누르면 currentCaegory 의 색에 해당 색 int 변환 하여 저장함
        }
        yellow_btn.setOnClickListener{
            category.categoryColor = Color.parseColor("#FECF35")
        }
        blue_btn.setOnClickListener{
            category.categoryColor = Color.parseColor("#27A5EC")
        }
        violet_btn.setOnClickListener{
            category.categoryColor = Color.parseColor("#A167FE")
        }
        purple_btn.setOnClickListener{
            category.categoryColor = Color.parseColor("#6B4CFF")
        }

        saveCategory_btn.setOnClickListener {

            category.categoryName = categoryNameView.getText().toString()  //저장 전 edittext의 텍스트 저장됨
            GlobalApplication.currentCategory.add(category)                //저장 누르면 edittext의 텍스트 와 해당 색 int 저장됨
            finish()

            //GlobalApplication.currentCategory = Category(categoryNameView.getText().toString(),GlobalApplication.currentCategory.categoryColor)
            //Log.d("저장된 카테고리",""+GlobalApplication.currentCategory)  // save 버튼 누르면 var currentCategory: Category = Category("",0) 변수로 저장됨 로그 확인

        }
    }

}

