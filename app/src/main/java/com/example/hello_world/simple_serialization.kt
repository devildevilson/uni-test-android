package com.example.hello_world

object simple_serialization {
  // нужно ли делать дженерик?
  fun save(map: Map<String, String>): String {
    var data = ""
    for ((key, value) in map) {
      if (key.contains("=")) throw IllegalArgumentException("keys cannot contain symbol '='")
      val line = "$key=$value\n"
      data += line
    }

    return data
  }

  fun load(data: String): Map<String, String> {
    val m = emptyMap<String, String>().toMutableMap()
    val lines = data.split("\n")
    for (line in lines) {
      val var_name = line.substringBefore("=")
      val var_data = line.substringAfter("=")
      m[var_name] = var_data
    }

    return m
  }
}