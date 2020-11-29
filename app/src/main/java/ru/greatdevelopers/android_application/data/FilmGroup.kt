package ru.greatdevelopers.android_application.data

import ru.greatdevelopers.android_application.data.model.Film

class FilmGroup {
    var groupName: String
        private set
    var filmList: List<Film>
        private set


    constructor(name: String, filmList: List<Film>){
        this.groupName = name
        this.filmList = filmList
    }
}