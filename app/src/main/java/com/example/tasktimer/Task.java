package com.example.tasktimer;

import java.io.Serializable;

class Task implements Serializable {
    public static final long serialVersionUID = 20200515L;

    private long m_ID;
    private final String name;
    private final String mDescription;
    private final int mSortOrder;

    public Task(long id, String name, String description, int sortOrder) {
        this.m_ID = id;
        this.name = name;
        mDescription = description;
        mSortOrder = sortOrder;
    }

    public long getID() {
        return m_ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getSortOrder() {
        return mSortOrder;
    }

    public void setID(long id) {
        this.m_ID = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "m_ID=" + m_ID +
                ", name='" + name + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSortOrder=" + mSortOrder +
                '}';
    }
}
