package ru.greatdevelopers.android_application.data

import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem

class FilmGroup {
    var groupName: String
        private set
    var filmList: List<FilmListItem>
        private set


    constructor(name: String, filmList: List<FilmListItem>){
        this.groupName = name
        this.filmList = filmList
    }
}