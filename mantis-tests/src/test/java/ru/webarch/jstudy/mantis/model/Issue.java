package ru.webarch.jstudy.mantis.model;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;

import java.math.BigInteger;

public class Issue {

    private IssueData issueData;
    private Project project;

    public Issue() {
        issueData = new IssueData();
    }

    public Issue(IssueData issueData) {
        this.issueData = issueData;
        ObjectRef project = issueData.getProject();
        withProject(
                new Project()
                        .withId(project.getId().intValue())
                        .withName(project.getName())
        );
    }

    public IssueData data() {
        return issueData;
    }

    public Issue withId(int id) {
        issueData.setId(BigInteger.valueOf(id));
        return this;
    }

    public Issue withSummary(String summary) {
        issueData.setSummary(summary);
        return this;
    }

    public Issue withDescription(String description) {
        issueData.setDescription(description);
        return this;
    }

    public Issue withProject(Project project) {
        this.project = project;
        issueData.setProject(
                new ObjectRef(
                        BigInteger.valueOf(project.getId()),
                        project.getName()
                )
        );
        return this;
    }

    public Issue withCategory(String category) {
        issueData.setCategory(category);
        return this;
    }

    public int getId() {
        return issueData.getId().intValue();
    }

    public String getSummary() {
        return issueData.getSummary();
    }

    public String getDescription() {
        return issueData.getDescription();
    }

    public Project getProject() {
        return project;
    }

    public String getCategory() {
        return issueData.getCategory();
    }
}
