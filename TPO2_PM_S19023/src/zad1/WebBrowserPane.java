package zad1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class WebBrowserPane extends JFXPanel
{
    WebEngine engine;

    WebBrowserPane(String url)
    {
        super();
        Platform.setImplicitExit(true);
        Platform.runLater(() -> {
            WebView webView = new WebView();
            engine = webView.getEngine();
            Platform.runLater(() -> {
                engine.load(url);
            });
            this.setScene(new Scene(webView));
        });
    }
}
