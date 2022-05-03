import mimuw.idlearn.TestRunner;
import mimuw.idlearn.WrongAnswerException;
import mimuw.idlearn.idlang.base.*;
import mimuw.idlearn.idlang.exceptions.SimulationException;
import mimuw.idlearn.idlang.keywords.Assignment;
import mimuw.idlearn.idlang.keywords.Block;
import mimuw.idlearn.idlang.operators.TwoArgOperator;
import mimuw.idlearn.problem_package_system.PackageManager;
import mimuw.idlearn.problem_package_system.ProblemPackage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRunnerTest {
    @Test
    public void testTestRunner() throws SimulationException {
        PackageManager.reloadProblemPackages();
        ProblemPackage pack = PackageManager.getProblemPackages()[0];
        Assertions.assertEquals(pack.getTitle(), "Addition");

        Expression<Void> solution = new Block(
                new InputHandler(pack, new Variable<>("x"), new Variable<>("y")),
                new Assignment<>("x", TwoArgOperator.newAdd(new Variable<>("x"), new Variable<>("y")), false),
                new OutputHandler(pack, new Variable<>("x"))
        );

        TestRunner testRunner = new TestRunner(pack, solution);

        double result = 0;
        try {
            result = testRunner.aggregateTestTimes();
        } catch (WrongAnswerException e) {
            Assertions.fail();
        }
        System.out.println(pack.getTitle() + " ran with score: " + result);

        Assertions.assertEquals(4, result, 0.1);

        Expression<Void> badSolution = new Block(
                new OutputHandler(pack, new Value<>(0))
        );

        testRunner = new TestRunner(pack, badSolution);

        Assertions.assertThrows(WrongAnswerException.class, testRunner::aggregateTestTimes);
    }
}