仅仅 RhjAudioManager 对外可见
1. 首先要调用 init
2. 可选设定
    唤醒状态回调  RhjAudioManager.getInstance().setWakeupStateChangeCallback
    TTS状态回调   RhjAudioManager.getInstance().setTtsStateChangeCallback
    消息结果回调  RhjAudioManager.getInstance().setMessageCallback
    命令结果回调  RhjAudioManager.getInstance().setCommandCallback  需要同步添加指定的 Const 处理

    如果所有获取完整的所有的结果 RhjAudioManager.getInstance().setRhjDMTaskCallback


还需要注册不同的 message 和 command 的 key

最终调用的时候，需要绑定 applicationId 进行鉴权
如需配置鉴权，请在 https://cloud.aispeech.com/product/authorization/index.html#/product/sdk?productId=279614681&channel=DUI  配置