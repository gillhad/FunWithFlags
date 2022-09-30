package com.example.fun_with_flags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class MenuFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val gameStart: Button = view.findViewById(R.id.start_btn)

        gameStart.setOnClickListener(){
            findNavController().navigate(R.id.action_menuFragment_to_flagFullWrite)
        }

        return view
    }



}