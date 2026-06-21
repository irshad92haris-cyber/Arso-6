package com.example.ui

import com.example.R

object DrawableResolver {
    fun resolve(name: String?): Int {
        return when (name) {
            "img_group_base_1781956222192" -> R.drawable.img_group_base_1781956222192
            "img_hero_banner_1781956181227" -> R.drawable.img_hero_banner_1781956181227
            "img_user_man_1781956194494" -> R.drawable.img_user_man_1781956194494
            "img_user_woman_1781956207922" -> R.drawable.img_user_woman_1781956207922
            "img_app_icon_1781956236809" -> R.drawable.img_app_icon_1781956236809
            else -> R.drawable.img_hero_banner_1781956181227 // Default fallback
        }
    }
}
