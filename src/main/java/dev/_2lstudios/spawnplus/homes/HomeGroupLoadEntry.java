package dev._2lstudios.spawnplus.homes;

public class HomeGroupLoadEntry {
    private HomeGroup homeGroup;
    private Runnable afterLoad;

    public HomeGroupLoadEntry(HomeGroup homeGroup, Runnable afterLoad) {
        this.homeGroup = homeGroup;
        this.afterLoad = afterLoad;
    }

    public HomeGroupLoadEntry(HomeGroup homeGroup) {
        this(homeGroup, null);
    }

    public HomeGroup getHomeGroup() {
        return homeGroup;
    }

    public void runAfterLoad() {
        if (afterLoad != null) {
            afterLoad.run();
        }
    }
}
