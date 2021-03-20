package com.myapplication

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myapplication.databinding.ActivityMainBinding
import com.myapplication.domain.model.PersonData
import com.myapplication.module.ui.acitvity.DisplayActivity
import com.networkmodule.config.DICommon
import com.networkmodule.config.EzetapCommonProducationDI
import com.networkmodule.module.ui.viewmodel.MainViewModel
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var vBinding: ActivityMainBinding
    private var viewModel: MainViewModel? = null
    private var diCommon: DICommon? = null
    var allEds = ArrayList<EditText>()
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diCommon = EzetapCommonProducationDI(Application())
        vBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vBinding.callback = this
        viewModel = ViewModelProvider(
            this,
            (diCommon as EzetapCommonProducationDI).provideViewModelFactory()
        ).get<MainViewModel>(MainViewModel::class.java)
        vBinding.pbMain.visibility = View.VISIBLE

        viewModel?.getImage()?.observe(this@MainActivity, Observer {
            if (it.success) {
                vBinding.pbMain.visibility = View.GONE
                val bmp = BitmapFactory.decodeStream(it.response.byteStream())
                addImageView(bmp)
            } else {
                toast("Data is not loaded")
                vBinding.pbMain.visibility = View.GONE

            }
        })
        viewModel?.getCustomUIData()?.observe(this@MainActivity, Observer {
            if (it.success) {
                Log.d("Data", it.response.uidata.first().uitype)
                addHeadingTextView(it.response.name)
                it.response.uidata.forEach(Consumer { uiItem ->
                    when (uiItem.uitype) {
                        "label" -> addTextView(uiItem.value)
                        "edittext" -> addEditText(index, uiItem.hint)
                        "button" -> addButton(uiItem.value)
                    }
                })
                vBinding.pbMain.visibility = View.GONE
            } else {
                toast(" Data is not loaded")
                vBinding.pbMain.visibility = View.GONE

            }
        })


    }

    private fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun addImageView(bmp: Bitmap) {
        val imgView = ImageView(this)
        imgView.setImageBitmap(bmp)
        vBinding.loMain.addView(imgView)
        imgView.layoutParams.height = 350
        imgView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        imgView.scaleType = ImageView.ScaleType.FIT_XY
    }

    private fun addHeadingTextView(label: String) {
        val textView = TextView(this)
        textView.text = label
        textView.gravity = Gravity.CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        textView.setTextColor(Color.BLACK)
        vBinding.loMain.addView(textView)
    }

    private fun addTextView(label: String) {
        val textView = TextView(this)
        textView.text = label
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView.setTextColor(Color.BLACK)
        vBinding.loMain.addView(textView)
    }

    private fun addEditText(editTextId: Int, hint: String) {
        val editText = EditText(this)
        editText.id = index
        editText.hint = hint
        allEds.add(editText)
        vBinding.loMain.addView(allEds[index])
        index++

    }

    private fun addButton(value: String) {
        var personData = PersonData()
        var name = String()
        var city = String()
        var phoneNo = String()

        val button = Button(this)
        button.text = value
        button.setBackgroundColor(Color.BLUE)
        button.setTextColor(Color.WHITE)
        vBinding.loMain.addView(button)

        button.setOnClickListener(View.OnClickListener {
            for (i in allEds.indices) {
                if (allEds[i].text.toString().isNotEmpty()) {
                    when (i) {
                        0 -> name = allEds[i].text.toString()
                        1 -> phoneNo = allEds[i].text.toString()
                        2 -> city = allEds[i].text.toString()
                    }
                    personData = PersonData(
                        name,
                        phoneNo,
                        city
                    )
                } else {
                    toast("All fields are mandatory")
                    break
                }
            }

            startActivity(
                DisplayActivity.getIntent(
                    applicationContext,
                    personData
                )
            )


        })

    }


}