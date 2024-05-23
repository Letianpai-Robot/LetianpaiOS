package com.rhj.audio.observer;

import com.rhj.audio.DmTaskResultBean;

public interface RhjDMTaskCallback {
    String dealResult(DmTaskResultBean dmTaskResultBean);

    void dealErrorResult();
}
