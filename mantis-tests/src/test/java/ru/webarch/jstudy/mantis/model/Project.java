package ru.webarch.jstudy.mantis.model;

import biz.futureware.mantis.rpc.soap.client.ProjectData;

import java.math.BigInteger;

public class Project {

    private ProjectData projectData;

    public Project() {
        projectData = new ProjectData();
    }

    public Project(ProjectData projectData) {
        this.projectData = projectData;
    }

    public ProjectData data() {
        return projectData;
    }

    public int getId() {
        return projectData.getId().intValue();
    }

    public String getName() {
        return projectData.getName();
    }

    public Project withId(int id) {
        projectData.setId(BigInteger.valueOf(id));
        return this;
    }

    public Project withName(String name) {
        projectData.setName(name);
        return this;
    }
}
