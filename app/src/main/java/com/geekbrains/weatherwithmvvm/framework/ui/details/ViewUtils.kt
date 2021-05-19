package com.geekbrains.weatherwithmvvm.framework.ui.details

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun getHeaderPicture(cityName: String): String {
    when (cityName) {
        "Москва" -> return "https://freepngimg.com/download/temple/" +
                "93582-building-basils-moscow-petersburg-saint-landmark-cathedral.png"
        "Санкт-Петербург" -> return "https://e7.pngegg.com/pngimages/170/463/png-" +
                "clipart-saint-petersburg-skyline-silhouette-animals-building-thumbnail.png"
        "Новосибирск" -> return "https://sun9-28.userapi.com/" +
                "4hHJFGDn8MIZhkqZZgxc7RR0Z7l1MYuif9djrQ/UHnmoOXypso.jpg"
        "Екатеринбург" -> return "https://unipo.ru/assets/cache/images/" +
                "ekb1-540x250-1c0.png"
        "Нижний Новгород" -> return "https://russouvenirs.ru/files/tovar/" +
                "3000_3100/3062/025-10f-76k21_anons.jpg"
        "Казань" -> return "https://skazka-deti.ru/upload/iblock/6c0/" +
                "6c02cb5c2904889f17b2667261953223.jpg"
        "Челябинск" -> return "https://bezopasnost112.ru/wp-content/uploads/" +
                "2020/07/12bb932701244df7827bb8c6fbfeeef0-330x206.jpg"
        "Омск" -> return "https://argonek55.ru/wp-content/uploads/2019/01/" +
                "cropped-1233-768x204.png"
        "Ростов-на-Дону" -> return "https://zalog124.ru/wp-content/uploads/2018/" +
                "06/zaimpodnedvij-768x203.jpg"
        "Лондон" -> return "https://1.bp.blogspot.com/-zI_fpKSz44s/XnIXHJH3fWI/" +
                "AAAAAAAAAPQ/cSuEIBVjAzoP7uwizmonBxwbD7NT6MDDwCLcBGAsYHQ/s320/big-ben-clipart-14.png"
        "Токио" -> return "https://png.pngitem.com/pimgs/s/" +
                "139-1399802_tokyo-japan-aljanh-tokyo-skyline-with-grey-buildings.png"
        "Париж" -> return "https://c7.hotpng.com/preview/170/693/168/eiffel-" +
                "tower-arc-de-triomphe-belxe9m-tower-wallpaper-eiffel-tower-in-paris-three-" +
                "thumbnail.jpg"
        "Берлин" -> return "https://secure.meetupstatic.com/photos/event/8/d/f/5/" +
                "600_475296341.jpeg"
        "Рим" -> return "https://w7.pngwing.com/pngs/363/138/png-transparent-" +
                "colosseum-roman-forum-pantheon-amphitheatrum-castrense-amphitheater-scenic-" +
                "colosseum-building-medieval-architecture-rome-thumbnail.png"
        "Минск" -> return "https://prometr.by/upload/medialibrary/3a2/" +
                "3a2f4e848c7bdfe2998ef2f87f6658e6.jpg"
        "Стамбул" -> return "https://www.pngkey.com/png/detail/" +
                "160-1607821_open-istanbul-vector-png.png"
        "Вашингтон" -> return "https://w7.pngwing.com/pngs/92/441/png-transparent" +
                "-united-states-capitol-dome-capitol-records-building-texas-state-capitol-united" +
                "-states-congress-hill-building-united-states-landmark-thumbnail.png"
        "Киев" -> return "https://www.touropia.com/gfx/b/2019/10/" +
                "ukraine_places-350x200.jpg"
        "Пекин" -> return "https://img.lovepik.com/element/40038/9227.png_300.png"
        else -> return "https://autogear.ru/misc/i/thumb/n/9/5/8/8/3/1/i/958831.jpg"
    }
}