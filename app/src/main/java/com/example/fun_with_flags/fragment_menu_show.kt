package com.example.fun_with_flags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.isVisible

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_menu_show.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_menu_show : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var menuFragment:MenuFragment = MenuFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_show, container, false)


       val rightButton:ImageButton = view.findViewById(R.id.continent_right)
       val leftButton:ImageButton = view.findViewById(R.id.continent_left)
        val imageFlag: ImageView = view.findViewById(R.id.continent_show_image)

        rightButton.setOnClickListener(){
            menuFragment.rightClick()
        }

        leftButton.setOnClickListener(){
            menuFragment.leftClick()
        }



        return view

    }

    fun changeContinent(imageFlag:ImageView, flag:String){
        imageFlag.setImageResource(resources.getIdentifier(flag,
            "drawable",
            activity?.packageName))
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_menu_show.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_menu_show().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}