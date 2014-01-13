package com.wl.dandan.core.handler;

import com.wl.dandan.core.model.SessionModel;
import com.wl.dandan.core.model.TransferModel;
import com.wl.dandan.core.interceptor.*;

import java.util.List;

public abstract class AbstractAppHandler<T> implements AppHandler<T> {

    private List<PreInterceptor> preInterceptors;
    private List<PostInterceptor> postInterceptors;
    private List<AlwaysInterceptor> alwaysInterceptors;
    private List<ExceptionInterceptor> exceptionInterceptors;

    @Override
    public final TransferModel execute(SessionModel session, T parameters) {

        TransferModel transferModel = new TransferModel();
        Exception exception = null;

        try {
            boolean canBeExecuted = invokePreInterceptors(session, parameters, transferModel);

            if (canBeExecuted) {
                Object results = doExecute(session, parameters, transferModel);

                invokePostInterceptors(session, parameters, transferModel, results);
            }
        } catch (Exception e) {
            exception = e;
            handleException(session, e, transferModel, parameters);
        } finally {
            invokeAlwaysInterceptors(session, parameters, transferModel, exception);
        }

        return transferModel;
    }

    protected abstract Object doExecute(SessionModel session, T parameters, TransferModel transferModel) throws Exception;

    public void invokePostInterceptors(SessionModel session, T parameters, TransferModel transferModel, Object results) {
        if (getPostInterceptors() != null) {
            for (PostInterceptor interceptor : getPostInterceptors()) {
                if (!interceptor.postExecute(session, parameters, transferModel, results)) {
                    break;
                }
            }
        }
    }

    public void invokeAlwaysInterceptors(SessionModel session, T parameters, TransferModel transferModel, Exception exception) {
        if (getAlwaysInterceptors() != null) {
            for (AlwaysInterceptor interceptor : getAlwaysInterceptors()) {
                interceptor.alwaysExecute(session, parameters, transferModel, exception);
            }
        }
    }

    public boolean invokePreInterceptors(SessionModel session, T parameters, TransferModel transferModel) throws Exception {
        if (getPreInterceptors() != null) {
            for (PreInterceptor interceptor : getPreInterceptors()) {
                if (!interceptor.preExecute(session, parameters, transferModel)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void handleException(SessionModel session, Exception ex, TransferModel transferModel, T parameters){
        if (!invokeExceptionInterceptors(session, parameters, transferModel, ex)) {
            if (ex instanceof RuntimeException) {
                throw ((RuntimeException) ex);
            } else {
                try {
                    throw new Exception(ex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean invokeExceptionInterceptors(SessionModel session, T parameters, TransferModel container, Exception ex) {
        if (getExceptionInterceptors() != null) {
            for (ExceptionInterceptor interceptor : getExceptionInterceptors()) {
                if (interceptor.exceptionHandler(session, parameters, container, ex)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<AlwaysInterceptor> getAlwaysInterceptors() {
        return alwaysInterceptors;
    }

    public void setAlwaysInterceptors(List<AlwaysInterceptor> alwaysInterceptors) {
        alwaysInterceptors = alwaysInterceptors;
    }

    public List<PreInterceptor> getPreInterceptors() {
        return preInterceptors;
    }

    public void setPreInterceptors(List<PreInterceptor> preInterceptors) {
        preInterceptors = preInterceptors;
    }

    public List<PostInterceptor> getPostInterceptors() {
        return postInterceptors;
    }

    public void setPostInterceptors(List<PostInterceptor> postInterceptors) {
        postInterceptors = postInterceptors;
    }

    public List<ExceptionInterceptor> getExceptionInterceptors() {
        return exceptionInterceptors;
    }

    public void setExceptionInterceptors(List<ExceptionInterceptor> exceptionInterceptors) {
        exceptionInterceptors = exceptionInterceptors;
    }

}
