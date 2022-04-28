package mimuw.idlearn.scenes.pkg;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import mimuw.idlearn.core.Emitter;
import mimuw.idlearn.core.Event;
import mimuw.idlearn.core.Listener;
import mimuw.idlearn.scenes.preloader.LoadTask;
import mimuw.idlearn.scenes.preloader.PreloaderEvent;

import java.io.IOException;

public class Preloader extends Scene implements Listener {
    private final AnchorPane parent;
    private final SceneManager sceneManager;
    private final ProgressBar bar;
    private final Emitter emitter;

    public Preloader(SceneManager sceneManager, LoadTask task) throws IOException {
        super(FXMLLoader.load(Preloader.class.getResource("Preloader.fxml")));
        this.parent = (AnchorPane) getRoot();

        this.sceneManager = sceneManager;

        this.bar = new ProgressBar();
        parent.getChildren().add(bar);

        emitter = new Emitter();
        task.setEmitter(emitter);
        emitter.connect(this);

        Thread loader = new Thread(task::load);
        loader.setDaemon(true);
        loader.start();
    }

    public void update(Duration time) {
        synchronized (emitter) {
            emitter.processEvents();
        }
    }

    //TODO?
    @Override
    public void onNotify(Event event) {
        if (event.type() == PreloaderEvent.class) {
            PreloaderEvent preloaderEvent = (PreloaderEvent) event.value();
            switch (preloaderEvent.type()) {
                case Success: {
                    bar.setProgress(1);
                    sceneManager.popScene();
                }
                case Progress: {
                    bar.setProgress(preloaderEvent.progress());
                }
                case Failure: {
                    //TODO
                }
            }
        }
    }
}
