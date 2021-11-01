package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of tags.
 */
public class TagListPanel extends UiPart<Region> {
    private static final String FXML = "TagListPanel.fxml";

    private final ObservableMap<Tag, Integer> tagCounter;

    @FXML
    private ListView<Tag> tagListView;

    /**
     * Constructs a TagListPanel with the specified observable list of tags.
     *
     * @param tagList List of tags to be displayed.
     */
    public TagListPanel(ObservableList<Tag> tagList, ObservableMap<Tag, Integer> tagCounter) {
        super(FXML);
        this.tagCounter = tagCounter;
        tagListView.setItems(tagList);
        tagListView.setCellFactory(listView -> new TagListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tag} using a {@code TagCard}.
     */
    class TagListViewCell extends ListCell<Tag> {
        @Override
        protected void updateItem(Tag tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TagCard(tag, tagCounter.get(tag)).getRoot());
            }
        }
    }

}
