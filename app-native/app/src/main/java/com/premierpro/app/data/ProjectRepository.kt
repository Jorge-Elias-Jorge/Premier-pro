package com.premierpro.app.data

import android.content.Context
import com.premierpro.app.captions.Caption
import org.json.JSONArray
import org.json.JSONObject

class ProjectRepository(private val context: Context) {

    private val prefs = context.getSharedPreferences("premier_pro", Context.MODE_PRIVATE)

    fun saveCaptions(captions: List<Caption>) {
        val array = JSONArray()
        captions.forEach { caption ->
            val item = JSONObject()
            item.put("id", caption.id)
            item.put("start", caption.startSeconds)
            item.put("end", caption.endSeconds)
            item.put("text", caption.text)
            array.put(item)
        }
        prefs.edit().putString("captions", array.toString()).apply()
    }

    fun loadCaptions(): List<Caption> {
        val raw = prefs.getString("captions", null) ?: return emptyList()
        return try {
            val array = JSONArray(raw)
            (0 until array.length()).map { index ->
                val item = array.getJSONObject(index)
                Caption(
                    id = item.getLong("id"),
                    startSeconds = item.getDouble("start"),
                    endSeconds = item.getDouble("end"),
                    text = item.getString("text")
                )
            }
        } catch (error: Exception) {
            emptyList()
        }
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
