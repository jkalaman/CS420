package sample;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URI;

public class App extends Application {
    Stage window;
    private Popup popup ;
    private Node popupContent ;
    private Label titleLabel ;
    private Label detailsLabel ;
    private FadeTransition fadeOut ;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("RSS Feed");
        RSSFeedParser parser = new RSSFeedParser();
        Feed feed = parser.readFeed("https://www.theguardian.com/world/rss");

        ListView<Item> listView = new ListView<>();


        popup = new Popup();
        titleLabel = new Label();
        titleLabel.setStyle("-fx-font-size: 1.5em ; -fx-font-weight: bold;");
        detailsLabel = new Label();
        popupContent = new VBox(10, titleLabel, detailsLabel);
        popupContent.setStyle("-fx-background-color: -fx-background; "+
                "-fx-background: lightskyblue; -fx-padding:12px;");
        popup.getContent().add(popupContent);

        fadeOut = new FadeTransition(Duration.millis(2000), popupContent);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> popup.hide());

        listView.setCellFactory(lv -> {
            ListCell<Item> cell = new ListCell<Item>() {
                @Override
                public void updateItem(Item item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            };
            cell.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
                if (isNowHovered && ! cell.isEmpty()) {
                    showPopup(cell);
                } else {
                    hidePopup();
                }
            });

            return cell ;
        });

        for (FeedMessage message : feed .getMessages()) {

            listView.getItems()
                    .add(new Item(message.getTitle(), message.getId(), message.getDescription(), message.getLink()));
        }


        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setOnMouseClicked(click -> {
            if(click.getClickCount()==2) {
                ObservableList<Item> currentItemSelected = listView.getSelectionModel().getSelectedItems();
                for(Item tmp : currentItemSelected)
                    openLink(tmp.getLink());

            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20,20));

        layout.getChildren().addAll(listView);

        window.setOnCloseRequest(e-> {
            e.consume();
            closeProgram();
        });

        Scene scene1 = new Scene(layout, 600, 300);
        window.setScene(scene1);
        window.show();
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Title", "You sure you want to exit?");
        if(answer)
            window.close();
    }

    private void openLink(String str) {
        try {
            URI theURI = new URI(str);
            java.awt.Desktop.getDesktop().browse(theURI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPopup(ListCell<Item> cell) {
        fadeOut.stop();
        popupContent.setOpacity(1.0);
        Bounds bounds = cell.localToScreen(cell.getBoundsInLocal());

        popup.show(cell, bounds.getMaxX()/2, bounds.getMinY()/2);
        Item item = cell.getItem() ;
        titleLabel.setText(item.getName());
        detailsLabel.setText(String.format("%s.%n",
                item.getDescription()));
    }

    private void hidePopup() {
        fadeOut.playFromStart();
    }

    public static class Item {
        private final int value ;
        private final String name ;
        private final String description;
        private final String link;

        public Item(String name, int value, String description, String link) {
            this.name = name ;
            this.value = value ;
            this.description = description;
            this.link = link;
        }

        public int getValue() {
            return value ;
        }
        public String getLink() {
            return link ;
        }

        /* do some clean up missed on parsing*/
        public String getDescription() {
            String desc = this.description;
            desc = desc.replace("</p><p>", "\n");
            if (desc.contains("<a")) {
                int posOfOpen = desc.indexOf("<a");
                String descTemp = desc.substring(posOfOpen);
                desc = desc.replace(descTemp,"");
            }
            desc = desc.replace("<p>", "")
                    .replace("</p>","");

            return desc;
        }
        public String getName() {
            return name ;
        }
    }
}
