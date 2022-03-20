package mimuw.idlearn.GUI.coding.codeblock;

import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitializeJavaFXStartupBase {

    public static void initializeStartup() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            latch.countDown();
        });
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    public static void main(String[] args) throws Exception {

        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });

        TextField tf = new TextField();
        System.out.println("Test");
    }
}