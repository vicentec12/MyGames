package br.com.vicentec12.mygames.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.navOptions
import br.com.vicentec12.mygames.R

fun NavController.navigateWithAnim(directions: NavDirections) {
    navigate(directions, getDefaultAnimationOptions())
}

fun NavController.getDefaultAnimationOptions() = navOptions {
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left
        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}