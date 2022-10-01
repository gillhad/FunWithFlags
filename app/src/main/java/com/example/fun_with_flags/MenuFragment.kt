package com.example.fun_with_flags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fun_with_flags.models.ModeEnum


class MenuFragment : Fragment() {

    val gameModes: List<ModeEnum> = listOf(ModeEnum.ALLGFLAGS,ModeEnum.CONTINENTFLAGS,ModeEnum.RANDOMFLAGS)
    var currentGameMode: Int = 0
    var currentContinentPosition: Int = 0
    val continents: List<String> = listOf("Africa","America", "Asia","Europa","Oceania")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val gameStart: Button = view.findViewById(R.id.start_btn)
        val gameMode:Button = view.findViewById(R.id.game_mode)
        //var bundle = bundleOf("CONTINENT_NAME" to ContinentFlags)

        gameStart.setOnClickListener(){
            when(currentGameMode){
                0 -> findNavController().navigate(R.id.action_menuFragment_to_flagFullWrite)
                1 -> findNavController().navigate(R.id.action_menuFragment_to_continentFlags)
                2 -> GameModeSelection(gameMode,ModeEnum.RANDOMFLAGS)
            }

        }

        gameMode.setOnClickListener(){
        if(currentGameMode == 2){
            currentGameMode = 0
        }else{
            currentGameMode++
        }
            when(currentGameMode){
                0 -> GameModeSelection(gameMode,ModeEnum.ALLGFLAGS)
                1 -> GameModeSelection(gameMode,ModeEnum.CONTINENTFLAGS)
                2 -> GameModeSelection(gameMode,ModeEnum.RANDOMFLAGS)
            }


        }



        return view
    }


    private fun GameModeSelection(modeButton:Button,mode: ModeEnum){
        when(mode){
            ModeEnum.ALLGFLAGS-> modeButton.setText("ALL FLAGS")
            ModeEnum.CONTINENTFLAGS-> modeButton.setText("CONTINENT FLAGS")
            ModeEnum.RANDOMFLAGS-> modeButton.setText("RANDOM FLAGS")
        }

    }

    private fun continentArgument(){
        var bundle = bundleOf(continents[currentContinentPosition] to ContinentFlags)


    }

}