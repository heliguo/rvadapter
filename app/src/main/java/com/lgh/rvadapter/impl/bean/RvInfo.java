package com.lgh.rvadapter.impl.bean;

import com.lgh.rvadapter.base.ItemType;

import java.util.List;

/**
 * @author lgh on 2020/10/14:15:00
 * @description
 */
public class RvInfo implements ItemType {

    private int type;

    private boolean grouping;

    private List<Bean> infos;

    public RvInfo(int type, boolean grouping, List<Bean> infos) {
        this.type = type;
        this.grouping = grouping;
        this.infos = infos;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean grouping() {
        return grouping;
    }

    public void setGrouping(boolean grouping) {
        this.grouping = grouping;
    }

    public List<Bean> getInfos() {
        return infos;
    }

    public void setInfos(List<Bean> infos) {
        this.infos = infos;
    }

    public static class Bean implements ItemType {

        private String content;

        public Bean(String content) {
            this.content = content;
        }

        @Override
        public int getType() {
            return 0;
        }

        @Override
        public void setType(int type) {

        }

        @Override
        public boolean grouping() {
            return false;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String info) {
            this.content = info;
        }
    }
}
