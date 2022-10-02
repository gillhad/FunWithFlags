package com.example.fun_with_flags

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fun_with_flags.models.Country
import com.example.fun_with_flags.models.CountryList
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlin.random.Random


class FlagFullWrite : Fragment() {


    var currentFlag = 0
    var countries: ArrayList<Country> = ArrayList()
    var countriesCheck: ArrayList<Country> = ArrayList()
    var gameEnded = false
    var currentPoints = 0
    var currentTime = 0
    val db = Firebase.firestore
    var dispatcher = Dispatchers.IO

    private var param_country: String? = null

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_flag_full_write, container, false)

        val btn_1: Button = view.findViewById(R.id.btn_1)
        val textFinal: TextView = view.findViewById(R.id.text_tv)
        val answer: EditText? = view?.findViewById(R.id.text_et)
        val flagView: ImageView = view.findViewById(R.id.flag_image)


        addCountries(flagView)

        println(countriesCheck.size)

        btn_1.setOnClickListener() {
            if (answer != null) {
                checkAnswer(textFinal, answer, flagView)

            }
        }

        return view
    }



    private fun randomCurrentFlag(size: Int) {

        println("hay ${size} paises")
        val rand: Int = Random.nextInt(0, size)
        currentFlag = rand
    }


    fun addCountries(view: ImageView): Int {
        println("inicia la carga de datos")

        val countriesDb = db.collection("countries")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    countries.add(
                        Country(
                            name = "${document.get("name")}",
                            continent = "${document.get("continent")}",
                            flag = "${document.get("flag")}",
                            null
                        )
                    )

                    println("+1")
                }
                countriesCheck.addAll(countries)
                randomCurrentFlag(countriesCheck.size)
                reDraw(view)

            }.addOnFailureListener { exception ->
                println("no funciona algo")
            }



        return countriesCheck.size



    }


    fun checkAnswer(textCheck: TextView, answer: EditText, flagView: ImageView) {
        val checkText = answer?.text.toString()
        println(checkText)
        if (checkText.equals(countriesCheck[currentFlag].name)) {
            textCheck.text = "Correcto"
            GlobalScope.launch {
                nextCountry(flagView)
            }
            answer.setText("")
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

    private fun gameEnded() {
        println("quedan ${countriesCheck.size} paises")
        if (countriesCheck.size == 0) {
            println("Se acabó")
            gameEnded = true
        } else {
            println("aun quedan paises")
        }
    }

    private fun deletePosition() {
        countriesCheck.removeAt(currentFlag)
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

}