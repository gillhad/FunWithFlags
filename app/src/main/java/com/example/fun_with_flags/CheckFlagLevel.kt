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
import com.example.fun_with_flags.models.*
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
    val buttons: ArrayList<Button> = ArrayList()

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
        buttons.add(checkButton1)
        buttons.add(checkButton2)
        buttons.add(checkButton3)
        buttons.add(checkButton4)

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
            println("estamos juganod con todo el mundo")
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
                        println("El tamaño de paises actual es:")
                        println(countries.size)
                    }
                    println(countries[0])
                    countriesCheck.addAll(countries)
                    randomCurrentFlag()
                    reDraw(view)
                    buttonFlagText(buttons)

                }.addOnFailureListener { exception ->
                    println("no funciona algo")
                }
        }
        "continente" -> {
            println("ESTAMOS JUGANDO CON UN CONTINENTE")
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
                    randomCurrentFlag()
                    reDraw(view)
                    buttonFlagText(buttons)

                }.addOnFailureListener { exception ->
                    println("no funciona algo")
                }
        }
    }
        return countriesCheck.size
    }

    //Crea un Int random para seleccionar bandera
    private fun randomCurrentFlag() {
        val rand: Int = Random.nextInt(0, countriesCheck.size)
        currentFlag = rand
    }

    //Dibuja la bandera
    fun reDraw(view: ImageView) {
        view.setImageResource(
            resources.getIdentifier(
                countriesCheck[currentFlag].flag,
                "drawable",
                activity?.packageName
            )
        )
    }

    //Pasos tras acertar el pais
    private fun nextCountry(flagView: ImageView) {
        deletePosition()
        gameEnded()
        if (!gameEnded) {
            randomCurrentFlag()
            Thread.sleep(500)
            reDraw(flagView)
        } else {
            findNavController().navigate(R.id.action_flagFullWrite_to_menuFragment)
        }

    }

    //elimina la bandera cuando se acierta
    private fun deletePosition() {
        countriesCheck.removeAt(currentFlag)
    }

    //Termina el juego y te devuelve a la pantalla principal
    private fun gameEnded() {
        println("quedan ${countriesCheck.size} paises")
        if (countriesCheck.size == 0) {
            println("Se acabó")
            gameEnded = true
        } else {
            println("aun quedan paises")
        }
    }

    //Revisa si la respuesta es correcta, pinta la celda según si es correcta o no
    fun checkIfCorrect(btn:Button, flagView: ImageView){
        if(btn.text==countriesCheck[currentFlag].name){
            btn.setBackgroundColor(resources.getColor(R.color.purple_500))
            Thread.sleep(200)
            nextCountry(flagView)
            buttonFlagText(buttons)

        }else{
            println("incorrecto")
            btn.setBackgroundColor(resources.getColor(R.color.Orange_700))
        }

    }

    //Añade los textos de cada botón
    fun buttonFlagText(btn: ArrayList<Button>){
        var levelFlags: ArrayList<Country> =  ArrayList()
        levelFlags.clear()
        levelFlags.add(countriesCheck[currentFlag])
        levelFlags = getNewFlag(levelFlags)
        levelFlags.shuffle()
        var i = 0
        for(but in btn){
            but.setText(levelFlags[i].name)
            i++
        }
        resetButtons(buttons)
    }

    fun getNewFlag(levelFlags: ArrayList<Country>) :  ArrayList<Country>{
        var rand: Int= 0
        println(countries.size)
        for (i in 1..3) {
            do {
                rand = Random.nextInt(0, countries.size)
                println("el pais nuevo de la lista es ${countries[rand]}")

            } while (levelFlags.contains(countries[rand]))
            levelFlags.add(countries[rand])
        }
        println("el total de paises en el listado es ${levelFlags.size}")
        return  levelFlags
    }

    fun resetButtons(but: ArrayList<Button>){
        println("resetea los botones")
        for(btn in but){
            btn.setBackgroundColor(resources.getColor(R.color.Pine_700))
        }

    }


}