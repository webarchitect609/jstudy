package ru.webarch.jstudy.github.tests;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests  {
    @Test
    public void testCommits() throws IOException {
        /**
         * Тест больше работать не будет, т.к. GitHub автоматически отозвал этот токен, как потенциальную опасность
         */
        Github github = new RtGithub("8cb8a484317ba8fbc9c87bdbd25962f9750d3540");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("webarchitect609", "jstudy")).commits();

        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}
