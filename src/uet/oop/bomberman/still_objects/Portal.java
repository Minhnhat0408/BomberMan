package uet.oop.bomberman.still_objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class Portal extends StillObject {

    private char symbol;
    public Portal(int x, int y, Image img) {
        super(x, y, img);
        symbol = 'X';
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        BombermanGame.map.setTILE_MAP(yblock,xblock,symbol);
    }
}