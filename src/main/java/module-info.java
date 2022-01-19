module CQMCORS {
    requires javafx.controls;
    requires javafx.fxml;

    opens CQMCORS to javafx.fxml;
    exports CQMCORS;
}
