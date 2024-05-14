package net.op.block;

import net.op.render.model.CubeModel;

public abstract class LiquidBlock extends Block {

	protected LiquidBlock(int id) {
		super(id);
	}

	protected LiquidBlock(int id, CubeModel model) {
		super(id, model);
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
}
