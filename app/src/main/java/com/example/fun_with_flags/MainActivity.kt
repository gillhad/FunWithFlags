package com.example.fun_with_flags

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonWriter
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
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
    val gameMode:String = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Capitole_preparation)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread.sleep(2000)

//        addCountries()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController =  navHostFragment.navController
        setupActionBarWithNavController(navController)

    }

//            fun addCountries() : Country {
//
    ////Añadir paises
//        val addcountrytodb = db.collection("countries")
//            .document("Colombia")
//            .set(hashMapOf(
//                "name" to "Colombia",
//                "flag" to "flag_colombia",
//                "continent" to "America"
//            ))



}

