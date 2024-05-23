#include <jni.h>
#include "inc/fmod.hpp"
#include <string>
#include <android/log.h>
#include <unistd.h>

using namespace FMOD;

#define LOGI(FORMAT, ...) __android_log_print(ANDROID_LOG_INFO,"FmodSound",FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"FmodSound",FORMAT,##__VA_ARGS__);

#define MODE_NORMAL 0
#define MODE_FUNNY 1
#define MODE_UNCLE 2
#define MODE_LOLITA 3
#define MODE_ROBOT 4
#define MODE_ROBOT_2 5
#define MODE_ROBOT_3 6

#define MODE_ETHEREAL 7
#define MODE_CHORUS 8
#define MODE_HORROR 9

Channel *channel;
/*
extern "C"
JNIEXPORT jint JNICALL
Java_com_demon_fmodsound_FmodSound_saveSound(JNIEnv *env, jobject cls, jstring path_jstr, jint type, jstring save_jstr) {
    Sound *sound;
    DSP *dsp;
    bool playing = true;
    float frequency = 0;
    System *mSystem;
    JNIEnv *mEnv = env;
    int code = 0;
    System_Create(&mSystem);
    const char *path_cstr = mEnv->GetStringUTFChars(path_jstr, NULL);
    LOGI("saveAiSound-%s", path_cstr)
    const char *save_cstr;
    if (save_jstr != NULL) {
        save_cstr = mEnv->GetStringUTFChars(save_jstr, NULL);
        LOGI("saveAiSound-save_path=%s", save_cstr)
    }
    try {
        if (save_jstr != NULL) {
            char cDest[200];
            strcpy(cDest, save_cstr);
            mSystem->setSoftwareFormat(8000, FMOD_SPEAKERMODE_MONO, 0); //设置采样率为8000，channel为1
            mSystem->setOutput(FMOD_OUTPUTTYPE_WAVWRITER); //保存文件格式为WAV
            mSystem->init(32, FMOD_INIT_NORMAL, cDest);
            mSystem->recordStart(0, sound, true);
        }
        //创建声音
        mSystem->createSound(path_cstr, FMOD_DEFAULT, NULL, &sound);
        mSystem->playSound(sound, 0, false, &channel);
        LOGI("saveAiSound-%s", "save_start")
        switch (type) {
            case MODE_NORMAL:
                LOGI("saveAiSound-%s", "save MODE_NORMAL")
                break;
            case MODE_FUNNY:
                LOGI("saveAiSound-%s", "save MODE_FUNNY")
                mSystem->createDSPByType(FMOD_DSP_TYPE_NORMALIZE, &dsp);
                channel->getFrequency(&frequency);
                frequency = frequency * 1.6;
                channel->setFrequency(frequency);
                break;
            case MODE_UNCLE:
                LOGI("saveAiSound-%s", "save MODE_UNCLE")
                mSystem->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.8);
                channel->addDSP(0, dsp);
                break;
            case MODE_LOLITA:
                LOGI("saveAiSound-%s", "save MODE_LOLITA")
                mSystem->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 1.8);
                channel->addDSP(0, dsp);
                break;
            case MODE_ROBOT:
                LOGI("saveAiSound-%s", "save MODE_ROBOT")
                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 50);
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 60);
                channel->addDSP(0, dsp);
                break;
            case MODE_ETHEREAL:
                LOGI("saveAiSound-%s", "save MODE_ETHEREAL")
                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 300);
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 20);
                channel->addDSP(0, dsp);
                break;
            case MODE_CHORUS:
                LOGI("saveAiSound-%s", "save MODE_CHORUS")
                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 100);
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 50);
                channel->addDSP(0, dsp);
                break;
            case MODE_HORROR:
                LOGI("saveAiSound-%s", "save MODE_HORROR")
                mSystem->createDSPByType(FMOD_DSP_TYPE_TREMOLO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW, 0.8);
                channel->addDSP(0, dsp);
                break;
            default:
                break;
        }
        mSystem->update();
    } catch (...) {
        LOGE("saveAiSound-%s", "save error!")
        code = 1;
        goto end;
    }
    while (playing) {
        usleep(1000);
        channel->isPlaying(&playing);
    }
    LOGI("saveAiSound-%s", "save over!")
    goto end;
    end:
    if (path_jstr != NULL) {
        mEnv->ReleaseStringUTFChars(path_jstr, path_cstr);
    }
    if (save_jstr != NULL) {
        mEnv->ReleaseStringUTFChars(save_jstr, save_cstr);
    }
    sound->release();
    mSystem->close();
    mSystem->release();
    return code;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_demon_fmodsound_FmodSound_playSound(JNIEnv *env, jobject cls, jstring path_jstr, jint type) {
    Sound *sound;
    DSP *dsp;

    bool playing = true;
    float frequency = 0;
    System *mSystem;
    JNIEnv *mEnv = env;
    int code = 0;
    System_Create(&mSystem);
    const char *path_cstr = mEnv->GetStringUTFChars(path_jstr, NULL);
    LOGI("playAiSound-%s", path_cstr)
    try {
        mSystem->init(32, FMOD_INIT_NORMAL, NULL);
        //创建声音
//        mSystem->createSound(path_cstr, FMOD_DEFAULT, NULL, &sound);
        mSystem->createSound(path_cstr, FMOD_DEFAULT, NULL, &sound);
        mSystem->playSound(sound, 0, false, &channel);
        LOGI("playAiSound-%s", "play_start")
        switch (type) {
            case MODE_NORMAL:
                LOGI("playAiSound-%s", "play MODE_NORMAL")
                break;
            case MODE_FUNNY:
                LOGI("playAiSound-%s", "play MODE_FUNNY")
                mSystem->createDSPByType(FMOD_DSP_TYPE_NORMALIZE, &dsp);
                channel->getFrequency(&frequency);
                frequency = frequency * 1.6;
                channel->setFrequency(frequency);
                break;
            case MODE_UNCLE:
                LOGI("playAiSound-%s", "play MODE_UNCLE")
                mSystem->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 0.8);
                channel->addDSP(0, dsp);
                break;
            case MODE_LOLITA:
                LOGI("playAiSound-%s", "play MODE_LOLITA")
                mSystem->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 1.7);
                channel->addDSP(0, dsp);
                break;
            case MODE_ROBOT:
                LOGI("playAiSound-%s", "play MODE_ROBOT")
                /// *本机在声音上产生回音，并以所需的速率淡出。 * /
                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                /// *（类型：float）-回声延迟，以ms为单位。10至5000。默认= 500。* /
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 40);
                /// *（类型：float）-每个延迟的回声衰减。0至100。100.0=无衰减，0.0=总衰减（即简单的1线延迟）。默认值=50.0* /
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 60);

                channel->addDSP(0, dsp);
                break;
//            case MODE_ROBOT:
//                LOGI("playAiSound-%s", "play MODE_ROBOT")
//                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
//                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 50);
//                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 60);
//                channel->addDSP(0, dsp);
//                break;

            case MODE_ROBOT_2:
                LOGI("playAiSound-%s", "play MODE_LOLITA")
                DSP *dsp2;
                mSystem->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 1.7);

                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp2);
                dsp2->setParameterFloat(FMOD_DSP_ECHO_DELAY, 30);
                dsp2->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 50);
                channel->addDSP(0, dsp);
                channel->addDSP(0, dsp2);

                channel->getFrequency(&frequency);
                frequency = frequency * 0.9;
                channel->setFrequency(frequency);
                break;
            case MODE_ROBOT_3:
                DSP *dsp3;
                DSP *lowPass;
                DSP *wave;
                DSP *delay;
                DSP *tremolo;
                mSystem->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 2.0);

                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp3);
                dsp3->setParameterFloat(FMOD_DSP_ECHO_DELAY, 10);
                dsp3->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 25);

                mSystem->createDSPByType(FMOD_DSP_TYPE_SFXREVERB, &wave);
                mSystem->createDSPByType(FMOD_DSP_TYPE_LOWPASS, &lowPass);
                lowPass->setParameterFloat(FMOD_DSP_LOWPASS_CUTOFF, 2000.0f);  // 设置低通滤波器的截止频率为500 Hz

//                mSystem->createDSPByType(FMOD_DSP_TYPE_DELAY, &delay);
//                delay->setParameterFloat(FMOD_DSP_DELAY_CH0, 500.0f);  // 设置左声道延迟500毫秒
//                delay->setParameterFloat(FMOD_DSP_DELAY_CH1, 750.0f);  // 设置右声道延迟750毫秒

                mSystem->createDSPByType(FMOD_DSP_TYPE_TREMOLO, &tremolo);
                tremolo->setParameterFloat(FMOD_DSP_TREMOLO_FREQUENCY, 20.0f);  // 设置颤音频率为10 Hz
                tremolo->setParameterFloat(FMOD_DSP_TREMOLO_DEPTH, 0.1f);        // 设置颤音深度为0.5

//                channel->addDSP(0, tremolo);
//                channel->addDSP(0, wave);
//                channel->addDSP(0, delay);
                channel->addDSP(0, lowPass);

//                mSystem->createDSPByType(FMOD_DSP_TYPE_DISTORTION, &distortion);
//                distortion->setParameterFloat(FMOD_DSP_DISTORTION_LEVEL, 0.5f);
//                channel->addDSP(0, distortion);

                channel->addDSP(0, dsp);
                channel->addDSP(0, dsp3);

                channel->getFrequency(&frequency);
                frequency = frequency * 0.85;
                channel->setFrequency(frequency);
                break;
            case MODE_ETHEREAL:
                LOGI("playAiSound-%s", "play MODE_ETHEREAL")
                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 300);
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 20);
                channel->addDSP(0, dsp);
                break;
            case MODE_CHORUS:
                LOGI("playAiSound-%s", "play MODE_CHORUS")
                mSystem->createDSPByType(FMOD_DSP_TYPE_ECHO, &dsp);
                dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY, 100);
                dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK, 50);
                channel->addDSP(0, dsp);
                break;
            case MODE_HORROR:
                LOGI("playAiSound-%s", "play MODE_HORROR")
                DSP *dsp_1;
                mSystem->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT, &dsp);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH, 1.63);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_OVERLAP, 32);
                dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_FFTSIZE, 256);

                mSystem->createDSPByType(FMOD_DSP_TYPE_FADER, &dsp_1);
                dsp_1->setParameterFloat(FMOD_DSP_FADER_GAIN, 10);

                channel->addDSP(0, dsp);
                channel->addDSP(0, dsp_1);
                break;
//            case MODE_HORROR:
//                LOGI("playAiSound-%s", "play MODE_HORROR")
//                mSystem->createDSPByType(FMOD_DSP_TYPE_TREMOLO, &dsp);
//                dsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW, 0.8);
//                channel->addDSP(0, dsp);
//                break;
            default:
                break;
        }
        mSystem->update();
    } catch (...) {
        LOGE("playAiSound-%s", "play error!")
        code = 1;
        goto end;
    }
    while (playing) {
        usleep(1000);
        channel->isPlaying(&playing);
    }
    LOGI("playAiSound-%s", "play over！")
    goto end;
    end:
    if (path_jstr != NULL) {
        mEnv->ReleaseStringUTFChars(path_jstr, path_cstr);
    }
    sound->release();
    mSystem->close();
    mSystem->release();
    return code;
}


extern "C" JNIEXPORT void JNICALL
Java_com_demon_fmodsound_FmodSound_stopPlay(JNIEnv *env, jobject jcls) {
    LOGI("%s", "stopPlay")
    channel->stop();
}

extern "C" JNIEXPORT void JNICALL
Java_com_demon_fmodsound_FmodSound_resumePlay(JNIEnv *env, jobject jcls) {
    LOGI("%s", "resumePlay")
    channel->setPaused(false);

}

extern "C" JNIEXPORT void JNICALL
Java_com_demon_fmodsound_FmodSound_pausePlay(JNIEnv *env, jobject jcls) {
    LOGI("%s", "pausePlay")
    channel->setPaused(true);
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_demon_fmodsound_FmodSound_isPlaying(JNIEnv *env, jobject jcls) {
    LOGI("%s", "isPlaying")
    bool isPlaying = true;
    return !channel->isPlaying(&isPlaying);

}
*/

