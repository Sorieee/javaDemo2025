/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.sorie.jraft.counter;

import com.alipay.sofa.jraft.Closure;
import top.sorie.jraft.counter.rpc.CounterOutter;

/**
 * @author likun (saimu.msm@antfin.com)
 */
public abstract class CounterClosure implements Closure {

    private CounterOutter.ValueResponse valueResponse;
    private CounterOperation counterOperation;

    public void setCounterOperation(CounterOperation counterOperation) {
        this.counterOperation = counterOperation;
    }

    public CounterOperation getCounterOperation() {
        return counterOperation;
    }

    public CounterOutter.ValueResponse getValueResponse() {
        return valueResponse;
    }

    public void setValueResponse(CounterOutter.ValueResponse valueResponse) {
        this.valueResponse = valueResponse;
    }

    protected void failure(final String errorMsg, final String redirect) {
        final CounterOutter.ValueResponse response = CounterOutter.ValueResponse.newBuilder().setSuccess(false).setErrorMsg(errorMsg)
                .setRedirect(redirect).build();
        setValueResponse(response);
    }

    protected void success(final long value) {
        final CounterOutter.ValueResponse response = CounterOutter.ValueResponse.newBuilder().setValue(value).setSuccess(true).build();
        setValueResponse(response);
    }
}
