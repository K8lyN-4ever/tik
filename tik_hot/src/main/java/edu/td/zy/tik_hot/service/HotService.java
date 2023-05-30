package edu.td.zy.tik_hot.service;

/**
 * @author K8lyN
 * @date 2023年4月10日 15:26:52
 * @version 1.0
 * */
public interface HotService {

    /**
     * 生成热榜
     * */
    void generateHot();

    /**
     * 多线程生成热榜
     * */
    void generateHotWithParallelProcessing();
}
