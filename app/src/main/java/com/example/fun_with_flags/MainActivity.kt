package com.example.fun_with_flags

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonWriter
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fun_with_flags.models.Country
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    val db = Firebase.firestore
    var dispatcher = Dispatchers.IO
    var countries: ArrayList<Country> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Capitole_preparation)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread.sleep(2000)

//        addCountries()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController =  navHostFragment.navController

    }

//            fun addCountries() : Country {
//                var response = CountryList()
//
//
//                    val countriesDb = db.collection("countries")
//                        .get()
//                        .addOnSuccessListener { documents ->
//                            for (document in documents) {
//                                countries.add(
//                                    Country(
//                                        name = "${document.get("name")}",
//                                        continent = "${document.get("continent")}",
//                                        flag = "${document.get("flag")}",
//                                        null
//                                    )
//                                )
//                                println("hay documentos")
//                                println(document.get("name"))
//                                println(countries.size)
//                            }
//                        }.addOnFailureListener { exception ->
//                            println("no funciona algo")
//                        }

            }

