package ru.webarch.jstudy.rest.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.webarch.jstudy.rest.enums.IssueState;
import ru.webarch.jstudy.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class FakeForeignSystemTests extends TestBase {
    /**
     * Закрытый/исправленный багрепорт
     */
    private Issue fixedIssue;

    /**
     * Открытый/неисправленный багрепорт
     */
    private Issue notfixedIssue;

    @BeforeClass
    public void preconditions() throws IOException {
        ifNoDifferentIssuesThenCreate();
        app.log().info("Ожидаются результаты: ");
        app.log().info("testFixedFeature должен успешно завершиться");
        app.log().info("testNotFixedFeature должен быть пропущен");
    }

    @Test(alwaysRun = true)
    public void testNotFixedFeature() {
        skipIfNotFixed(notfixedIssue.getId());
        app.log().error("Тест testNotFixedFeature должен быть пропущен, а он исполнился!!!");
    }

    @Test(alwaysRun = true)
    public void testFixedFeature() {
        skipIfNotFixed(fixedIssue.getId());
        app.log().info("Тест testFixedFeature отработал, как и ожидалось.");
    }

    private void ifNoDifferentIssuesThenCreate() throws IOException {

        findDifferentIssues();

        if (fixedIssue == null) {
            fixedIssue = app.rest().createIssue(
                    new Issue()
                            .withState(IssueState.IN_PROGRESS)
                            .withSubject("Fixed issue sample")
                            .withDescription("Sample of closed(fixed) bug-report")
            );
        }

        if (notfixedIssue == null) {
            notfixedIssue = app.rest().createIssue(
                    new Issue()
                            .withState(IssueState.OPEN)
                            .withSubject("Notfixed issue sample")
                            .withDescription("Sample of opened(notfixed) bug-report")
            );
        }

    }

    private void findDifferentIssues() throws IOException {
        Set<Issue> issueList = app.rest().getIssues();
        for (Issue issue : issueList) {
            //Если уже нашлись разнообразные баг-репорты, то останавливаем поиск
            if (fixedIssue != null && notfixedIssue != null) {
                break;
            }

            if (isIssueOpen(issue) && notfixedIssue == null) {
                notfixedIssue = issue;
            } else if (!isIssueOpen(issue) && fixedIssue == null) {
                fixedIssue = issue;
            }
        }
    }

}
