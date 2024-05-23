package com.demon.fmodsound

import java.lang.Exception

/**
 * @author DeMon
 * Created on 2020/12/31.
 * E-mail 757454343@qq.com
 * Desc:
 */

object FmodSound {
    //音效的类型

    init {
        System.loadLibrary("fmodL")
        System.loadLibrary("fmod")
        System.loadLibrary("FmodSound")
    }

    external fun playTts(url: String)

    /**
     * 变声保存
     * @param path 音频路径，只支持WAV格式
     * @param type 变声音效类型，默认=0即普通播放无变声效果
     * @param savePath 变声后保存的路径，输出为WAV格式
     * @param listener 变声结果监听，根据回调可以在变声成功后播放
     */
//    fun saveSoundAsync(path: String, type: Int, savePath: String, listener: ISaveSoundListener? = null) {
//        try {
//
//            val result = saveSound(path, type, savePath)
//            if (result == 0) {
//                listener?.onFinish(path, savePath, type)
//            } else {
//                listener?.onError("error")
//            }
//        } catch (e: Exception) {
//            listener?.onError(e.message)
//        }
//    }

    interface ISaveSoundListener {
        //成功
        fun onFinish(path: String, savePath: String, type: Int)

        //出错
        fun onError(msg: String?)
    }

}