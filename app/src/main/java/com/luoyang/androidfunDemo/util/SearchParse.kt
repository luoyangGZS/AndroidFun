package com.luoyang.androidfunDemo.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luoyang.androidfunDemo.bean.DetailBean
import com.luoyang.androidfunDemo.bean.ItemBean
import com.luoyang.androidfunDemo.bean.SearchSuggestBean
import com.luoyang.androidfunDemo.retrofit.api.Api
import org.json.JSONObject

/**
 *
 *
 * @author luoyang
 * @date 2022/7/24
 */
object SearchParse {

    fun composeSuggestURL(query: String): String? {
        return (Api.BASE_API + Api.SEARCH_SUGGEST_PATH + "?query=" + query)
    }

    fun parseSearchSuggest(suggestString: String?): List<ItemBean>? {
        if (suggestString == null) {
            return null
        }
        val beans: MutableList<ItemBean> = java.util.ArrayList<ItemBean>()
        return try {
            val j = JSONObject(suggestString)
            val result = j.getInt("result")

            if (result == 0) {
                val content = j.getJSONArray("content")
                for (i in 0 until content.length()) {
                    val contentItem = content.getJSONObject(i)
                    if (contentItem.has("package")) {
                        val detailBean = DetailBean()
                        detailBean.setIcon(contentItem.getString("icon"))
                        detailBean.setAppName(contentItem.getString("appName"))
                        detailBean.setSize(contentItem.getString("size"))
                        detailBean.setVersionCode(contentItem.getString("versionCode"))
                        detailBean.setPackageName(contentItem.getString("package"))
                        detailBean.setPackageId(contentItem.getString("packageId"))
                        detailBean.setEditorIntro(contentItem.optString("editorIntro"))
                        detailBean.setDownloadNum(contentItem.getString("downloadNum"))
                        val type = object : TypeToken<List<String?>?>() {}.type
                        val labels =
                            Gson().fromJson<List<String>>(contentItem.optString("labelList"), type)
                        detailBean.setLabels(labels)
                        detailBean.setApkUrl(contentItem.getString("apkUrl"))
                        //添加应用宝参数
                        if (contentItem.has("exParamters")) {
                            detailBean.setExParamters(contentItem.optString("exParamters"))
                        }
                        //添加app大小介绍
                        if (contentItem.has("apkSizeDesc")) {
                            detailBean.setApkSizeDesc(contentItem.optString("apkSizeDesc"))
                        }
                        //下载次数介绍
                        if (contentItem.has("downloadDesc")) {
                            detailBean.setDownloadDesc(contentItem.optString("downloadDesc"))
                        }
                        beans.add(detailBean)
                    } else if (contentItem.has("Sug")) {
                        val suggestBean = SearchSuggestBean()
                        suggestBean.setSug(contentItem.getString("Sug"))
                        suggestBean.setWeight(contentItem.getInt("Weight"))
                        val ext = contentItem.getJSONObject("Ext")
                        suggestBean.setType(ext.getString("type"))
                        suggestBean.setPackageId(ext.getString("packageId"))
                        beans.add(suggestBean)
                    }
                }
                beans
            } else {
                null
            }
        } catch (e: java.lang.Exception) {
            null
        }
    }
}