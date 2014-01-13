package com.wl.dandan.core.handler;

import com.wl.dandan.core.model.SessionModel;
import com.wl.dandan.core.model.TransferModel;

public interface AppHandler<T> {

    TransferModel execute(SessionModel session, T parameters);

}
