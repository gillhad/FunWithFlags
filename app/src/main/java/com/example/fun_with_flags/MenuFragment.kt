package com.example.fun_with_flags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fun_with_flags.models.ModeEnum


class MenuFragment : Fragment() {

    val gameModes: List<ModeEnum> = listOf(ModeEnum.ALLGFLAGS,ModeEnum.CONTINENTFLAGS,ModeEnum.RANDOMFLAGS)
    var currentGameMode: Int = 0
    var currentContinentPosition: Int = 0
    val continents: List<String> = listOf("Africa","America", "Asia","Europa","Oceania")
    val continentsFlags: List<String> = listOf("continent_africa","continent_america","continent_asia","continent_europa","conitnen_oceania")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val gameStart:Button = view.findViewById(R.id.start_btn)
        val gameMode:Button = view.findViewById(R.id.game_mode)

        val leftButton:ImageButton = view.findViewById(R.id.continent_left)
        val rightButton:ImageButton = view.findViewById(R.id.continent_right)
        val imageFlag:ImageView = view.findViewById(R.id.continent_show_image)


        gameStart.setOnClickListener(){
            val action = MenuFragmentDirections.actionMenuFragmentToContinentFlags(continents[currentContinentPosition])
            when(currentGameMode){
                0 -> findNavController().navigate(R.id.action_menuFragment_to_flagFullWrite)
                1 -> findNavController().navigate(action)
                2 -> GameModeSelection(gameMode,ModeEnum.RANDOMFLAGS)
            }

        }

        if(leftButton.isVisible){
            println("le doy al boton izk")
            leftButton.setOnClickListener() {
                if (currentContinentPosition == 0) {
                    currentContinentPosition = continentsFlags.size
                } else {
                    currentContinentPosition--
                }
               // continentFlagFragment.changeContinent(imageFlag,continentsFlags[currentContinentPosition])
            }
        }

        if(rightButton.isVisible){
            println("le doy al boton izk")
            if(currentContinentPosition == continentsFlags.size){
                currentContinentPosition = 0
            }else{
                currentContinentPosition++
            }

            //continentFlagFragment.changeContinent(imageFlag,continentsFlags[currentContinentPosition])
        }

        gameMode.setOnClickListener(){

        if(currentGameMode == 2){
            currentGameMode = 0
        }else{
            currentGameMode++
        }


            when(currentGameMode){
                0 -> {GameModeSelection(gameMode,ModeEnum.ALLGFLAGS) }
                1 -> {GameModeSelection(gameMode,ModeEnum.CONTINENTFLAGS)
                    leftButton.isVisible = true
                    rightButton.isVisible = true
                    imageFlag.setImageResource(resources.getIdentifier(continentsFlags[currentContinentPosition],
                        "drawable",
                        activity?.packageName))
                }
                2 -> GameModeSelection(gameMode,ModeEnum.RANDOMFLAGS)
            }


        }



        return view
    }

    fun leftClick(){
        if(currentContinentPosition == continentsFlags.size){
            currentContinentPosition = 0
        }else{
            currentContinentPosition++
        }
    }

    fun rightClick(){
        println("le doy al boton izk")
        if(currentContinentPosition == continentsFlags.size){
            currentContinentPosition = 0
        }else{
            currentContinentPosition++
        }
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