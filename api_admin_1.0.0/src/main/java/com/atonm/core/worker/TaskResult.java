package com.atonm.core.worker;

public class TaskResult<S, R> {
    private S taskId;
    private R result;
    public TaskResult(S taskId, R result) {
        this.taskId = taskId;
        this.result = result;
    }
    public S getTaskId() {
        return taskId;
    }
    public R getResult() {
        return result;
    }
    /*@Override
    public String toString() {
        return "[taskId=" + taskId + ", result=" + result + "]";
    }*/
}
