package com.wl.dandan.core.interceptor;

import com.wl.dandan.core.model.SessionModel;
import com.wl.dandan.core.model.TransferModel;

public interface PostInterceptor<T,R> extends Interceptor {

    public boolean postExecute(SessionModel session, T parameters, TransferModel transferModel, R results);
}
