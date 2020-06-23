package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*
import kotlin.math.ln

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val intent = intent
        val stateName = intent.getStringExtra("State chosen")
        val infoMap  = intent.getSerializableExtra("Info map") as HashMap<String?, StateInfo>

        for((key, values) in infoMap){
            println("$key : ${values.death} , ${values.positive}")
        }

        nameOfState.text = stateName
        numPosCases.text = infoMap[stateName]?.positive.toString()
        numNegCases.text = infoMap[stateName]?.negative.toString()
        numDeaths.text = infoMap[stateName]?.death.toString()
        numHospCurr.text = infoMap[stateName]?.hospitalizedCurrently.toString()
        numRecovered.text = infoMap[stateName]?.recovered.toString()
    }
}

