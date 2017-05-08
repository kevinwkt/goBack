package mx.itesm.goback;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;


/*
 * Created by gerry on 4/7/17.
 */

class ArcadeYellowOrb extends ArcadeOrb{
    ArcadeYellowOrb(World world, Texture tx, int place, boolean active, float life){
        super(world, tx, place, active, life, ArcadeValues.radius[0], 1);
    }
}

