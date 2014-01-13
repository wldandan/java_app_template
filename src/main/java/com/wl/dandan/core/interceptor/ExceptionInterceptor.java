package com.wl.dandan.core.interceptor;

import com.wl.dandan.core.model.SessionModel;
import com.wl.dandan.core.model.TransferModel;

public interface ExceptionInterceptor<T> extends Interceptor{

    boolean exceptionHandler(SessionModel session, T parameters, TransferModel transfer, Exception exception);
}
