package controller;

public class FoundationController {
    public static void handleClick(KlondikeController controller) {
        if (controller.getSelection().isEmpty()) return;
        controller.moveToFoundation();
    }
}
