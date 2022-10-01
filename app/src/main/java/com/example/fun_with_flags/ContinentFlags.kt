package com.example.fun_with_flags

import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CONTINENT_NAME = "param1"
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
        val that = args.continentname
        continentText.setText(that)

        return view

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


}