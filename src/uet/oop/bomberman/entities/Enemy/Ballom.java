package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.utils.Collision;
import uet.oop.bomberman.utils.ConstVar;
import uet.oop.bomberman.graphics.Sprite;

public class Ballom extends Enemy {

    private int time = 0;

    private boolean isStuck = false;

    private int timeMoveTile = 8;
    public Ballom(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        movevalX = 0;
        movevalY = 0;
        status = WALK_TYPE.RIGHT;
        speed = ConstVar.BALLOM_SPEED;
        w = 47;
        h = 47;
        life = 1;
        preBlock =' ';
    }

    @Override
    public void moveMent() {
        if(alive) {

            double randomDirection = Math.random();
            if (this.x % ConstVar.TILE_SIZE <= 1 && this.y % ConstVar.TILE_SIZE <= 1) {
                BombermanGame.map.setTILE_MAP(yblock, xblock, preBlock);
                xblock = this.getXblock();
                yblock = this.getYblock();
                if(BombermanGame.map.getTILE_MAP()[yblock][xblock] == ' ' || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'i'
                        || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'x' || BombermanGame.map.getTILE_MAP()[yblock][xblock] == 'X') {
                    preBlock = BombermanGame.map.getTILE_MAP()[yblock][xblock];
                }
                BombermanGame.map.setTILE_MAP(yblock, xblock, 'B');
                if (timeMoveTile >= 8) {
                    randomDirection = Math.random();
                    if (randomDirection < 0.25) {
                        moveLeft();
                    } else if (randomDirection < 0.5) {
                        moveRight();
                    } else if (randomDirection < 0.75) {
                        moveUp();
                    } else {
                        moveDown();
                    }
                    timeMoveTile = 0;
                } else {
                    timeMoveTile++;
                }

                if (isStuck) {
                    if (randomDirection < 0.25) {
                        moveLeft();
                    } else if (randomDirection < 0.5) {
                        moveRight();
                    } else if (randomDirection < 0.75) {
                        moveUp();
                    } else {
                        moveDown();
                    }
                    isStuck = false;
                }

            }

            BombermanGame.map.mapCollision(this);

            if (movevalX == 0 && movevalY == 0) {
                isStuck = true;
            }
            x += movevalX;
            y += movevalY;

            if (life <= 0) {
                alive = false;
                stopWatch.start();
            }
            boolean check = Collision.checkCollision(this, BombermanGame.bomberman);

            if (check && !BombermanGame.bomberman.isHit()) {
                BombermanGame.bomberman.setHit(true);
                BombermanGame.bomberman.setLife(BombermanGame.bomberman.getLife() - 1);
                BombermanGame.bomberman.getStopWatch().start();
                BombermanGame.scoreNumber -= 200;
            }
        }
    }

    @Override
    public void Animation() {
        if (status == WALK_TYPE.RIGHT) {
            img = Sprite.movingSprite(Sprite.balloom_right1,
                    Sprite.balloom_right2, Sprite.balloom_right3 ,time,30).getFxImage();
            time += 1;
        } else if (status == WALK_TYPE.LEFT) {
            img = Sprite.movingSprite(Sprite.balloom_left1,
                    Sprite.balloom_left2,Sprite.balloom_left3,time,30).getFxImage();
            time += 1;
        }else if (status == WALK_TYPE.UP) {
            img = Sprite.movingSprite(Sprite.balloom_right1,
                    Sprite.balloom_right2,Sprite.balloom_right3,time,30).getFxImage();
            time += 1;
        } else if (status == WALK_TYPE.DOWN) {
            img = Sprite.movingSprite(Sprite.balloom_right1,
                    Sprite.balloom_right2,Sprite.balloom_right3,time,30).getFxImage();
            time += 1;
        }
        if(!alive) {
            if(stopWatch.getElapsedTime() <= 600) {
                img = Sprite.balloom_dead.getFxImage();
            }else{
                if(stopWatch.getElapsedTime() <= 900) {
                    img = Sprite.mob_dead1.getFxImage();
                }
                else if(stopWatch.getElapsedTime() <= 1200) {
                    img = Sprite.mob_dead2.getFxImage();
                }
                else {
                    img = Sprite.mob_dead3.getFxImage();
                }
            }
            BombermanGame.map.setTILE_MAP(yblock,xblock,' ');
        }

    }

    @Override
    public void update() {
        moveMent();
        Animation();
    }
}