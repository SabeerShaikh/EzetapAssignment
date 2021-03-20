package com.myapplication.module.ui.acitvity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.myapplication.R
import com.myapplication.databinding.ActivityDisplay2Binding
import com.myapplication.domain.model.PersonData

class DisplayActivity : AppCompatActivity() {
    lateinit var uiData: PersonData
    lateinit var vBinding: ActivityDisplay2Binding

    companion object {
        private const val PARAM_DATA = "param_data"

        @JvmStatic
        fun getIntent(context: Context?, personData: PersonData): Intent {
            val intent = Intent(context, DisplayActivity::class.java)
            intent.putExtra(PARAM_DATA, personData)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vBinding = DataBindingUtil.setContentView(this, R.layout.activity_display2)
        vBinding.callback = this
        uiData = intent.getSerializableExtra(PARAM_DATA) as PersonData

        vBinding.name.text = "Name - " + uiData.name
        vBinding.phone.text = "Phone No - " + uiData.phoneNo
        vBinding.city.text = "City - " + uiData.city

    }
}