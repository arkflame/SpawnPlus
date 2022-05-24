package dev._2lstudios.spawnplus.homes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class HomesSaveTask implements Runnable {
    private Collection<HomeGroup> saveQueue = new HashSet<>();

    @Override
    public void run() {
        Iterator<HomeGroup> iterator = saveQueue.iterator();

        while (iterator.hasNext()) {
            HomeGroup homeGroup = iterator.next();

            homeGroup.save();
            iterator.remove();
        }
    }

    public void add(HomeGroup homeGroup) {
        saveQueue.add(homeGroup);
    }
}
