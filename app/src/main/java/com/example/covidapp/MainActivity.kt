package com.example.covidapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.Serializable
import java.lang.reflect.Type

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private val client = OkHttpClient()
    private val infoMap : HashMap<String, StateInfo> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://covidtracking.com/api/v1/states/current.json"
        runJSON(url);

        //set up buttons tags being their respective state abbreviations
        run {
            stateAL.tag = "AL"
            stateAK.tag = "AK"
            stateAR.tag = "AR"
            stateAZ.tag = "AZ"
            stateCA.tag = "CA"
            stateCO.tag = "CO"
            stateCT.tag = "CT"
            stateDE.tag = "DE"
            stateFL.tag = "FL"
            stateGA.tag = "GA"
            stateHI.tag = "HI"
            stateIA.tag = "IA"
            stateID.tag = "ID"
            stateIL.tag = "IL"
            stateIN.tag = "IN"
            stateKS.tag = "KS"
            stateKY.tag = "KY"
            stateLA.tag = "LA"
            stateMA.tag = "MA"
            stateMD.tag = "MD"
            stateME.tag = "ME"
            stateMI.tag = "MI"
            stateMN.tag = "MN"
            stateMO.tag = "MO"
            stateMS.tag = "MS"
            stateMT.tag = "MT"
            stateNC.tag = "NC"
            stateND.tag = "ND"
            stateNE.tag = "NE"
            stateNH.tag = "NH"
            stateNJ.tag = "NJ"
            stateNM.tag = "NM"
            stateNV.tag = "NV"
            stateNY.tag = "NY"
            stateOH.tag = "OH"
            stateOK.tag = "OK"
            stateOR.tag = "OR"
            statePA.tag = "PA"
            stateRI.tag = "RI"
            stateSC.tag = "SC"
            stateSD.tag = "SD"
            stateTN.tag = "TN"
            stateTX.tag = "TX"
            stateUT.tag = "UT"
            stateVA.tag = "VA"
            stateVT.tag = "VT"
            stateWA.tag = "WA"
            stateWV.tag = "WV"
            stateWY.tag = "WY"
        }
        //set on click listeners for buttons
        run {
            stateAL.setOnClickListener(this)
            stateAK.setOnClickListener(this)
            stateAZ.setOnClickListener(this)
            stateAR.setOnClickListener(this)
            stateCA.setOnClickListener(this)
            stateCO.setOnClickListener(this)
            stateCT.setOnClickListener(this)
            stateDE.setOnClickListener(this)
            stateFL.setOnClickListener(this)
            stateGA.setOnClickListener(this)
            stateHI.setOnClickListener(this)
            stateIA.setOnClickListener(this)
            stateID.setOnClickListener(this)
            stateIL.setOnClickListener(this)
            stateIN.setOnClickListener(this)
            stateKS.setOnClickListener(this)
            stateKY.setOnClickListener(this)
            stateLA.setOnClickListener(this)
            stateMA.setOnClickListener(this)
            stateMD.setOnClickListener(this)
            stateME.setOnClickListener(this)
            stateMI.setOnClickListener(this)
            stateMN.setOnClickListener(this)
            stateMO.setOnClickListener(this)
            stateMS.setOnClickListener(this)
            stateMT.setOnClickListener(this)
            stateNC.setOnClickListener(this)
            stateND.setOnClickListener(this)
            stateNE.setOnClickListener(this)
            stateNH.setOnClickListener(this)
            stateNJ.setOnClickListener(this)
            stateNM.setOnClickListener(this)
            stateNV.setOnClickListener(this)
            stateNY.setOnClickListener(this)
            stateOH.setOnClickListener(this)
            stateOK.setOnClickListener(this)
            stateOR.setOnClickListener(this)
            statePA.setOnClickListener(this)
            stateRI.setOnClickListener(this)
            stateSC.setOnClickListener(this)
            stateSD.setOnClickListener(this)
            stateTN.setOnClickListener(this)
            stateTX.setOnClickListener(this)
            stateUT.setOnClickListener(this)
            stateVA.setOnClickListener(this)
            stateVT.setOnClickListener(this)
            stateWA.setOnClickListener(this)
            stateWV.setOnClickListener(this)
            stateWY.setOnClickListener(this)
        }

    }

    /*
        runJSON takes a url string for an API and takes JSON and using
        Gson builder we format it into the model object we need
     */
    private fun runJSON(url : String) {
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {e.printStackTrace()}

            override fun onResponse(call: Call, response: Response) {
                //println(response.body?.string())
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val collectionType : Type? = object :
                    TypeToken<ArrayList<StateInfo?>?>(){}.type


                val usData : ArrayList<StateInfo> = gson.fromJson(body, collectionType)

                //println(usData[0].state)
                buildHashMap(usData)
                //print out just to be sure
                for((key, values) in infoMap){
                    println("$key : ${values.death} , ${values.positive}")
                }

            }

        })

    }


    /*
        Takes API Json data and builds a Hash Map with the ArrayList of info
        that holds StateInfo class, the Keys are the state Abbreviations
        values are values from StateInfo
     */
    private fun buildHashMap(usInfo : ArrayList<StateInfo>){
        //loop through usInfo
        //states are keys rest of info is thrown into the StateInfo
        for (state in usInfo ){
            //key
            val key = state.state

            //add to map
            infoMap[key] = StateInfo(state.state,state.positive,state.negative,state.hospitalizedCurrently,state.recovered,state.death)
        }


    }

    override fun onClick(v: View?) {
        val intent = Intent(this, InfoActivity::class.java)
        intent.putExtra("Info map", infoMap)
        intent.putExtra("State chosen", v?.tag.toString())
        startActivity(intent)
    }


}


class StateInfo(val state: String, val positive : Int, val negative: Int,
                val hospitalizedCurrently: Int, val recovered: Int, val death: Int) : Serializable

/*
"date": 20200608,
"state": "AK",
"positive": 563,
"negative": 65349,
"hospitalizedCurrently": 7,
"recovered": 384,
"death": 10,
 */
