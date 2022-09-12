package com.mudhut.dvtappchallenge.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mudhut.dvtappchallenge.data.remote.models.*


class CurrentWeatherConverter {
    @TypeConverter
    fun toJson(obj: CurrentWeather): String {
        return Gson().toJson(obj) ?: "n/a"
    }

    @TypeConverter
    fun fromJson(string: String?): CurrentWeather? {
        return string?.let { Gson().fromJson(string, CurrentWeather::class.java) }
    }
}


class WeatherForecastConverter {
    @TypeConverter
    fun toJson(obj: WeatherForecast): String {
        return Gson().toJson(obj) ?: "n/a"
    }

    @TypeConverter
    fun fromJson(string: String?): WeatherForecast? {
        return string?.let { Gson().fromJson(string, WeatherForecast::class.java) }
    }
}


class WeatherListConverter {
    @TypeConverter
    fun toJson(weather: List<Weather>): String {
        return Gson()
            .toJson(
                weather,
                object : TypeToken<ArrayList<Weather>>() {}.type
            ) ?: "[]"
    }

    @TypeConverter
    fun fromJson(json: String): List<Weather> {
        return Gson()
            .fromJson<ArrayList<Weather>>(
                json,
                object : TypeToken<ArrayList<Weather>>() {}.type
            ) ?: emptyList()
    }
}


class ListElementListConverter {
    @TypeConverter
    fun toJson(weather: List<ListElement>): String {
        return Gson()
            .toJson(
                weather,
                object : TypeToken<ArrayList<ListElement>>() {}.type
            ) ?: "[]"
    }

    @TypeConverter
    fun fromJson(json: String): List<ListElement> {
        return Gson()
            .fromJson<ArrayList<ListElement>>(
                json,
                object : TypeToken<ArrayList<ListElement>>() {}.type
            ) ?: emptyList()
    }
}


class WeatherConverter {
    @TypeConverter
    fun toJson(obj: Weather): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJson(string: String): Weather {
        return Gson().fromJson(string, Weather::class.java)
    }
}


class CoordConverter {
    @TypeConverter
    fun toJson(obj: Coord): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJson(string: String): Coord {
        return Gson().fromJson(string, Coord::class.java)
    }
}


class MainConverter {
    @TypeConverter
    fun toJson(obj: Main): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJson(string: String): Main {
        return Gson().fromJson(string, Main::class.java)
    }
}


class WindConverter {
    @TypeConverter
    fun toJson(obj: Wind): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJson(string: String): Wind {
        return Gson().fromJson(string, Wind::class.java)
    }
}


class RainConverter {
    @TypeConverter
    fun toJson(obj: Rain): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJson(string: String): Rain {
        return Gson().fromJson(string, Rain::class.java)
    }
}


class CloudsConverter {
    @TypeConverter
    fun toJson(obj: Clouds): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJson(string: String): Clouds {
        return Gson().fromJson(string, Clouds::class.java)
    }
}


class SysConverter {
    @TypeConverter
    fun toJson(obj: Sys): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJson(string: String): Sys {
        return Gson().fromJson(string, Sys::class.java)
    }
}


class ListElementConverter {
    @TypeConverter
    fun toJson(obj: ListElement): String {
        return Gson().toJson(obj)
    }

    @TypeConverter
    fun fromJson(string: String): ListElement {
        return Gson().fromJson(string, ListElement::class.java)
    }
}

