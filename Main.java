public class Main {
    public static void main(String[] args) {

        SceneLinkedList scenes = GameLoader.loadScenes("scenes.csv");

        Scene startScene = scenes.findSceneById(1);

        new GameGUI(scenes, startScene);
    }
}