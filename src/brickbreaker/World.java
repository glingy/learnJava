package brickbreaker;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

public class World {
    public GameCanvas canvas = new GameCanvas();
    public Map<Class<? extends Entity>, List<Entity>> entities = new LinkedHashMap<>();

    public <T extends Entity> void addEntity(T entity) {
        List<Entity> list = entities.get(entity.getClass());
        if (list != null) {
            list.add(entity);
        } else {
            list = new ArrayList<>();
            list.add(entity);
            entities.put(entity.getClass(), list);
        }
    }

    public <T> T getEntity(Class<T> clazz) {
        return clazz.cast(entities.get(clazz).get(0));
    }

    public <T> List<T> getEntities(Class<T> clazz) {
        //noinspection unchecked
        return (List<T>) entities.get(clazz);
    }

    public World() {
        addEntity(new BlockManager(canvas));
        addEntity(new Paddle(canvas));
        addEntity(new Ball(140, 400, 3));
    }


    public void startLevel() {
        Block[][] blocks = new Block[BlockManager.NUM_ROWS][BlockManager.NUM_COLS];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = new Block(new Color(i * (255 / blocks.length), j * (255 / blocks[i].length), i * (127 / blocks.length) + j * (127 / blocks[i].length)));
            }
        }
        getEntity(BlockManager.class).loadBlocks(blocks);

        new Thread(new Runnable() {
            @Override
            public void run() {
                new Timer(33, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        update();
                    }
                }).start();
            }
        }).start();
    }

    private void update() {
        for (List<Entity> list : entities.values()) {
            for (Entity entity : list) {
                entity.clear(canvas.getGraphics());
                entity.preUpdate(this);
            }
        }

        for (List<Entity> list : entities.values()) {
            for (Entity entity : list) {
                entity.update(this);
            }
        }

        for (List<Entity> list : entities.values()) {
            for (Entity entity : list) {
                entity.postUpdate(this);
                entity.draw(canvas.getGraphics());
            }
        }

        canvas.repaint();
    }
}
