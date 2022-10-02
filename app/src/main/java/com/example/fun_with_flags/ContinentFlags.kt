package com.example.fun_with_flags

import android.media.Image
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fun_with_flags.models.ContinentViewModel
import com.example.fun_with_flags.models.Country
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CONTINENT_NAME = "param1"
private val db = Firebase.firestore
private var dispatcher = Dispatchers.IO
var currentFlag = 0
var countries: ArrayList<Country> = ArrayList()
var countriesCheck: ArrayList<Country> = ArrayList()
var gameEnded = false
var currentPoints = 0
var currentTime = 0
var currentContinent = ""

/**
 * A simple [Fragment] subclass.
 * Use the [ContinentFlags.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContinentFlags : Fragment() {

    private val continentViewModel:ContinentViewModel by activityViewModels()
    val continents: List<String> = listOf("Africa","America", "Asia","Europa","Oceania")
    val continentsFlags: List<String> = listOf("continent_africa","continent_america","continent_asia","continent_europa","conitnen_oceania")
    var currentContinent:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_continent_flags, container, false)
        val continentText: TextView = view.findViewById(R.id.continent_text_mode)
        val flagView : ImageView = view.findViewById(R.id.flag_image_continent)
        val checkButton:Button = view.findViewById(R.id.btn_check_continent)
        val textFinal: TextView = view.findViewById(R.id.text_tv_continent)
        val answer: EditText? = view?.findViewById(R.id.text_et_continent)
        continentViewModel.continentPosition.observe(viewLifecycleOwner, Observer{
            set -> "Asia"
        })
        println(continentViewModel.continentPosition.value)
        currentContinent = continents[continentViewModel.continentPosition.value!!]

        continentText.setText(currentContinent)

        addCountries(flagView)

        checkButton.setOnClickListener() {
            if (answer != null) {
                checkAnswer(textFinal, answer, flagView)

            }
        }


        return view

    }



    fun addCountries(view: ImageView): Int {
        println("inicia la carga de datos")

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



        return countriesCheck.size



    }

    private fun randomCurrentFlag(size: Int) {

        println("hay ${size} paises")
        val rand: Int = Random.nextInt(0, size)
        currentFlag = rand
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContinentFlags().apply {
                arguments = Bundle().apply {
                    putString(CONTINENT_NAME, param1)
                }
            }
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


}