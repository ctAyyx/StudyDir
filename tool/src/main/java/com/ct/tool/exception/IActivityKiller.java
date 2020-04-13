package com.ct.tool.exception;

import android.os.Message;

/**
 * Created by Administrator on 2019/8/26.
 */

public interface IActivityKiller {

    void finishLaunchActivity(Message message);

    void finishResumeActivity(Message message);

    void finishPauseActivity(Message message);

    void finishStopActivity(Message message);

}
