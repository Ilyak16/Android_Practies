package ci.nsu.moble.main

import androidx.annotation.StringRes
enum class AppScreens(@StringRes val title: Int){
    Screen1(title = R.string.screen1_title),
    Screen2(title = R.string.screen2_title),
    Screen3(title = R.string.screen3_title),
    Screen4(title = R.string.screen4_title),
    Screen5(title = R.string.screen5_title)
}