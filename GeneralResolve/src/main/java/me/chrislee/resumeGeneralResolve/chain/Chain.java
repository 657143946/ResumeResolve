package me.chrislee.resumeGeneralResolve.chain;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ChrisLee.
 * 流水线
 */
public class Chain<T> {
    private List<T> chain = new LinkedList<>();

    /**
     * 在流水线最后添加工序
     *
     * @param process
     */
    public void add(T process) {
        this.chain.add(process);
    }

    /**
     * 流水线中指定位置插入工序, 如果越界了，则在流水线末尾添加工序
     *
     * @param index
     * @param process
     */
    public void insert(int index, T process) {
        if (index > this.chain.size() || index < 0) {
            this.chain.add(process);
        } else {
            this.chain.add(index, process);
        }
    }

    /**
     * 流水线中删除指定的工序, 如果没有这个工序则不做任何操作
     *
     * @param index
     */
    public void delete(int index) {
        if (0 <= index && index < this.chain.size()) {
            this.chain.remove(index);
        }
    }

    /**
     * 流水线中删除指定的工序, 如果没有这个工序则不做任何操作
     *
     * @param process
     */
    public void delete(T process) {
        this.chain.remove(process);
    }

    /**
     * 流水线中设置某工序的工作，如果不存在该工序，则在工程最后新增该工序
     *
     * @param index
     * @param process
     */
    public void setOrAdd(int index, T process) {
        if (0 <= index && index < this.chain.size()) {
            this.chain.set(index, process);
        } else {
            this.chain.add(process);
        }
    }
}
