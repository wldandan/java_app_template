package com.wl.dandan.core.interceptor;

import com.wl.dandan.core.model.SessionModel;
import com.wl.dandan.core.model.TransferModel;

public interface AlwaysInterceptor<T> extends Interceptor{

    public boolean alwaysExecute(SessionModel session, T parameters, TransferModel transfer, Exception exception);
}
