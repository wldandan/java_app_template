package com.wl.dandan.core.interceptor;

import com.wl.dandan.core.model.SessionModel;
import com.wl.dandan.core.model.TransferModel;

public interface PreInterceptor<T> extends Interceptor{

        public boolean preExecute(SessionModel session, T parameters, TransferModel transfer);

}
