package com.example.fun_with_flags

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fun_with_flags.models.CheckMenuModeViewModel
import com.example.fun_with_flags.models.ContinentViewModel
import com.example.fun_with_flags.models.Country
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random


class CheckFlagLevel : Fragment() {

    val modeTypeViewer: CheckMenuModeViewModel  by activityViewModels()
    val continentViewer:ContinentViewModel by activityViewModels()

    var typeMode: String = "world"
    private val db = Firebase.firestore
    var currentFlag = 0
    var countries: ArrayList<Country> = ArrayList()
    var countriesCheck: ArrayList<Country> = ArrayList()
    val continents: List<String> = listOf("Africa", "America", "Asia", "Europa", "Oceania")
    var gameEnded = false
    var currentPoints = 0
    var currentTime = 0
    var currentContinent:String =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_check_flag_level, container, false)
        val flagView :ImageView = view.findViewById(R.id.flag_image_check)
        val checkButton1 :Button = view.findViewById(R.id.flag_opt_1)
        val checkButton2 :Button = view.findViewById(R.id.flag_opt_2)
        val checkButton3 :Button = view.findViewById(R.id.flag_opt_3)
        val checkButton4 :Button = view.findViewById(R.id.flag_opt_4)

        typeMode = modeTypeViewer.checkMode.value!!
        currentContinent = continents[continentViewer.continentPosition.value!!]

        addCountries(flagView, typeMode)

        checkButton1.setOnClickListener(){
            checkIfCorrect(checkButton1,flagView)
        }
        checkButton2.setOnClickListener(){
            checkIfCorrect(checkButton2,flagView)
        }
        checkButton3.setOnClickListener(){
            checkIfCorrect(checkButton3,flagView)
        }
        checkButton4.setOnClickListener(){
            checkIfCorrect(checkButton4,flagView)
        }

        return view
    }

    fun addCountries(view: ImageView, mode:String): Int {
    when(mode){
        "world" -> {
            val countriesDb = db.collection("countries")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        println("el documento es:")
                        println(document.get("continent"))
                        if(document.get("continent")==currentContinent) {
                            countries.add(
                                Country(
                                    name = "${document.get("name")}",
                                    continent = "${document.get("continent")}",
                                    flag = "${document.get("flag")}",
                                    null
                                )
                            )
                        }
                        println("+1")
                    }
                    println(countries[0])
                    countriesCheck.addAll(countries)
                    randomCurrentFlag(countriesCheck.size)
                    reDraw(view)

                }.addOnFailureListener { exception ->
                    println("no funciona algo")
                }
        }
        "continent" -> {
            val countriesDb = db.collection("countries")
                .whereGreaterThanOrEqualTo("continent",currentContinent)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        println("el documento es:")
                        println(document.get("continent"))
                        if(document.get("continent")==currentContinent) {
                            countries.add(
                                Country(
                                    name = "${document.get("name")}",
                                    continent = "${document.get("continent")}",
                                    flag = "${document.get("flag")}",
                                    null
                                )
                            )
                        }
                        println("+1")
                    }
                    println(countries[0])
                    countriesCheck.addAll(countries)
                    randomCurrentFlag(countriesCheck.size)
                    reDraw(view)

                }.addOnFailureListener { exception ->
                    println("no funciona algo")
                }
        }
    }
        return countriesCheck.size
    }

    private fun randomCurrentFlag(size: Int) {

        println("hay ${size} paises")
        val rand: Int = Random.nextInt(0, size)
        currentFlag = rand
    }

    fun reDraw(view: ImageView) {
        view.setImageResource(
            resources.getIdentifier(
                countriesCheck[currentFlag].flag,
                "drawable",
                activity?.packageName
            )
        )
    }

    fun checkAnswer(textCheck: TextView, answer: EditText, flagView: ImageView) {
        val checkText = answer?.text.toString()
        println(checkText)
        if (checkText.equals(countriesCheck[currentFlag].name)) {
            textCheck.text = "Correcto"
            nextCountry(flagView)
            answer.setText("")
            GlobalScope.launch {
                Thread.sleep(1000)
                textCheck.setText("")
            }

        } else {
            textCheck.text = "Incorrecto"
        }
    }

    private fun nextCountry(flagView: ImageView) {
        deletePosition()
        gameEnded()
        if (!gameEnded) {
            randomCurrentFlag(countriesCheck.size)
            Thread.sleep(500)
            reDraw(flagView)
        } else {
            findNavController().navigate(R.id.action_flagFullWrite_to_menuFragment)
        }

    }

    private fun deletePosition() {
        countriesCheck.removeAt(currentFlag)
    }

    private fun gameEnded() {
        println("quedan ${countriesCheck.size} paises")
        if (countriesCheck.size == 0) {
            println("Se acabó")
            gameEnded = true
        } else {
            println("aun quedan paises")
        }
    }

    fun checkIfCorrect(btn:Button, flagView: ImageView){
        if(btn.text==currentContinent){
            btn.setBackgroundColor(R.color.teal_700)
            Thread.sleep(200)
            nextCountry(flagView)
        }else{
            btn.setBackgroundColor(R.color.Orange_700)
        }

    }



}