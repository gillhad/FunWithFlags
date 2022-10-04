package com.example.fun_with_flags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fun_with_flags.models.CheckMenuModeViewModel
import com.example.fun_with_flags.models.ContinentViewModel
import com.example.fun_with_flags.models.ModeEnum


class MenuFragment : Fragment() {

    val gameModes: List<ModeEnum> = listOf(ModeEnum.ALLGFLAGS, ModeEnum.CONTINENTFLAGS)
    var currentGameMode: Int = 0
    var currentContinentPosition: Int = 0
    var checkMode:Boolean = false
    val continents: List<String> = listOf("Africa", "America", "Asia", "Europa", "Oceania")
    val continentsFlags: List<String> = listOf(
        "continent_africa",
        "continent_america",
        "continent_asia",
        "continent_europa",
        "continent_oceania"
    )
    private val continentViewModel: ContinentViewModel by activityViewModels()
    private val checkMenuModeViewModel: CheckMenuModeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        val gameStart: Button = view.findViewById(R.id.start_btn)
        val gameMode: Button = view.findViewById(R.id.game_mode)
        val gameDiff: Button = view.findViewById(R.id.game_mode_diff)

        val leftButton: ImageButton = view.findViewById(R.id.continent_left)
        val rightButton: ImageButton = view.findViewById(R.id.continent_right)
        val imageFlag: ImageView = view.findViewById(R.id.continent_show_image)

        checkMenuModeViewModel.selectiItem("world")

        /**
         * BUTTON FUNCTIONS
         * */
        gameStart.setOnClickListener() {
           NavModeStart(gameMode)
        }

        gameMode.setOnClickListener() {
            GameModeController(gameMode,leftButton,rightButton,imageFlag)
        }

        gameDiff.setOnClickListener() {
            DifficultSelection()
            if (checkMode) {
                gameDiff.setText("CHECK")
            } else {
                gameDiff.setText("ESCRIBE")
            }
        }




        return view
    }


    /**
     * Game Modes Selection
     *
     * */
    private fun GameModeSelection(modeButton: Button, mode: ModeEnum) {
        when (mode) {
            ModeEnum.ALLGFLAGS -> modeButton.setText(R.string.world)
            ModeEnum.CONTINENTFLAGS -> modeButton.setText(R.string.continents)
        }

    }

    private fun GameModeController(gameMode:Button,leftButton: ImageButton, rightButton:ImageButton, imageFlag:ImageView) {
        if (currentGameMode == gameModes.size - 1) {
            currentGameMode = 0
            checkMenuModeViewModel.selectiItem("world")
            println(checkMenuModeViewModel.checkMode.value)
        } else {
            checkMenuModeViewModel.selectiItem("continente")
            println(checkMenuModeViewModel.checkMode.value)
            currentGameMode++
        }


        when (currentGameMode) {
            0 -> {
                GameModeSelection(gameMode, ModeEnum.ALLGFLAGS)
                leftButton.isVisible = false
                rightButton.isVisible = false
                imageFlag.setImageResource(
                    resources.getIdentifier(
                        "world",
                        "drawable",
                        activity?.packageName
                    )
                )
            }
            1 -> {
                GameModeSelection(gameMode, ModeEnum.CONTINENTFLAGS)
                leftButton.isVisible = true
                rightButton.isVisible = true
                imageFlag.setImageResource(
                    resources.getIdentifier(
                        continentsFlags[currentContinentPosition],
                        "drawable",
                        activity?.packageName
                    )
                )
            }


        }
    }


    /**
     *Difficult selection
     *
     */

    private fun DifficultSelection() {
       checkMode = !checkMode
    }



    /**
     * NAVIGATION
     * **/

    private fun NavModeStart(gameMode: Button) {
        when (currentGameMode) {
            0 -> {
                if (!checkMode) {
                    findNavController().navigate(R.id.action_menuFragment_to_flagFullWrite)
                } else {
                    findNavController().navigate(R.id.action_menuFragment_to_checkFlagLevel)
                }

            }
            1 -> {
                if (!checkMode) {
                    findNavController().navigate(R.id.action_menuFragment_to_continentFlags)
                } else {
                    findNavController().navigate(R.id.action_menuFragment_to_checkFlagLevel)
                }

            }

        }
    }

}