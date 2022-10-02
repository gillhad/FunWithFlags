package com.example.fun_with_flags

import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.fun_with_flags.models.ContinentViewModel
import com.example.fun_with_flags.models.Country
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
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
        val args: ContinentFlagsArgs by navArgs()


        currentContinent = args.continentname
        continentText.setText(currentContinent)




        return view

    }



    fun addCountries(view: ImageView): Int {
        println("inicia la carga de datos")

        val countriesDb = db.collection("countries")
            .whereEqualTo(currentContinent, true)
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


}